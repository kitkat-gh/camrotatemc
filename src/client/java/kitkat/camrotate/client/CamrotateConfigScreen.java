package kitkat.camrotate.client;

import kitkat.camrotate.CamrotateConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Locale;

/**
 * cloth config stuff
 */
public final class CamrotateConfigScreen {
	private CamrotateConfigScreen() {}

	/** @param parent the screen to return to when closed (null returned) */
	public static Screen create(Screen parent) {
		CamrotateConfig config = CamrotateConfig.get();

		CamrotateConfig defaults = new CamrotateConfig();

		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.translatable("camrotate.config.title"))
				.setSavingRunnable(CamrotateConfig::save);

		ConfigEntryBuilder entries = builder.entryBuilder();

		// === General ===
		ConfigCategory general = builder.getOrCreateCategory(
				Component.translatable("camrotate.config.category.general"));

		general.addEntry(entries
				.startBooleanToggle(Component.translatable("camrotate.config.mod_enabled"), config.modEnabled)
				.setDefaultValue(defaults.modEnabled)
				.setTooltip(Component.translatable("camrotate.config.mod_enabled.tooltip"))
				.setSaveConsumer(value -> config.modEnabled = value)
				.build());

		// === Rotation ===
		ConfigCategory rotation = builder.getOrCreateCategory(
				Component.translatable("camrotate.config.category.rotation"));

		rotation.addEntry(entries
				.startBooleanToggle(Component.translatable("camrotate.config.only_first_person"), config.onlyInFirstPerson)
				.setDefaultValue(defaults.onlyInFirstPerson)
				.setTooltip(Component.translatable("camrotate.config.only_first_person.tooltip"))
				.setSaveConsumer(value -> config.onlyInFirstPerson = value)
				.build());

		rotation.addEntry(entries
				.startEnumSelector(
						Component.translatable("camrotate.config.riding_rotation_type"),
						CamrotateConfig.RidingRotationType.class,
						config.ridingRotationType)
				.setDefaultValue(defaults.ridingRotationType)
				// riding_rotation_type.normal / always_follow
				.setEnumNameProvider(value -> Component.translatable(
						"camrotate.config.riding_rotation_type." + value.name().toLowerCase(Locale.ROOT)))
				.setTooltip(Component.translatable("camrotate.config.riding_rotation_type.tooltip"))
				.setSaveConsumer(value -> config.ridingRotationType = value)
				.build());

		return builder.build();
	}
}