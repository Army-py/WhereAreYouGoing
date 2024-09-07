package fr.army.whereareyougoing.menu.button.impl;

import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import fr.army.whereareyougoing.menu.view.impl.MenuView;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class CloseButton extends Button<MenuView, ButtonTemplate> {

    public CloseButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        clickEvent.getWhoClicked().closeInventory();
    }

    @Override
    public @NotNull Button<? extends AbstractMenuView<?>, ButtonTemplate> get(@NotNull ButtonTemplate buttonTemplate) {
        return new CloseButton(buttonTemplate);
    }
}
