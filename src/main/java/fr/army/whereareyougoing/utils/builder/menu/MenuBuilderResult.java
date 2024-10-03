package fr.army.whereareyougoing.utils.builder.menu;

import fr.army.whereareyougoing.menu.template.MenuTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
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
