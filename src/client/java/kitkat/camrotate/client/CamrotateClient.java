package kitkat.camrotate.client;

import kitkat.camrotate.Camrotate;
import net.fabricmc.api.ClientModInitializer;

public class CamrotateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Camrotate.LOGGER.info("[🔁| CamrotateClient.java] Init success on client");
	}
}