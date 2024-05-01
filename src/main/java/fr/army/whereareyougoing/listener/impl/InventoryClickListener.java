package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.selector.DestinationSelector;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final WhereAreYouGoingPlugin plugin;

    public InventoryClickListener(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        final ItemStack item = event.getCurrentItem();

        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (item.getItemMeta().getPersistentDataContainer().isEmpty()) return;

        if (DestinationSelector.isDestinationSelector(item)) {
            event.setCancelled(true);
        }
    }
}
