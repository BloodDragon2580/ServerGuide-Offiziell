package de.blooddragon.serverguide;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinListener implements Listener {
    private final ServerGuidePlugin plugin;
    private final GuideManager guideManager;

    public JoinListener(ServerGuidePlugin plugin, GuideManager guideManager) {
        this.plugin = plugin;
        this.guideManager = guideManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("enabled", true)
            || !plugin.getConfig().getBoolean("show-on-join", true)
            || event.getPlayer().hasPermission("serverguide.bypass")
            || guideManager.isHidden(event.getPlayer())) {
            return;
        }

        long delay = Math.max(0L, plugin.getConfig().getLong("join-delay-ticks", 40L));
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (event.getPlayer().isOnline() && !guideManager.isHidden(event.getPlayer())) {
                guideManager.show(event.getPlayer());
            }
        }, delay);
    }
}
