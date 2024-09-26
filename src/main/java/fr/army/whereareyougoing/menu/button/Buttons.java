package fr.army.whereareyougoing.menu.button;

import fr.army.whereareyougoing.menu.button.impl.BlankButton;
import fr.army.whereareyougoing.menu.button.impl.CloseButton;
import fr.army.whereareyougoing.menu.button.impl.ServerSelectorButton;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public enum Buttons {

    BUTTON_BLANK(BlankButton::new),
    BUTTON_SERVER_SELECTOR(ServerSelectorButton::new),
    BUTTON_CLOSE(CloseButton::new),
    ;

    private final Function<ButtonTemplate, ? extends Button<?>> buttonSupplier;

    Buttons(Function<ButtonTemplate, Button<?>> buttonSupplier) {
        this.buttonSupplier = buttonSupplier;
    }

    @Nullable
    public Button<?> createButton(@NotNull ButtonTemplate buttonTemplate){
         try {
             return buttonSupplier.apply(buttonTemplate);
         } catch (Exception e) {
             return null;
         }
    }

    @NotNull
    public static Buttons getButtonType(@Nullable String name, boolean isComponent) {
        if (name == null) return BUTTON_BLANK;

        final String buttonType;
        if (isComponent)
            buttonType = "COMPONENT_BUTTON_" + name.toUpperCase();
        else
            buttonType = "BUTTON_" + name.toUpperCase();

        try {
            return Buttons.valueOf(buttonType);
        } catch (IllegalArgumentException e) {
            return BUTTON_BLANK;
        }
    }

}