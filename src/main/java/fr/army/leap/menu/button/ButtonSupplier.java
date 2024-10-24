package fr.army.leap.menu.button;

import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import org.jetbrains.annotations.NotNull;

public interface ButtonSupplier {

    @NotNull
    Button<? extends AbstractMenuView<?>> get(@NotNull ButtonTemplate buttonTemplate);

}