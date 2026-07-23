package de.blooddragon.serverguide;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public final class GuideManager {
    private final ServerGuidePlugin plugin;
    private final LanguageManager languages;
    private final NamespacedKey hiddenKey;

    public GuideManager(ServerGuidePlugin plugin, LanguageManager languages, NamespacedKey hiddenKey) {
        this.plugin = plugin;
        this.languages = languages;
        this.hiddenKey = hiddenKey;
    }

    public boolean isHidden(Player player) {
        return player.getPersistentDataContainer().getOrDefault(hiddenKey, PersistentDataType.BYTE, (byte) 0) == 1;
    }

    public void setHidden(Player player, boolean hidden) {
        if (hidden) {
            player.getPersistentDataContainer().set(hiddenKey, PersistentDataType.BYTE, (byte) 1);
        } else {
            player.getPersistentDataContainer().remove(hiddenKey);
        }
    }

    public void show(Player player) {
        showPage(player, 0);
    }

    public void showPage(Player player, int pageIndex) {
        if (!player.isOnline()) {
            return;
        }

        List<String> pageIds = plugin.getConfig().getStringList("pages");
        if (pageIds.isEmpty()) {
            player.sendMessage(Component.text("ServerGuide: No pages configured."));
            return;
        }

        int index = Math.max(0, Math.min(pageIndex, pageIds.size() - 1));
        String pageId = pageIds.get(index);

        List<Component> bodyLines = new ArrayList<>();
        List<String> configuredLines = languages.lines(player, "pages." + pageId + ".lines");
        if (configuredLines.isEmpty()) {
            configuredLines = List.of("<red>Missing page: " + pageId + "</red>");
        }
        for (String line : configuredLines) {
            bodyLines.add(languages.parse(line));
        }

        Component body = Component.empty();
        for (int i = 0; i < bodyLines.size(); i++) {
            if (i > 0) {
                body = body.append(Component.newline());
            }
            body = body.append(bodyLines.get(i));
        }

        List<ActionButton> actions = new ArrayList<>();
        if (index > 0) {
            int previous = index - 1;
            actions.add(button(player, "buttons.previous", () -> showPage(player, previous)));
        }
        if (index < pageIds.size() - 1) {
            int next = index + 1;
            actions.add(button(player, "buttons.next", () -> showPage(player, next)));
        }

        actions.add(button(player, "buttons.disable", () -> {
            setHidden(player, true);
            player.closeDialog();
            player.sendMessage(languages.message(player, "disabled"));
        }));

        ActionButton closeButton = button(player, "buttons.close", player::closeDialog);
        int columns = Math.max(1, Math.min(4, plugin.getConfig().getInt("button-columns", 2)));

        MultiActionType type = DialogType.multiAction(actions, closeButton, columns);

        final Component finalBody = body;

        Dialog dialog = Dialog.create(builder -> builder.empty()
            .base(DialogBase.builder(languages.component(player, "pages." + pageId + ".title"))
                .canCloseWithEscape(plugin.getConfig().getBoolean("allow-escape", true))
                .body(List.of(DialogBody.plainMessage(finalBody)))
                .build())
            .type(type)
        );

        player.showDialog(dialog);
    }

    private ActionButton button(Player player, String textPath, Runnable runnable) {
        return ActionButton.builder(languages.component(player, textPath))
            .action(DialogAction.customClick(
                (view, audience) -> {
                    if (audience instanceof Player clicked && clicked.getUniqueId().equals(player.getUniqueId())) {
                        runnable.run();
                    }
                },
                ClickCallback.Options.builder().uses(1).build()
            ))
            .build();
    }
}
