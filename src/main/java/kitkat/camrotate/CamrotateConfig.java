package kitkat.camrotate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
	* settings
 */
public class CamrotateConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("camrotate.json");

	private static CamrotateConfig instance = new CamrotateConfig();

	/** body rotation type while riding a something */
	public enum RidingRotationType {
		/** mc default */
		NORMAL,
		/** mod control */
		ALWAYS_FOLLOW
	}

	// ---- gener. ----

	public boolean modEnabled = true;

	// ---- rot. ----

	public boolean onlyInFirstPerson = false;

	/** rot type */
	public RidingRotationType ridingRotationType = RidingRotationType.NORMAL;

	// ---- persistence ----

	public static CamrotateConfig get() {
		return instance;
	}

	/** loads file then saves it back to the file */
	public static void load() {
		if (Files.exists(CONFIG_PATH)) {
			try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
				CamrotateConfig loaded = GSON.fromJson(reader, CamrotateConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			} catch (IOException | JsonParseException e) {
				Camrotate.LOGGER.error("[🔁| CamrotateConfig.java] Could not read config, using defaults", e);
			}
		}
		instance.sanitize();
		save();
	}

	public static void save() {
		try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
			GSON.toJson(instance, writer);
		} catch (IOException e) {
			Camrotate.LOGGER.error("[🔁| CamrotateConfig.java] Could not save config", e);
		}
	}

	/** fixes manual configuration mistakes */
	private void sanitize() {
		if (ridingRotationType == null) {
			ridingRotationType = RidingRotationType.NORMAL;
		}
	}
}