package fr.army.leap.menu.button.template;

import fr.army.leap.menu.button.ButtonItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ButtonTemplate {

    private final char character;
    private ButtonItem buttonItem;

    private final Map<String, ButtonItem> stateButtonItems = new HashMap<>();

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

    public void addState(String state, @NotNull ButtonItem stateButtonItem) {
        stateButtonItems.put(state.toLowerCase(), stateButtonItem);
    }

    @NotNull
    public ButtonItem getStateButtonItem(String state) {
        return stateButtonItems.get(state.toLowerCase());
    }

    public boolean hasState(String state) {
        return stateButtonItems.containsKey(state.toLowerCase());
    }
}
