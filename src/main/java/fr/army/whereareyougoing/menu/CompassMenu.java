package fr.army.whereareyougoing.menu;

import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import fr.army.whereareyougoing.utils.builder.menu.MenuBuilderResult;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public abstract class CompassMenu<T extends AbstractMenuView<T>> {

    protected final MenuBuilderResult<T> menuBuilderResult;

    public CompassMenu(@NotNull MenuBuilderResult<T> menuBuilderResult) {
        this.menuBuilderResult = menuBuilderResult;
    }

    public abstract T createView(Player player);

    public abstract void onClick(InventoryClickEvent clickEvent);


    @NotNull
    public MenuBuilderResult<T> getMenuBuilderResult() {
        return menuBuilderResult;
    }
}