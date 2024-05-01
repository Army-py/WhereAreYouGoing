package fr.army.whereareyougoing.menu.button;

import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;

public abstract class ComponentButton<T extends AbstractMenuView<T>, C> extends Button<T> {
    public ComponentButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    public abstract void replacePlaceholders(C component);
}
