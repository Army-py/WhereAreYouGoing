package fr.army.leap.menu.button.impl;

import fr.army.leap.menu.button.Button;
import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import fr.army.leap.menu.view.impl.MenuView;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class BlankButton extends Button<MenuView> {

    public BlankButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
    }

    @Override
    public @NotNull Button<? extends AbstractMenuView<?>> get(@NotNull ButtonTemplate buttonTemplate) {
        return new BlankButton(buttonTemplate);
    }
}
