package fr.army.whereareyougoing.menu.button.impl;

import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import fr.army.whereareyougoing.menu.view.impl.MenuView;
import fr.army.whereareyougoing.utils.network.packet.impl.PlayerSenderPacket;
import fr.army.whereareyougoing.utils.network.task.sender.AsyncDataSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class ServerSelectorButton extends Button<MenuView> {

    public ServerSelectorButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        final Player player = (Player) clickEvent.getWhoClicked();
        final String serverName = buttonTemplate.getButtonItem().getMetadata().get("server");

        if (serverName == null) return;

        final AsyncDataSender asyncDataSender = new AsyncDataSender();
        final PlayerSenderPacket playerSenderPacket = new PlayerSenderPacket(player, serverName);

        player.closeInventory();
        asyncDataSender.sendPluginMessage(playerSenderPacket);
    }

    @Override
    public @NotNull Button<? extends AbstractMenuView<?>> get(@NotNull ButtonTemplate buttonTemplate) {
        return new ServerSelectorButton(buttonTemplate);
    }
}
