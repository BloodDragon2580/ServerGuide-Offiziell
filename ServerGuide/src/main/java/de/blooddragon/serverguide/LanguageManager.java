package de.blooddragon.serverguide;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class LanguageManager {
    private final ServerGuidePlugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<String, YamlConfiguration> languages = new HashMap<>();

    public LanguageManager(ServerGuidePlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        File folder = new File(plugin.getDataFolder(), "languages");
        if (!folder.exists() && !folder.mkdirs()) {
            plugin.getLogger().warning("Could not create languages folder.");
        }

        saveBundled("en_US.yml");
        saveBundled("en_GB.yml");
        saveBundled("de_DE.yml");
        saveBundled("de_AT.yml");
        saveBundled("de_CH.yml");
        saveBundled("fr_FR.yml");
        saveBundled("es_ES.yml");
        saveBundled("it_IT.yml");
        saveBundled("nl_NL.yml");
        saveBundled("pl_PL.yml");
        saveBundled("pt_BR.yml");
        saveBundled("ru_RU.yml");
        saveBundled("tr_TR.yml");
        saveBundled("uk_UA.yml");
        saveBundled("cs_CZ.yml");
        saveBundled("hu_HU.yml");
        saveBundled("ro_RO.yml");
        saveBundled("zh_CN.yml");
        saveBundled("ja_JP.yml");
        saveBundled("ko_KR.yml");
        saveBundled("pt_PT.yml");
        saveBundled("es_MX.yml");

        languages.clear();
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".yml"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            String id = file.getName().substring(0, file.getName().length() - 4);
            YamlConfiguration configuration = loadLanguageFile(file);
            if (configuration != null) {
                languages.put(normalize(id), configuration);
            }
        }
    }

    private YamlConfiguration loadLanguageFile(File file) {
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(file);
            return configuration;
        } catch (Exception exception) {
            plugin.getLogger().warning(
                "Invalid language file detected: " + file.getName()
                + ". Restoring the bundled version."
            );

            File backup = new File(file.getParentFile(), file.getName() + ".broken");
            try {
                Files.copy(
                    file.toPath(),
                    backup.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                plugin.saveResource("languages/" + file.getName(), true);

                configuration = new YamlConfiguration();
                configuration.load(file);

                plugin.getLogger().info(
                    "Restored " + file.getName()
                    + ". The previous file was saved as " + backup.getName() + "."
                );
                return configuration;
            } catch (Exception repairException) {
                plugin.getLogger().severe(
                    "Could not repair " + file.getName()
                    + ": " + repairException.getMessage()
                );
                return null;
            }
        }
    }

    private void saveBundled(String name) {
        File target = new File(plugin.getDataFolder(), "languages/" + name);
        if (!target.exists()) {
            plugin.saveResource("languages/" + name, false);
        }
    }

    public String locale(Player player) {
        String requested = normalize(player.locale().toString());
        if (languages.containsKey(requested)) {
            return requested;
        }

        int separator = requested.indexOf('_');
        if (separator > 0) {
            String language = requested.substring(0, separator);
            for (String available : languages.keySet()) {
                if (available.startsWith(language + "_")) {
                    return available;
                }
            }
        }

        String configured = normalize(plugin.getConfig().getString("default-language", "en_US"));
        if (languages.containsKey(configured)) {
            return configured;
        }
        return "en_US";
    }

    public String raw(Player player, String path) {
        return raw(locale(player), path);
    }

    public String raw(String locale, String path) {
        YamlConfiguration selected = languages.get(normalize(locale));
        String value = selected == null ? null : selected.getString(path);

        if (value == null) {
            String fallback = normalize(plugin.getConfig().getString("fallback-language", "en_US"));
            YamlConfiguration fallbackConfig = languages.get(fallback);
            value = fallbackConfig == null ? null : fallbackConfig.getString(path);
        }

        return applyPlaceholders(value == null ? "<red>Missing text: " + path + "</red>" : value);
    }

    public List<String> lines(Player player, String path) {
        YamlConfiguration selected = languages.get(locale(player));
        List<String> lines = selected == null ? List.of() : selected.getStringList(path);
        if (!lines.isEmpty()) {
            return lines.stream().map(this::applyPlaceholders).toList();
        }

        YamlConfiguration fallback = languages.get(normalize(
            plugin.getConfig().getString("fallback-language", "en_US")
        ));
        if (fallback == null) {
            return List.of("<red>Missing text: " + path + "</red>");
        }
        return fallback.getStringList(path).stream().map(this::applyPlaceholders).toList();
    }

    public Component component(Player player, String path) {
        return miniMessage.deserialize(raw(player, path));
    }

    public Component parse(String value) {
        return miniMessage.deserialize(value);
    }

    public Component message(Player player, String path) {
        return component(player, "messages." + path);
    }

    private String applyPlaceholders(String value) {
        if (value == null) {
            return "";
        }

        return value
            .replace("<server_name>", plugin.getConfig().getString("server.name", "My Minecraft Server"))
            .replace("<website>", plugin.getConfig().getString("server.website", "https://example.com"))
            .replace("<website_display>", plugin.getConfig().getString("server.website-display", "example.com"))
            .replace("<discord>", plugin.getConfig().getString("server.discord", "https://discord.gg/example"))
            .replace("<discord_display>", plugin.getConfig().getString("server.discord-display", "discord.gg/example"));
    }

    private String normalize(String locale) {
        if (locale == null || locale.isBlank()) {
            return "en_US";
        }
        String cleaned = locale.replace('-', '_');
        String[] parts = cleaned.split("_", 2);
        if (parts.length == 1) {
            return parts[0].toLowerCase(Locale.ROOT);
        }
        return parts[0].toLowerCase(Locale.ROOT) + "_" + parts[1].toUpperCase(Locale.ROOT);
    }
}
