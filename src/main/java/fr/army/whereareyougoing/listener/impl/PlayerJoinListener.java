package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class PlayerJoinListener implements Listener {

    private final WhereAreYouGoingPlugin plugin;

    public PlayerJoinListener(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Inventory inventory = player.getInventory();

        // inventory.setItem();
    }
}
