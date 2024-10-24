package fr.army.leap.menu.button;

import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Button<V extends AbstractMenuView<V>> implements ButtonSupplier {

    protected final ButtonTemplate buttonTemplate;
    protected V menuView;

    public Button(ButtonTemplate buttonTemplate) {
        this.buttonTemplate = buttonTemplate;
    }

    public abstract void onClick(InventoryClickEvent clickEvent);


    public void setButtonItem(@NotNull ButtonItem buttonItem) {
        buttonTemplate.setButtonItem(buttonItem);
    }

    @NotNull
    public ButtonTemplate getButtonTemplate() {
        return buttonTemplate;
    }

    @Nullable
    public V getMenuView() {
        return menuView;
    }

    public Button<V> setMenuView(V menuView) {
        this.menuView = menuView;
        return this;
    }

    public ButtonItem getButtonItem() {
        return buttonTemplate.getButtonItem();
    }
}
