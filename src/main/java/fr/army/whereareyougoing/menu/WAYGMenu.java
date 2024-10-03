package fr.army.whereareyougoing.menu;

import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.template.MenuTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import fr.army.whereareyougoing.utils.builder.menu.MenuBuilderResult;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public abstract class WAYGMenu<T extends AbstractMenuView<T>> {

    protected final MenuBuilderResult<T> menuBuilderResult;

    public WAYGMenu(@NotNull MenuBuilderResult<T> menuBuilderResult) {
        this.menuBuilderResult = menuBuilderResult;
    }

    public abstract T createView(Player player);

    public void onClick(InventoryClickEvent clickEvent){
        final MenuTemplate<T> menuTemplate = this.menuBuilderResult.menuTemplate();
        final int slot = clickEvent.getSlot();
        final Button<T> button = menuTemplate.getButton(slot);

        button.onClick(clickEvent);
    }


    @NotNull
    public MenuBuilderResult<T> getMenuBuilderResult() {
        return menuBuilderResult;
    }
}