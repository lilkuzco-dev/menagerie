package dev.lilkuzco.menagerie.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.lilkuzco.menagerie.Menagerie;
import dev.lilkuzco.menagerie.guide.MenagerieNet;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * The Field Guide: entry list on the left (silhouettes + ??? until discovered),
 * live-registry details on the right, completion counter up top. All content comes
 * from the server payload — the client hand-writes nothing.
 */
public class GuideScreen extends Screen {
	private static final int PANEL_WIDTH = 360;
	private static final int LIST_WIDTH = 150;
	private static final int PER_PAGE = 9;

	private final MenagerieNet.GuideS2C data;
	private int selected;
	private int page;

	public GuideScreen(MenagerieNet.GuideS2C data) {
		super(Component.translatable("item.menagerie.field_guide"));
		this.data = data;
	}

	private int panelX() {
		return (width - PANEL_WIDTH) / 2;
	}

	private int panelY() {
		return Math.max(24, height / 2 - 110);
	}

	@Override
	protected void init() {
		int x = panelX();
		int y = panelY() + 26;
		List<MenagerieNet.GuideEntry> entries = data.entries();
		int start = page * PER_PAGE;
		for (int i = start; i < Math.min(entries.size(), start + PER_PAGE); i++) {
			final int index = i;
			MenagerieNet.GuideEntry entry = entries.get(i);
			Component label = entry.discovered()
					? displayName(entry)
					: Component.literal("???");
			addRenderableWidget(Button.builder(label, b -> {
				selected = index;
				rebuildWidgets();
			}).bounds(x, y + (i - start) * 20, LIST_WIDTH, 18).build());
		}
		int navY = y + PER_PAGE * 20 + 4;
		if (page > 0) {
			addRenderableWidget(Button.builder(Component.literal("<"), b -> {
				page--;
				rebuildWidgets();
			}).bounds(x, navY, 40, 18).build());
		}
		if ((page + 1) * PER_PAGE < entries.size()) {
			addRenderableWidget(Button.builder(Component.literal(">"), b -> {
				page++;
				rebuildWidgets();
			}).bounds(x + LIST_WIDTH - 40, navY, 40, 18).build());
		}
		addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> onClose())
				.bounds(x + PANEL_WIDTH - 70, navY, 70, 18).build());
	}

	private static Component displayName(MenagerieNet.GuideEntry entry) {
		String path = Identifier.parse(entry.entityId()).getPath();
		return Component.literal(dev.lilkuzco.menagerie.data.Species.namePrefix(entry.species(), path))
				.append(Component.translatable("entity.menagerie." + path));
	}

	private static Identifier icon(MenagerieNet.GuideEntry entry) {
		String base = Identifier.parse(entry.entityId()).getPath() + "_" + entry.species();
		return Menagerie.id("textures/gui/guide/" + base + (entry.discovered() ? "" : "_silhouette") + ".png");
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
		super.extractRenderState(g, mouseX, mouseY, delta);
		int x = panelX();
		int y = panelY();
		g.fill(x - 8, y - 8, x + PANEL_WIDTH + 8, y + 30 + PER_PAGE * 20 + 26, 0xC0101014);

		long documented = data.entries().stream().filter(MenagerieNet.GuideEntry::discovered).count();
		g.text(font, Component.translatable("guide.menagerie.title").withStyle(s -> s.withBold(true)),
				x, y, 0xFFFFFFFF);
		g.text(font, Component.translatable("guide.menagerie.progress",
				documented, data.entries().size()), x + PANEL_WIDTH - 110, y, 0xFFE8C36A);

		if (selected < 0 || selected >= data.entries().size()) {
			return;
		}
		MenagerieNet.GuideEntry entry = data.entries().get(selected);
		int dx = x + LIST_WIDTH + 12;
		int dy = y + 26;
		g.blit(RenderPipelines.GUI_TEXTURED, icon(entry), dx, dy, 0, 0, 32, 32, 32, 32);
		if (entry.discovered()) {
			g.text(font, displayName(entry).copy().withStyle(s -> s.withBold(true)), dx + 38, dy + 4, 0xFFFFFFFF);
			int lineY = dy + 40;
			for (String line : entry.lines()) {
				g.textWithWordWrap(font, Component.literal(line), dx, lineY,
						PANEL_WIDTH - LIST_WIDTH - 16, 0xFFC8C8D0);
				lineY += 12 * (1 + font.width(line) / Math.max(1, PANEL_WIDTH - LIST_WIDTH - 16));
			}
		} else {
			g.text(font, Component.literal("???").withStyle(s -> s.withBold(true)), dx + 38, dy + 4, 0xFF9098A0);
			g.textWithWordWrap(font, Component.translatable("guide.menagerie.undiscovered"),
					dx, dy + 40, PANEL_WIDTH - LIST_WIDTH - 16, 0xFF9098A0);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
