package fr.army.whereareyougoing.listener.impl;

import fr.army.whereareyougoing.menu.view.impl.MenuView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenusListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || event.getClickedInventory() == null)
            return;

        InventoryHolder inventoryHolder = event.getView().getTopInventory().getHolder();

        if (inventoryHolder instanceof MenuView) {
            event.setCancelled(true);

            if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
                MenuView menuView = (MenuView) inventoryHolder;
                menuView.getMenu().onClick(event);
            }
        }
    }

}
