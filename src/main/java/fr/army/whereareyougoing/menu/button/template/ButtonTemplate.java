package fr.army.whereareyougoing.menu.button.template;

import fr.army.whereareyougoing.menu.button.ButtonItem;
import org.jetbrains.annotations.NotNull;

public class ButtonTemplate {

    private final char character;
    private ButtonItem buttonItem;

    public ButtonTemplate(char character, @NotNull ButtonItem buttonItem) {
        this.character = character;
        this.buttonItem = buttonItem;
    }

    public char getCharacter() {
        return character;
    }

    @NotNull
    public ButtonItem getButtonItem() {
        return buttonItem;
    }

    public ButtonTemplate setButtonItem(@NotNull ButtonItem buttonItem) {
        this.buttonItem = buttonItem;
        return this;
    }
}
