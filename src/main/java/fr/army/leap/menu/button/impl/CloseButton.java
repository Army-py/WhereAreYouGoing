package fr.army.leap.menu.button.impl;

import fr.army.leap.menu.button.Button;
import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import fr.army.leap.menu.view.impl.MenuView;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class CloseButton extends Button<MenuView> {

    public CloseButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        clickEvent.getWhoClicked().closeInventory();
    }

    @Override
    public @NotNull Button<? extends AbstractMenuView<?>> get(@NotNull ButtonTemplate buttonTemplate) {
        return new CloseButton(buttonTemplate);
    }
}
