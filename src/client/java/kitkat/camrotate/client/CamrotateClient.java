package kitkat.camrotate.client;

import com.mojang.blaze3d.platform.InputConstants;
import kitkat.camrotate.Camrotate;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class CamrotateClient implements ClientModInitializer {
	private static KeyMapping openConfigKey;

	@Override
	public void onInitializeClient() {
		// keybind grouping
		KeyMapping.Category keyCategory = KeyMapping.Category.register(Camrotate.id("main"));

		openConfigKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.camrotate.open_config",   // translation key (see en_us.json)
				InputConstants.KEY_B,          // default key: B
				keyCategory
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openConfigKey.consumeClick()) {
				client.gui.setScreen(CamrotateConfigScreen.create(null));
			}
		});

		Camrotate.LOGGER.info("[🔁| CamrotateClient.java] Camrotate init success on client");
	}
}