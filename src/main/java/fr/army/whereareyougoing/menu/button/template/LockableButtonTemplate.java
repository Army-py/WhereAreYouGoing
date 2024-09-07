package fr.army.whereareyougoing.menu.button.template;

import fr.army.whereareyougoing.menu.button.ButtonItem;
import org.jetbrains.annotations.NotNull;

public class LockableButtonTemplate extends ButtonTemplate {

    private final ButtonItem lockedButtonItem;

    public LockableButtonTemplate(char character, @NotNull ButtonItem buttonItem, @NotNull ButtonItem lockedButtonItem) {
        super(character, buttonItem);
        this.lockedButtonItem = lockedButtonItem;
    }

    public ButtonItem getLockedButtonItem() {
        return lockedButtonItem;
    }
}
