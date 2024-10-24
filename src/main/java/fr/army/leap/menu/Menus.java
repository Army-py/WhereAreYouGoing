package fr.army.leap.menu;

import fr.army.leap.menu.impl.SelectorMenu;
import fr.army.leap.menu.view.impl.MenuView;

public class Menus {

    public static WAYGMenu<MenuView> SELECTOR_MENU;

    public void init() {
        SELECTOR_MENU = SelectorMenu.createInstance("selector_menu");
    }
}
