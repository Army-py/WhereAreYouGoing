package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.selector.DestinationSelector;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectorListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Inventory inventory = player.getInventory();

        if (Config.clearInventoryOnJoin){
            inventory.clear();
        }

        final DestinationSelector destinationSelector = Config.destinationSelector;
        inventory.setItem(destinationSelector.getSlot(), destinationSelector.getButtonItem());
    }

    @EventHandler
    public void onSelectorInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().name().contains("LEFT")) return;

        final ItemStack item = event.getItem();

        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (item.getItemMeta().getPersistentDataContainer().isEmpty()) return;

        if (!DestinationSelector.isDestinationSelector(item)) return;

        Menus.SELECTOR_MENU.createView(player).open();
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

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        final ItemStack item = event.getItemDrop().getItemStack();

        if (item.getItemMeta() == null) return;
        if (item.getItemMeta().getPersistentDataContainer().isEmpty()) return;

        if (DestinationSelector.isDestinationSelector(item)) {
            event.setCancelled(true);
        }
    }
}
