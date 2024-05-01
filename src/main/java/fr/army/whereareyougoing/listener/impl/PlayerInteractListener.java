package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.selector.DestinationSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final WhereAreYouGoingPlugin plugin;

    public PlayerInteractListener(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        final ItemStack item = event.getItem();

        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (item.getItemMeta().getPersistentDataContainer().isEmpty()) return;

        if (!DestinationSelector.isDestinationSelector(item)) return;

        Menus.SELECTOR_MENU.createView(player).open();
    }
}
