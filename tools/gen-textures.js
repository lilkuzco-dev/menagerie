#!/usr/bin/env node
// gen-textures.js — paints every Menagerie entity texture + the mod icon.
//
// Doctrine: vanilla sources first, never from-scratch invention. Every color used
// here is SAMPLED from vanilla mob textures in the Loom-cached client jar
// (wolf/panda grays for the gorilla, turtle greens for the crocodile & tortoise,
// ocelot golds for the leopard, polar bear whites for the snow leopard), then
// painted onto OUR OWN cube-model UV layouts with deterministic noise. No pixels
// are copied verbatim from Alex's Mobs / Untamed Wilds — those are all-rights-
// reserved / GPL and were used as visual reference only.
//
// UV layouts below MUST mirror the Java models in src/client/.../client/model/.
// PNG codec ported from vibranium/tools/gen-textures.js (ours).
"use strict";
const zlib = require("node:zlib");
const fs = require("node:fs");
const os = require("node:os");
const path = require("node:path");
const { execFileSync } = require("node:child_process");

const OUT_ROOT = path.join(__dirname, "..", "src/main/resources/assets/menagerie");

// ---------- locate the vanilla client jar ----------
function findClientJar() {
	const cacheRoot = path.join(os.homedir(), ".gradle/caches/fabric-loom");
	for (const version of fs.existsSync(cacheRoot) ? fs.readdirSync(cacheRoot) : []) {
		for (const name of ["minecraft-client-only.jar", "minecraft-client.jar"]) {
			const jar = path.join(cacheRoot, version, name);
			if (fs.existsSync(jar)) {
				try {
					execFileSync("unzip", ["-l", jar, "assets/minecraft/textures/entity/wolf/wolf.png"], { stdio: "pipe" });
					return jar;
				} catch { /* not in this jar */ }
			}
		}
	}
	throw new Error("No Loom-cached client jar with entity textures. Run ./gradlew build once first.");
}
const readFromJar = (jar, entry) => execFileSync("unzip", ["-p", jar, entry], { maxBuffer: 1 << 24 });

// ---------- PNG decode (any color type) ----------
function decodePng(buf) {
	let off = 8;
	let w, h, bitDepth, colorType;
	const palette = [], trns = [], idat = [];
	while (off < buf.length) {
		const len = buf.readUInt32BE(off);
		const type = buf.toString("ascii", off + 4, off + 8);
		const data = buf.subarray(off + 8, off + 8 + len);
		if (type === "IHDR") {
			w = data.readUInt32BE(0); h = data.readUInt32BE(4);
			bitDepth = data[8]; colorType = data[9];
			if (data[12] !== 0) throw new Error("interlaced png not supported");
		} else if (type === "PLTE") for (let i = 0; i < data.length; i += 3) palette.push([data[i], data[i + 1], data[i + 2]]);
		else if (type === "tRNS") trns.push(...data);
		else if (type === "IDAT") idat.push(data);
		off += 12 + len;
	}
	const raw = zlib.inflateSync(Buffer.concat(idat));
	const channels = { 0: 1, 2: 3, 3: 1, 4: 2, 6: 4 }[colorType];
	const bpp = Math.max(1, (channels * bitDepth) / 8);
	const stride = Math.ceil((w * channels * bitDepth) / 8);
	const out = Buffer.alloc(h * stride);
	let prev = Buffer.alloc(stride);
	for (let y = 0; y < h; y++) {
		const filter = raw[y * (stride + 1)];
		const line = Buffer.from(raw.subarray(y * (stride + 1) + 1, (y + 1) * (stride + 1)));
		for (let x = 0; x < stride; x++) {
			const a = x >= bpp ? line[x - bpp] : 0;
			const b = prev[x];
			const c = x >= bpp ? prev[x - bpp] : 0;
			if (filter === 1) line[x] = (line[x] + a) & 0xff;
			else if (filter === 2) line[x] = (line[x] + b) & 0xff;
			else if (filter === 3) line[x] = (line[x] + ((a + b) >> 1)) & 0xff;
			else if (filter === 4) {
				const p = a + b - c, pa = Math.abs(p - a), pb = Math.abs(p - b), pc = Math.abs(p - c);
				line[x] = (line[x] + (pa <= pb && pa <= pc ? a : pb <= pc ? b : c)) & 0xff;
			}
			out[y * stride + x] = line[x];
		}
		prev = line;
	}
	const bitAt = (row, i) => {
		const bitPos = i * bitDepth;
		return (out[row * stride + (bitPos >> 3)] >> (8 - bitDepth - (bitPos & 7))) & ((1 << bitDepth) - 1);
	};
	const px = Buffer.alloc(w * h * 4);
	for (let y = 0; y < h; y++)
		for (let x = 0; x < w; x++) {
			const i = (y * w + x) * 4;
			if (colorType === 6) out.copy(px, i, y * stride + x * 4, y * stride + x * 4 + 4);
			else if (colorType === 2) { out.copy(px, i, y * stride + x * 3, y * stride + x * 3 + 3); px[i + 3] = 255; }
			else if (colorType === 3) {
				const idx = bitAt(y, x);
				const [r, g, b] = palette[idx] ?? [0, 0, 0];
				px[i] = r; px[i + 1] = g; px[i + 2] = b; px[i + 3] = trns[idx] ?? 255;
			} else if (colorType === 0) {
				const v = Math.round(bitAt(y, x) * (255 / ((1 << bitDepth) - 1)));
				px[i] = px[i + 1] = px[i + 2] = v; px[i + 3] = 255;
			} else if (colorType === 4) {
				const v = out[y * stride + x * 2];
				px[i] = px[i + 1] = px[i + 2] = v; px[i + 3] = out[y * stride + x * 2 + 1];
			}
		}
	return { w, h, px };
}

// ---------- PNG encode (RGBA) ----------
const CRC_TABLE = (() => {
	const t = new Int32Array(256);
	for (let n = 0; n < 256; n++) {
		let c = n;
		for (let k = 0; k < 8; k++) c = c & 1 ? 0xedb88320 ^ (c >>> 1) : c >>> 1;
		t[n] = c;
	}
	return t;
})();
const crc32 = (buf) => {
	let c = 0xffffffff;
	for (const b of buf) c = CRC_TABLE[(c ^ b) & 0xff] ^ (c >>> 8);
	return (c ^ 0xffffffff) >>> 0;
};
function pngChunk(type, data) {
	const len = Buffer.alloc(4);
	len.writeUInt32BE(data.length);
	const body = Buffer.concat([Buffer.from(type, "ascii"), data]);
	const crc = Buffer.alloc(4);
	crc.writeUInt32BE(crc32(body));
	return Buffer.concat([len, body, crc]);
}
function encodePng(w, h, rgba) {
	const ihdr = Buffer.alloc(13);
	ihdr.writeUInt32BE(w, 0);
	ihdr.writeUInt32BE(h, 4);
	ihdr[8] = 8;
	ihdr[9] = 6;
	const raw = Buffer.alloc(h * (1 + w * 4));
	for (let y = 0; y < h; y++) {
		raw[y * (1 + w * 4)] = 0;
		rgba.copy(raw, y * (1 + w * 4) + 1, y * w * 4, (y + 1) * w * 4);
	}
	return Buffer.concat([
		Buffer.from([0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a]),
		pngChunk("IHDR", ihdr),
		pngChunk("IDAT", zlib.deflateSync(raw, { level: 9 })),
		pngChunk("IEND", Buffer.alloc(0)),
	]);
}

// ---------- palette sampling ----------
const lum = ([r, g, b]) => 0.299 * r + 0.587 * g + 0.114 * b;
const sat = ([r, g, b]) => {
	const mx = Math.max(r, g, b), mn = Math.min(r, g, b);
	return mx === 0 ? 0 : (mx - mn) / mx;
};
// Collect opaque pixels passing `filter`, sort by luminance, return quantile picks
// dark -> light. This is how vanilla art supplies our shades without pixel copying.
function samplePalette(img, filter, quantiles = [0.06, 0.25, 0.45, 0.65, 0.88]) {
	const pixels = [];
	for (let i = 0; i < img.px.length; i += 4) {
		if (img.px[i + 3] < 200) continue;
		const c = [img.px[i], img.px[i + 1], img.px[i + 2]];
		if (filter(c)) pixels.push(c);
	}
	if (pixels.length < 20) throw new Error("palette sample too small — filter too strict");
	pixels.sort((a, b) => lum(a) - lum(b));
	return quantiles.map((q) => pixels[Math.min(pixels.length - 1, Math.floor(q * pixels.length))]);
}
const shade = (c, f) => c.map((v) => Math.max(0, Math.min(255, Math.round(v * f))));
// Rescale each sampled color to a target luminance, keeping its hue — guarantees a
// readable dark->light spread even when the vanilla sample skews dark (gorilla grays).
function normalizeLum(palette, targets) {
	return palette.map((c, i) => {
		const l = Math.max(1, lum(c));
		return shade(c, targets[i] / l);
	});
}

// ---------- deterministic PRNG ----------
function mulberry32(seed) {
	let a = seed >>> 0;
	return () => {
		a |= 0; a = (a + 0x6d2b79f5) | 0;
		let t = Math.imul(a ^ (a >>> 15), 1 | a);
		t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t;
		return ((t ^ (t >>> 14)) >>> 0) / 4294967296;
	};
}
const hashStr = (s) => [...s].reduce((a, c) => (Math.imul(a, 31) + c.charCodeAt(0)) | 0, 7);

// ---------- canvas helpers ----------
const makeCanvas = (size) => ({ w: size, h: size, px: Buffer.alloc(size * size * 4) });
function setPx(cv, x, y, [r, g, b]) {
	if (x < 0 || y < 0 || x >= cv.w || y >= cv.h) return;
	const i = (y * cv.w + x) * 4;
	cv.px[i] = r; cv.px[i + 1] = g; cv.px[i + 2] = b; cv.px[i + 3] = 255;
}
function fillRect(cv, x0, y0, w, h, colorFn) {
	for (let y = 0; y < h; y++) for (let x = 0; x < w; x++) setPx(cv, x0 + x, y0 + y, colorFn(x, y));
}
// Standard Minecraft box UV: fills all six faces of a (w,h,d) box whose texOffs is
// (u,v). shader(face, x, y, fw, fh) -> [r,g,b] where face is top/bottom/right/front/left/back.
function paintBox(cv, u, v, w, h, d, shader) {
	fillRect(cv, u + d, v, w, d, (x, y) => shader("top", x, y, w, d));
	fillRect(cv, u + d + w, v, w, d, (x, y) => shader("bottom", x, y, w, d));
	fillRect(cv, u, v + d, d, h, (x, y) => shader("right", x, y, d, h));
	fillRect(cv, u + d, v + d, w, h, (x, y) => shader("front", x, y, w, h));
	fillRect(cv, u + d + w, v + d, d, h, (x, y) => shader("left", x, y, d, h));
	fillRect(cv, u + d + w + d, v + d, w, h, (x, y) => shader("back", x, y, w, h));
}
// Generic fur/skin shader: pick around a base palette index with noise, lighter on
// top faces, darker on bottoms — the classic vanilla mob look.
function furShader(palette, rand, baseIdx, noise = 0.9) {
	return (face, x, y) => {
		let idx = baseIdx + (face === "top" ? 0.7 : face === "bottom" ? -0.9 : 0);
		idx += (rand() - 0.5) * 2 * noise;
		const i = Math.max(0, Math.min(palette.length - 1, Math.round(idx)));
		const c = palette[i];
		const j = 0.96 + rand() * 0.08; // subtle per-pixel jitter
		return shade(c, j);
	};
}

// ================= animal painters =================
// Every painter takes (canvas, palette P [5 shades dark->light], rng).

// ---- gorilla: layout head(0,0 8x8x6) muzzle(28,0 4x3x2) body(0,16 12x12x14)
//               arm(44,0 4x12x4) leg(0,42 4x7x4)
function paintGorilla(cv, P, rand, { silverback }) {
	paintBox(cv, 0, 0, 8, 8, 6, furShader(P, rand, 1.2));
	// face patch: lighter inner front face + eyes
	fillRect(cv, 6 + 1, 6 + 2, 6, 5, () => shade(P[2], 0.96 + rand() * 0.08));
	setPx(cv, 6 + 2, 6 + 3, P[0]); setPx(cv, 6 + 5, 6 + 3, P[0]); // eyes
	setPx(cv, 6 + 2, 6 + 4, P[4]); setPx(cv, 6 + 5, 6 + 4, P[4]); // catchlight
	paintBox(cv, 28, 0, 4, 3, 2, furShader(P, rand, 2));
	setPx(cv, 30 + 1, 2 + 1, P[0]); setPx(cv, 30 + 2, 2 + 1, P[0]); // nostrils (muzzle front)
	paintBox(cv, 0, 16, 12, 12, 14, (face, x, y, fw, fh) => {
		// silverback saddle: light gray rear half of the back + upper flanks
		if (silverback) {
			const rear = face === "top" && y > fh * 0.45;
			const flank = (face === "left" || face === "right") && y < fh * 0.4 && x > fw * 0.35;
			const rump = face === "back" && y < fh * 0.5;
			if (rear || flank || rump) return shade(P[4], 0.9 + rand() * 0.12);
		}
		return furShader(P, rand, 1.1)(face, x, y, fw, fh);
	});
	// chest: slightly lighter (front face of body center)
	fillRect(cv, 14 + 3, 30 + 2, 6, 6, () => shade(P[2], 0.92 + rand() * 0.1));
	paintBox(cv, 44, 0, 4, 12, 4, furShader(P, rand, 0.9));
	paintBox(cv, 0, 42, 4, 7, 4, furShader(P, rand, 0.9));
}

// ---- crocodile: body(0,0 8x4x16) head(0,20 6x2x9) jaw(30,20 6x2x9)
//                 tail(0,31 6x3x10) tail2(32,31 4x2x8) leg(0,44 3x4x3)
function paintCroc(cv, P, rand) {
	const scale = (baseIdx) => (face, x, y, fw, fh) => {
		if (face === "bottom" || (face === "front" && y > fh * 0.6)) {
			return shade(P[3], 0.95 + rand() * 0.1); // pale belly
		}
		// osteoderm ridge rows along the back
		if (face === "top" && (x % 2 === 0) && (y % 3 !== 1)) {
			return shade(P[0], 0.9 + rand() * 0.15);
		}
		return furShader(P, rand, baseIdx, 0.7)(face, x, y, fw, fh);
	};
	paintBox(cv, 0, 0, 8, 4, 16, scale(1.4));
	paintBox(cv, 0, 20, 6, 2, 9, scale(1.4));
	// eyes on top of the head, near the rear
	setPx(cv, 9 + 0, 20 + 7, P[4]); setPx(cv, 9 + 5, 20 + 7, P[4]);
	setPx(cv, 9 + 0, 20 + 8, P[0]); setPx(cv, 9 + 5, 20 + 8, P[0]);
	paintBox(cv, 30, 20, 6, 2, 9, (face, x, y, fw, fh) =>
		face === "bottom" ? shade(P[3], 0.95 + rand() * 0.1) : furShader(P, rand, 1.2, 0.6)(face, x, y, fw, fh));
	paintBox(cv, 0, 31, 6, 3, 10, scale(1.3));
	paintBox(cv, 32, 31, 4, 2, 8, scale(1.2));
	paintBox(cv, 0, 44, 3, 4, 3, furShader(P, rand, 1.2, 0.6));
}

// ---- tortoise: shell(0,0 10x6x12) shellTop(0,18 8x2x8) head(44,0 4x4x4)
//                leg(44,8 3x4x3) tail(32,18 2x2x2)
function paintTortoise(cv, P, rand, skin) {
	const scute = (face, x, y, fw, fh) => {
		// scute grid: dark seams every 3px, lighter plate centers
		if (face !== "bottom" && (x % 3 === 0 || y % 3 === 0)) return shade(P[0], 0.95 + rand() * 0.1);
		if (face === "bottom") return shade(skin[3], 0.95 + rand() * 0.08);
		return shade(P[2 + ((x + y) % 2)], 0.94 + rand() * 0.1);
	};
	paintBox(cv, 0, 0, 10, 6, 12, scute);
	paintBox(cv, 0, 18, 8, 2, 8, scute);
	paintBox(cv, 44, 0, 4, 4, 4, furShader(skin, rand, 2, 0.6));
	setPx(cv, 48 + 0, 4 + 1, skin[0]); setPx(cv, 48 + 3, 4 + 1, skin[0]); // eyes
	paintBox(cv, 44, 8, 3, 4, 3, furShader(skin, rand, 1.8, 0.6));
	paintBox(cv, 32, 18, 2, 2, 2, furShader(skin, rand, 1.8, 0.6));
}

// ---- leopard: body(0,0 7x5x16) head(0,21 6x5x5) ear(22,21 2x2x1)
//               leg(28,21 2x7x2) tail(36,21 1x1x8)
function paintLeopard(cv, P, rand, spotColor) {
	const spotted = (baseIdx) => (face, x, y, fw, fh) => {
		if (face === "bottom") return shade(P[4], 0.95 + rand() * 0.08); // pale belly
		if (rand() < 0.18) return shade(spotColor, 0.9 + rand() * 0.2); // rosettes
		return furShader(P, rand, baseIdx, 0.6)(face, x, y, fw, fh);
	};
	paintBox(cv, 0, 0, 7, 5, 16, spotted(2.4));
	paintBox(cv, 0, 21, 6, 5, 5, spotted(2.4));
	// face: eyes + nose on the front face at (5,26)
	setPx(cv, 5 + 1, 26 + 1, P[0]); setPx(cv, 5 + 4, 26 + 1, P[0]);
	setPx(cv, 5 + 2, 26 + 3, spotColor); setPx(cv, 5 + 3, 26 + 3, spotColor);
	paintBox(cv, 22, 21, 2, 2, 1, furShader(P, rand, 1.4, 0.5));
	paintBox(cv, 28, 21, 2, 7, 2, spotted(2.2));
	paintBox(cv, 36, 21, 1, 1, 8, spotted(2.2));
}

// ---- hippo: body(0,0 14x12x16) head(0,28 10x6x10) ears(56,0 2x2x1)
//             jaw(0,44 9x3x9) leg(40,28 4x5x4)
function paintHippo(cv, P, mouthPink, rand) {
	const hide = (baseIdx) => furShader(P, rand, baseIdx, 0.5); // smooth thick skin
	paintBox(cv, 0, 0, 14, 12, 16, (face, x, y, fw, fh) =>
		face === "bottom" || (face !== "top" && y > fh * 0.7)
				? shade(P[3], 0.96 + rand() * 0.06) // pale underbelly
				: hide(1.6)(face, x, y, fw, fh));
	paintBox(cv, 0, 28, 10, 6, 10, hide(1.6));
	// nostrils on the snout front
	setPx(cv, 10 + 2, 34 + 1, P[0]); setPx(cv, 10 + 7, 34 + 1, P[0]);
	// eyes high on the head top
	setPx(cv, 10 + 1, 28 + 8, P[0]); setPx(cv, 10 + 8, 28 + 8, P[0]);
	paintBox(cv, 56, 0, 2, 2, 1, hide(1.4));
	paintBox(cv, 0, 44, 9, 3, 9, (face, x, y, fw, fh) =>
		face === "top" ? shade(mouthPink, 0.9 + rand() * 0.15) // open-mouth interior
				: hide(1.5)(face, x, y, fw, fh));
	paintBox(cv, 40, 28, 4, 5, 4, hide(1.3));
}

// ---- grizzly: body(0,0 12x11x16) head(0,27 8x7x6) snout(28,27 4x3x3)
//               ears(42,27 2x2x1) leg(0,40 5x6x5)
function paintGrizzly(cv, P, rand) {
	paintBox(cv, 0, 0, 12, 11, 16, furShader(P, rand, 1.8));
	paintBox(cv, 0, 27, 8, 7, 6, furShader(P, rand, 1.8));
	// eyes on the head front face at (u+d,v+d) = (6,33)
	setPx(cv, 6 + 1, 33 + 2, P[0]); setPx(cv, 6 + 6, 33 + 2, P[0]);
	paintBox(cv, 28, 27, 4, 3, 3, furShader(P, rand, 2.6, 0.5)); // lighter muzzle
	setPx(cv, 31 + 1, 30 + 0, P[0]); setPx(cv, 31 + 2, 30 + 0, P[0]); // nose
	paintBox(cv, 42, 27, 2, 2, 1, furShader(P, rand, 1.2, 0.5));
	paintBox(cv, 0, 40, 5, 6, 5, furShader(P, rand, 1.2));
}

// ---- vulture: body(0,0 6x4x8) neck(28,0 3x5x3) head(40,0 4x3x5)
//               wing(0,12 12x1x8) tail(0,21 5x1x6) leg(22,21 2x4x2)
function paintVulture(cv, P, baldP, rand) {
	const feathers = (baseIdx) => furShader(P, rand, baseIdx, 0.8);
	paintBox(cv, 0, 0, 6, 4, 8, feathers(1.3));
	// white ruff collar at the neck base, bald pink neck + head above it
	paintBox(cv, 28, 0, 3, 5, 3, (face, x, y, fw, fh) =>
		face !== "top" && face !== "bottom" && y >= fh - 1
				? shade(P[4], 0.95 + rand() * 0.1)
				: shade(baldP[2 + (rand() < 0.3 ? 1 : 0)], 0.92 + rand() * 0.12));
	paintBox(cv, 40, 0, 4, 3, 5, (face, x, y, fw, fh) => {
		if (face === "front") { // hooked beak tip
			return shade(P[4], 0.75 + rand() * 0.1);
		}
		return shade(baldP[2 + (rand() < 0.3 ? 1 : 0)], 0.92 + rand() * 0.12);
	});
	// eyes on the head sides
	setPx(cv, 40 + 1, 5 + 0, P[0]); setPx(cv, 40 + 11, 5 + 0, P[0]);
	paintBox(cv, 0, 12, 12, 1, 8, (face, x, y, fw, fh) => {
		// darker flight-feather edge along the wing tip and trailing edge
		const tip = (face === "top" || face === "bottom") && (x < 3 || y >= fh - 2);
		return tip ? shade(P[0], 0.9 + rand() * 0.1) : feathers(1.5)(face, x, y, fw, fh);
	});
	paintBox(cv, 0, 21, 5, 1, 6, feathers(1.2));
	paintBox(cv, 22, 21, 2, 4, 2, (face, x, y, fw, fh) => shade(baldP[1], 0.9 + rand() * 0.1));
}

// ---- snake: head(0,0 3x2x3) bodyseg(12,0 3x2x4) tail(26,0 2x1x4)
function paintSnake(cv, P, blotch, rand) {
	const scales = (face, x, y, fw, fh) => {
		if (face === "bottom") return shade(P[4], 0.95 + rand() * 0.08); // pale scutes
		if ((x + y) % 3 === 0 && rand() < 0.6) return shade(blotch, 0.9 + rand() * 0.15); // dorsal blotches
		return shade(P[1 + ((x + y) % 2)], 0.94 + rand() * 0.1);
	};
	paintBox(cv, 0, 0, 3, 2, 3, scales);
	setPx(cv, 3 + 0, 2 + 0, P[0]); setPx(cv, 3 + 2, 2 + 0, P[0]); // eyes on head top
	paintBox(cv, 12, 0, 3, 2, 4, scales);
	paintBox(cv, 26, 0, 2, 1, 4, scales);
}

// ---------- mod icon: blocky gorilla face, palette from the same wolf/panda grays ----------
function paintIcon(P) {
	const cv = makeCanvas(128);
	const rand = mulberry32(hashStr("icon"));
	const px = 8; // 16x16 logical pixels
	const put = (x, y, c) => fillRect(cv, x * px, y * px, px, px, () => shade(c, 0.95 + rand() * 0.1));
	for (let y = 2; y < 14; y++) for (let x = 3; x < 13; x++) put(x, y, P[1]); // head
	for (let y = 3; y < 6; y++) { put(2, y, P[1]); put(13, y, P[1]); } // ears
	for (let y = 7; y < 12; y++) for (let x = 4; x < 12; x++) put(x, y, P[2]); // face patch
	for (let y = 10; y < 13; y++) for (let x = 5; x < 11; x++) put(x, y, P[3]); // muzzle
	put(5, 8, P[0]); put(10, 8, P[0]); // eyes
	put(6, 11, P[0]); put(9, 11, P[0]); // nostrils
	return cv;
}

// ================= main =================
const jar = findClientJar();
console.log("client jar:", jar);
const wolf = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/wolf/wolf.png"));
const panda = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/panda/panda.png"));
const turtle = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/turtle/turtle.png"));
const ocelot = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/cat/ocelot.png"));
const polar = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/bear/polarbear.png"));

// palettes (each: 5 shades dark -> light)
const grayFur = samplePalette({ px: Buffer.concat([wolf.px, panda.px]) }, (c) => sat(c) < 0.25);
const gorillaP = normalizeLum(grayFur, [32, 58, 88, 125, 195]); // charcoal -> silver
const mountainP = normalizeLum(grayFur, [24, 46, 70, 102, 175]); // darker, denser coat
const crocGreens = samplePalette(turtle, (c) => sat(c) > 0.15 && c[1] >= c[0] && c[1] >= c[2]);
const saltGreens = crocGreens.map((c) => shade([c[0] * 1.05, c[1] * 0.92, c[2] * 1.0], 0.8)); // murkier, darker
const shellP = samplePalette(turtle, (c) => sat(c) > 0.1).map((c) => shade([c[0] * 1.25, c[1] * 1.05, c[2] * 0.7], 0.85)); // browner shell
const skinP = samplePalette(turtle, (c) => sat(c) > 0.1).map((c) => shade([c[0] * 1.3, c[1] * 1.1, c[2] * 0.75], 1.0)); // sandy skin
const leopardP = samplePalette(ocelot, (c) => sat(c) > 0.12);
const snowP = samplePalette(polar, () => true);
const spotDark = shade(leopardP[0], 0.6);
const snowSpot = shade(snowP[0], 0.75);

// ---------- Phase 2 palettes ----------
const pig = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/pig/pig_temperate.png"));
const mooshroom = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/cow/mooshroom_brown.png"));
const chestnutWolf = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/wolf/wolf_chestnut.png"));
const chicken = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/chicken/chicken_temperate.png"));
const parrotGrey = decodePng(readFromJar(jar, "assets/minecraft/textures/entity/parrot/parrot_grey.png"));
const cactus = decodePng(readFromJar(jar, "assets/minecraft/textures/block/cactus_side.png"));
const sandstone = decodePng(readFromJar(jar, "assets/minecraft/textures/block/sandstone.png"));

// hippo: pig pinks + mooshroom browns, heavily desaturated toward gray-mauve hide
const desat = (c, keep) => {
	const l = lum(c);
	return c.map((v, i) => Math.round(l + (v - l) * keep));
};
const hippoP = normalizeLum(
		samplePalette({ px: Buffer.concat([pig.px, mooshroom.px]) }, (c) => sat(c) > 0.05)
				.map((c) => desat(c, 0.35)), [40, 70, 100, 135, 180]);
const hippoSwampP = normalizeLum(hippoP, [30, 55, 80, 110, 150]); // darker, murkier
const mouthPink = samplePalette(pig, (c) => sat(c) > 0.15)[3]; // undesaturated pig pink

// grizzly: chestnut wolf browns; black bear: same hues crushed dark
const grizzlyP = normalizeLum(samplePalette(chestnutWolf, (c) => sat(c) > 0.1), [35, 62, 92, 128, 185]);
const blackBearP = normalizeLum(grizzlyP, [18, 32, 50, 75, 130]);

// vulture: grey parrot + chicken darks for feathers, chicken/pig pinks for the bald head
const vultureP = normalizeLum(
		samplePalette({ px: Buffer.concat([parrotGrey.px, chicken.px]) }, (c) => sat(c) < 0.35),
		[25, 48, 75, 110, 200]);
const baldP = normalizeLum(samplePalette({ px: Buffer.concat([chicken.px, pig.px]) },
		(c) => sat(c) > 0.15 && c[0] >= c[1]), [90, 120, 150, 175, 200]);

// snake: cactus greens for the python, sandstone tones for the desert viper
const pythonP = normalizeLum(samplePalette(cactus, (c) => sat(c) > 0.1), [30, 55, 82, 112, 160]);
const viperP = normalizeLum(samplePalette(sandstone, () => true), [55, 85, 115, 150, 195]);
const viperBlotch = shade(viperP[0], 0.6);
const pythonBlotch = shade(pythonP[0], 0.6);

const jobs = [
	["textures/entity/gorilla/lowland.png", (cv, r) => paintGorilla(cv, gorillaP, r, { silverback: false })],
	["textures/entity/gorilla/lowland_silverback.png", (cv, r) => paintGorilla(cv, gorillaP, r, { silverback: true })],
	["textures/entity/gorilla/mountain.png", (cv, r) => paintGorilla(cv, mountainP, r, { silverback: false })],
	["textures/entity/gorilla/mountain_silverback.png", (cv, r) => paintGorilla(cv, mountainP, r, { silverback: true })],
	["textures/entity/crocodile/nile.png", (cv, r) => paintCroc(cv, crocGreens, r)],
	["textures/entity/crocodile/saltwater.png", (cv, r) => paintCroc(cv, saltGreens, r)],
	["textures/entity/tortoise/savanna.png", (cv, r) => paintTortoise(cv, shellP, r, skinP)],
	["textures/entity/leopard/leopard.png", (cv, r) => paintLeopard(cv, leopardP, r, spotDark)],
	["textures/entity/leopard/snow.png", (cv, r) => paintLeopard(cv, snowP, r, snowSpot)],
	["textures/entity/hippo/river.png", (cv, r) => paintHippo(cv, hippoP, mouthPink, r)],
	["textures/entity/hippo/swamp.png", (cv, r) => paintHippo(cv, hippoSwampP, mouthPink, r)],
	["textures/entity/grizzly/grizzly.png", (cv, r) => paintGrizzly(cv, grizzlyP, r)],
	["textures/entity/grizzly/black.png", (cv, r) => paintGrizzly(cv, blackBearP, r)],
	["textures/entity/vulture/griffon.png", (cv, r) => paintVulture(cv, vultureP, baldP, r)],
	["textures/entity/snake/viper.png", (cv, r) => paintSnake(cv, viperP, viperBlotch, r)],
	["textures/entity/snake/python.png", (cv, r) => paintSnake(cv, pythonP, pythonBlotch, r)],
];

for (const [rel, painter] of jobs) {
	const cv = makeCanvas(64);
	painter(cv, mulberry32(hashStr(rel)));
	const out = path.join(OUT_ROOT, rel);
	fs.mkdirSync(path.dirname(out), { recursive: true });
	fs.writeFileSync(out, encodePng(cv.w, cv.h, cv.px));
	console.log("wrote", rel);
}

const icon = paintIcon(gorillaP);
fs.writeFileSync(path.join(OUT_ROOT, "icon.png"), encodePng(icon.w, icon.h, icon.px));
console.log("wrote icon.png");
console.log("done.");
