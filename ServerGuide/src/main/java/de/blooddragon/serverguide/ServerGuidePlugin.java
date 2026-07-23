package de.blooddragon.serverguide;

import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerGuidePlugin extends JavaPlugin {
    private LanguageManager languageManager;
    private GuideManager guideManager;
    private NamespacedKey hiddenKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        hiddenKey = new NamespacedKey(this, "hide_guide");

        languageManager = new LanguageManager(this);
        languageManager.load();

        guideManager = new GuideManager(this, languageManager, hiddenKey);

        getServer().getPluginManager().registerEvents(new JoinListener(this, guideManager), this);

        GuideCommand command = new GuideCommand(this, guideManager, languageManager, hiddenKey);
        PluginCommand pluginCommand = getCommand("serverguide");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        }

        getLogger().info("ServerGuide 1.3.0 enabled.");
    }

    public void reloadPlugin() {
        reloadConfig();
        languageManager.load();
    }
}
