package fr.army.leap.menu.view.impl;

import fr.army.leap.menu.LeapMenu;
import fr.army.leap.menu.button.Button;
import fr.army.leap.menu.template.MenuTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuView extends AbstractMenuView<MenuView> {

    public MenuView(Player player, LeapMenu<MenuView> menu) {
        super(player, menu);
    }

    @Override
    public Inventory createInventory() {
        final MenuTemplate<MenuView> menuTemplate = menu.getMenuBuilderResult().menuTemplate();
        final Inventory inventory = Bukkit.createInventory(this, menuTemplate.getSize(), menuTemplate.getTitle());

        for (int slot = 0; slot < menuTemplate.getSize(); slot++) {
            final Button<MenuView> button = menuTemplate.getButton(slot).setMenuView(this);
            final ItemStack itemStack = button.getButtonItem().build();
            inventory.setItem(slot, itemStack);
        }

        return inventory;
    }
}
