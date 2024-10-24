package fr.army.leap.menu.impl;

import fr.army.leap.menu.LeapMenu;
import fr.army.leap.menu.view.impl.MenuView;
import fr.army.leap.utils.builder.menu.MenuBuilder;
import fr.army.leap.utils.builder.menu.MenuBuilderResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectorMenu extends LeapMenu<MenuView> {

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
