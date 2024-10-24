package fr.army.leap.listener.impl;

import fr.army.leap.config.Config;
import fr.army.leap.config.DefaultSelectedSlot;
import fr.army.leap.config.DestinationSelector;
import fr.army.leap.menu.Menus;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SelectorListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();

        if (Config.clearInventoryOnJoin){
            inventory.clear();
        }

        final DestinationSelector destinationSelector = Config.destinationSelector;
        inventory.setItem(destinationSelector.getSlot(), destinationSelector.getButtonItem());

        final DefaultSelectedSlot defaultSelectedSlot = Config.defaultSelectedSlot;
        if (defaultSelectedSlot.enabled()) {
            inventory.setHeldItemSlot(defaultSelectedSlot.slot());
        }
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
