package dev.lilkuzco.menagerie.guide;

import dev.lilkuzco.menagerie.Menagerie;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/** Field Guide networking: the server ships the full registry snapshot + discoveries. */
public final class MenagerieNet {
	/** One species entry, pre-formatted server-side from the live registry. */
	public record GuideEntry(String entityId, String species, boolean discovered, List<String> lines) {
	}

	public record GuideS2C(List<GuideEntry> entries) implements CustomPacketPayload {
		public static final CustomPacketPayload.Type<GuideS2C> TYPE =
				new CustomPacketPayload.Type<>(Menagerie.id("guide"));
		public static final StreamCodec<RegistryFriendlyByteBuf, GuideS2C> CODEC = StreamCodec.of(
				(buf, payload) -> {
					buf.writeVarInt(payload.entries().size());
					for (GuideEntry entry : payload.entries()) {
						buf.writeUtf(entry.entityId());
						buf.writeUtf(entry.species());
						buf.writeBoolean(entry.discovered());
						buf.writeVarInt(entry.lines().size());
						for (String line : entry.lines()) {
							buf.writeUtf(line);
						}
					}
				},
				buf -> {
					int count = buf.readVarInt();
					List<GuideEntry> entries = new ArrayList<>();
					for (int i = 0; i < count; i++) {
						String entityId = buf.readUtf();
						String species = buf.readUtf();
						boolean discovered = buf.readBoolean();
						int lineCount = buf.readVarInt();
						List<String> lines = new ArrayList<>();
						for (int j = 0; j < lineCount; j++) {
							lines.add(buf.readUtf());
						}
						entries.add(new GuideEntry(entityId, species, discovered, lines));
					}
					return new GuideS2C(entries);
				});

		@Override
		public CustomPacketPayload.Type<GuideS2C> type() {
			return TYPE;
		}
	}

	public static void init() {
		PayloadTypeRegistry.clientboundPlay().register(GuideS2C.TYPE, GuideS2C.CODEC);
	}

	private MenagerieNet() {
	}
}
