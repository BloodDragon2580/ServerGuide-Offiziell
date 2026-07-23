package de.blooddragon.serverguide;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class GuideCommand implements CommandExecutor, TabCompleter {
    private final ServerGuidePlugin plugin;
    private final GuideManager guideManager;
    private final LanguageManager languages;
    private final NamespacedKey hiddenKey;

    public GuideCommand(ServerGuidePlugin plugin, GuideManager guideManager,
                        LanguageManager languages, NamespacedKey hiddenKey) {
        this.plugin = plugin;
        this.guideManager = guideManager;
        this.languages = languages;
        this.hiddenKey = hiddenKey;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        String sub = args.length == 0 ? "open" : args[0].toLowerCase(Locale.ROOT);

        if (sub.equals("open")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be used by a player.");
                return true;
            }
            guideManager.show(player);
            return true;
        }

        if (!sender.hasPermission("serverguide.admin")) {
            if (sender instanceof Player player) {
                player.sendMessage(languages.message(player, "no-permission"));
            } else {
                sender.sendMessage("No permission.");
            }
            return true;
        }

        if (sub.equals("reload")) {
            plugin.reloadPlugin();
            if (sender instanceof Player player) {
                player.sendMessage(languages.message(player, "reloaded"));
            } else {
                sender.sendMessage("ServerGuide reloaded.");
            }
            return true;
        }

        if (sub.equals("reset")) {
            if (args.length == 1) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("Usage: /serverguide reset <player>");
                    return true;
                }
                guideManager.setHidden(player, false);
                player.sendMessage(languages.message(player, "reset-self"));
                return true;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if (!target.hasPlayedBefore() && !target.isOnline()) {
                if (sender instanceof Player player) {
                    player.sendMessage(languages.message(player, "player-not-found"));
                } else {
                    sender.sendMessage("Player not found.");
                }
                return true;
            }

            Player onlineTarget = target.getPlayer();
            if (onlineTarget == null) {
                if (sender instanceof Player player) {
                    player.sendMessage(languages.message(player, "player-not-found"));
                } else {
                    sender.sendMessage("The player must be online to reset the guide preference.");
                }
                return true;
            }

            guideManager.setHidden(onlineTarget, false);

            if (sender instanceof Player player) {
                player.sendRichMessage(
                    languages.raw(player, "messages.reset-other"),
                    Placeholder.unparsed("player", onlineTarget.getName())
                );
            } else {
                sender.sendMessage("Guide preference reset for " + onlineTarget.getName() + ".");
            }
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>(List.of("open"));
            if (sender.hasPermission("serverguide.admin")) {
                options.add("reload");
                options.add("reset");
            }
            return options.stream()
                .filter(value -> value.startsWith(args[0].toLowerCase(Locale.ROOT)))
                .toList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("reset") && sender.hasPermission("serverguide.admin")) {
            return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(args[1].toLowerCase(Locale.ROOT)))
                .toList();
        }
        return List.of();
    }
}
