package fr.army.whereareyougoing.utils.loader;

import fr.army.whereareyougoing.utils.loader.exception.UnableLoadConfigException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class ConfigLoader {

    private final Plugin plugin;


    public ConfigLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public YamlConfiguration initFile(@NotNull String fileName) throws UnableLoadConfigException {
        plugin.saveDefaultConfig();
        final File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                Files.copy(Objects.requireNonNull(plugin.getResource(fileName)), file.toPath());
            } catch (IOException ignored) {
                throw new UnableLoadConfigException(fileName, "Unable to copy file from resources");
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
