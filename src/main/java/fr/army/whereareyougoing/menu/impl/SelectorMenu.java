package fr.army.whereareyougoing.menu.impl;

import fr.army.whereareyougoing.menu.WAYGMenu;
import fr.army.whereareyougoing.menu.view.impl.MenuView;
import fr.army.whereareyougoing.utils.builder.menu.MenuBuilder;
import fr.army.whereareyougoing.utils.builder.menu.MenuBuilderResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectorMenu extends WAYGMenu<MenuView> {

    private SelectorMenu(@NotNull MenuBuilderResult<MenuView> menuBuilderResult) {
        super(menuBuilderResult);
    }

    @Override
    public MenuView createView(Player player) {
        return new MenuView(player, this);
    }

    public static SelectorMenu createInstance(String configName){
        final MenuBuilderResult<MenuView> builderResult = MenuBuilder.getInstance().loadMenu(configName + ".yml");
        return new SelectorMenu(builderResult);
    }
}
