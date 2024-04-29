package fr.army.whereareyougoing.menu.button.impl;

import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.impl.MenuView;
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
    public @NotNull Button<MenuView> get(@NotNull ButtonTemplate buttonTemplate) {
        return new BlankButton(buttonTemplate);
    }
}
