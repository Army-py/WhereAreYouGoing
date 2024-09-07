package fr.army.whereareyougoing.menu.button;

import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Button<V extends AbstractMenuView<V>, T extends ButtonTemplate> implements ButtonSupplier<T> {

    protected final T buttonTemplate;

    protected V menuView;

    public Button(T buttonTemplate) {
        this.buttonTemplate = buttonTemplate;
    }

    public abstract void onClick(InventoryClickEvent clickEvent);


    public Button<V, T> setButtonItem(@NotNull ButtonItem buttonItem) {
        buttonTemplate.setButtonItem(buttonItem);
        return this;
    }

    @NotNull
    public T getButtonTemplate() {
        return buttonTemplate;
    }

    @Nullable
    public V getMenuView() {
        return menuView;
    }

    public Button<V, T> setMenuView(V menuView) {
        this.menuView = menuView;
        return this;
    }

    // protected void openPreviousMenu(Optional<Team> team){
    //     final MenuTemplate<T> menuTemplate = menuView.getMenu().getMenuBuilderResult().getMenuTemplate();
    //     final Player viewer = menuView.getViewer();
    //     if (menuTemplate.canPrecede() && menuTemplate.getPrecedingMenu() != null){
    //         menuTemplate.getPrecedingMenu().createView(viewer, team).open();
    //     }else{
    //         viewer.closeInventory();
    //     }
    // }
}
