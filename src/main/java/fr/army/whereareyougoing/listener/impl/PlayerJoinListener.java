package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.selector.DestinationSelector;
import fr.army.whereareyougoing.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    private final WhereAreYouGoingPlugin plugin;

    public PlayerJoinListener(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Inventory inventory = player.getInventory();

        final DestinationSelector destinationSelector = Config.destinationSelector;

        inventory.setItem(4, destinationSelector.getButtonItem());

    }
}
