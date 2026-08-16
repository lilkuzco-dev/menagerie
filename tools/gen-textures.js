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
// NOTE: the gorilla is NOT generated here — it ships imported public-domain art from
// Animal Garden - Western Gorilla (see CREDITS.md); this script must not touch it.
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

// ---------- Phase 3: cage textures, field guide icon, guide entry icons ----------
// cage: bar grid from iron_bars/iron_block palettes; closed variant is denser + dim fill
function paintCage(cv, barP, thick, closed, rand) {
	const size = 16;
	const bar = (x, y) => setPx(cv, x, y, shade(barP[2 + ((x + y) % 2)], 0.92 + rand() * 0.12));
	for (let x = 0; x < size; x++) { // frame
		for (let t = 0; t < thick; t++) {
			bar(x, t); bar(x, size - 1 - t); bar(t, x); bar(size - 1 - t, x);
		}
	}
	for (let x = 3; x < size - 1; x += 4) { // vertical bars
		for (let y = 0; y < size; y++) {
			for (let t = 0; t < thick; t++) bar(x + t, y);
		}
	}
	if (closed) {
		for (let y = 5; y < size - 1; y += 5) { // horizontal lock-bars
			for (let x = 0; x < size; x++) bar(x, y);
		}
		for (let y = 1; y < size - 1; y++) { // dim interior: something is in there
			for (let x = 1; x < size - 1; x++) {
				const i = (y * cv.w + x) * 4;
				if (cv.px[i + 3] === 0 && rand() < 0.5) setPx(cv, x, y, shade(barP[0], 0.5));
			}
		}
	}
}

// guide entry icon: crop a face region from the species texture, integer-upscale into
// a 32x32 tile; the silhouette variant flattens every opaque pixel to dark slate
function guideIcon(srcPng, rx, ry, rw, rh, silhouette) {
	const cv = makeCanvas(32);
	const scale = Math.max(1, Math.floor(Math.min(32 / rw, 32 / rh)));
	const ox = Math.floor((32 - rw * scale) / 2);
	const oy = Math.floor((32 - rh * scale) / 2);
	for (let y = 0; y < rh; y++) {
		for (let x = 0; x < rw; x++) {
			const i = ((ry + y) * srcPng.w + (rx + x)) * 4;
			if (srcPng.px[i + 3] < 200) continue;
			const c = silhouette ? [38, 40, 48] : [srcPng.px[i], srcPng.px[i + 1], srcPng.px[i + 2]];
			for (let sy = 0; sy < scale; sy++) {
				for (let sx = 0; sx < scale; sx++) {
					setPx(cv, ox + x * scale + sx, oy + y * scale + sy, c);
				}
			}
		}
	}
	return cv;
}


// ---------- albino gorilla ----------
// Derived from the SOURCED gorilla skin, not painted fresh: every UV pixel stays where
// it is, so it is provably the same gorilla in white. Fur is flattened to luminance and
// remapped onto a cream ramp; the bare-skin UV rects (face, ears, hands, muzzle — taken
// straight from GorillaModel's texOffs values) get a pink cast the way real albinism
// leaves unpigmented skin showing through.
function paintAlbinoGorilla(src) {
	const cv = makeCanvas(src.w);
	// bare-skin rectangles in UV space, from the model's box layout
	const SKIN = [
		[8, 43, 8, 9],    // head front (face)
		[68, 2, 5, 2],    // mouth front
		[66, 32, 3, 1],   // nose front
		[45, 17, 5, 1],   // lower jaw front
		[66, 5, 8, 6],    // right ear block
		[19, 69, 8, 6],   // left ear block
		[62, 34, 18, 9],  // right hand block
		[64, 15, 18, 9],  // left hand block
	];
	const inSkin = (x, y) => SKIN.some(([rx, ry, rw, rh]) => x >= rx && x < rx + rw && y >= ry && y < ry + rh);
	for (let y = 0; y < src.h; y++) {
		for (let x = 0; x < src.w; x++) {
			const i = (y * src.w + x) * 4;
			if (src.px[i + 3] < 8) continue;
			const l = lum([src.px[i], src.px[i + 1], src.px[i + 2]]);
			// compress the dark fur range into a bright cream ramp, keeping the shading
			const t = Math.min(1, l / 90);
			let c = [
				Math.round(214 + 34 * t),
				Math.round(208 + 34 * t),
				Math.round(196 + 34 * t),
			];
			if (inSkin(x, y)) {
				// unpigmented skin: warmer and a touch deeper than the coat
				c = [Math.min(255, Math.round(c[0] * 1.02)), Math.round(c[1] * 0.84), Math.round(c[2] * 0.83)];
			}
			const j = (y * cv.w + x) * 4;
			cv.px[j] = c[0]; cv.px[j + 1] = c[1]; cv.px[j + 2] = c[2]; cv.px[j + 3] = src.px[i + 3];
		}
	}
	return cv;
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

// albino coat, generated from the imported source skin (see paintAlbinoGorilla)
{
	const gorillaDir = path.join(OUT_ROOT, "textures/entity/gorilla");
	const base = decodePng(fs.readFileSync(path.join(gorillaDir, "default.png")));
	const albino = paintAlbinoGorilla(base);
	fs.writeFileSync(path.join(gorillaDir, "albino.png"), encodePng(albino.w, albino.h, albino.px));
	console.log("wrote textures/entity/gorilla/albino.png");
}

const icon = paintIcon(gorillaP);
fs.writeFileSync(path.join(OUT_ROOT, "icon.png"), encodePng(icon.w, icon.h, icon.px));
console.log("wrote icon.png");

// ---------- Phase 3 outputs ----------
const ironBars = decodePng(readFromJar(jar, "assets/minecraft/textures/block/iron_bars.png"));
const ironBlock = decodePng(readFromJar(jar, "assets/minecraft/textures/block/iron_block.png"));
const barP = samplePalette(ironBars, () => true);
const blockP = samplePalette(ironBlock, () => true);
const cageJobs = [
	["textures/block/cage_trap.png", barP, 1, false],
	["textures/block/cage_trap_closed.png", barP, 1, true],
	["textures/block/reinforced_cage_trap.png", blockP, 2, false],
	["textures/block/reinforced_cage_trap_closed.png", blockP, 2, true],
];
for (const [rel, palette, thick, closed] of cageJobs) {
	const cv = makeCanvas(16);
	paintCage(cv, palette, thick, closed, mulberry32(hashStr(rel)));
	const out = path.join(OUT_ROOT, rel);
	fs.mkdirSync(path.dirname(out), { recursive: true });
	fs.writeFileSync(out, encodePng(cv.w, cv.h, cv.px));
	console.log("wrote", rel);
}

// field guide item: the vanilla book, hue-shifted toward melon green
const book = decodePng(readFromJar(jar, "assets/minecraft/textures/item/book.png"));
const guideItem = makeCanvas(16);
for (let y = 0; y < 16; y++) {
	for (let x = 0; x < 16; x++) {
		const i = (y * book.w + x) * 4;
		if (book.px[i + 3] < 200) continue;
		const c = [book.px[i], book.px[i + 1], book.px[i + 2]];
		// shift the red leather cover toward green, keep pages/binding as-is
		const isCover = sat(c) > 0.2 && c[0] >= c[1];
		setPx(guideItem, x, y, isCover ? [Math.round(c[1] * 0.55), Math.round(c[0] * 0.95), Math.round(c[2] * 0.5)] : c);
	}
}
fs.mkdirSync(path.join(OUT_ROOT, "textures/item"), { recursive: true });
fs.writeFileSync(path.join(OUT_ROOT, "textures/item/field_guide.png"),
		encodePng(16, 16, guideItem.px));
console.log("wrote textures/item/field_guide.png");

// guide entry icons: face crops from OUR species textures (regions follow each
// model's UV map) + silhouette variants — the "no new art" discovery presentation
const ICON_REGIONS = {
	gorilla: [8, 43, 8, 9],      // head front (128x128 imported skin)
	crocodile: [9, 20, 6, 9],    // snout from above
	tortoise: [12, 0, 10, 12],   // shell from above
	leopard: [5, 26, 6, 5],      // head front
	hippo: [10, 38, 10, 6],      // head front
	grizzly: [6, 33, 8, 7],      // head front
	vulture: [45, 0, 4, 5],      // bald head from above
	snake: [12, 0, 14, 6],       // body segment strip
	lion: [7, 33, 8, 7],         // head front (128x128 imported skin)
};
const speciesDir = path.join(__dirname, "..", "src/main/resources/data/menagerie/species");
for (const file of fs.readdirSync(speciesDir)) {
	const spec = JSON.parse(fs.readFileSync(path.join(speciesDir, file), "utf8"));
	const animal = spec.entity.split(":")[1];
	const region = ICON_REGIONS[animal];
	const texRel = spec.texture.split(":")[1]; // textures/entity/...
	const tex = decodePng(fs.readFileSync(path.join(OUT_ROOT, texRel)));
	for (const silhouette of [false, true]) {
		const cv = guideIcon(tex, region[0], region[1], region[2], region[3], silhouette);
		const rel = "textures/gui/guide/" + animal + "_" + spec.species + (silhouette ? "_silhouette" : "") + ".png";
		const out = path.join(OUT_ROOT, rel);
		fs.mkdirSync(path.dirname(out), { recursive: true });
		fs.writeFileSync(out, encodePng(cv.w, cv.h, cv.px));
	}
	console.log("wrote guide icons for", animal, spec.species);
}
console.log("done.");
