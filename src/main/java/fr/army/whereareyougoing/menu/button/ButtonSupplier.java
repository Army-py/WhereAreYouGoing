package fr.army.whereareyougoing.menu.button;

import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import org.jetbrains.annotations.NotNull;

public interface ButtonSupplier {

    @NotNull
    Button<? extends AbstractMenuView<?>> get(@NotNull ButtonTemplate buttonTemplate);

}