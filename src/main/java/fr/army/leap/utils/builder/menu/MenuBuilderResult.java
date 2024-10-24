package fr.army.leap.utils.builder.menu;

import fr.army.leap.menu.template.MenuTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record MenuBuilderResult<T extends AbstractMenuView<T>>(MenuTemplate<T> menuTemplate, YamlConfiguration config) {

    @Override
    @NotNull
    public MenuTemplate<T> menuTemplate() {
        return menuTemplate;
    }

    @Override
    @Nullable
    public YamlConfiguration config() {
        return config;
    }
}
