package fr.army.whereareyougoing.menu.button;

import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import org.jetbrains.annotations.NotNull;

public interface ButtonSupplier<T extends ButtonTemplate> {

    @NotNull
    Button<? extends AbstractMenuView<?>, T> get(@NotNull T buttonTemplate);

}