package fr.army.leap.menu.button;

import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.view.AbstractMenuView;

public abstract class ComponentButton<T extends AbstractMenuView<T>, C> extends Button<T> {
    public ComponentButton(ButtonTemplate buttonTemplate) {
        super(buttonTemplate);
    }

    public abstract void replacePlaceholders(C component);
}
