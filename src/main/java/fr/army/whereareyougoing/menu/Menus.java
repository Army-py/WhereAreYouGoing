package fr.army.whereareyougoing.menu;

import fr.army.whereareyougoing.menu.impl.SelectorMenu;
import fr.army.whereareyougoing.menu.view.impl.MenuView;

public class Menus {

    public static WAYGMenu<MenuView> SELECTOR_MENU;

    public void init() {
        SELECTOR_MENU = SelectorMenu.createInstance("selector_menu");
    }
}
