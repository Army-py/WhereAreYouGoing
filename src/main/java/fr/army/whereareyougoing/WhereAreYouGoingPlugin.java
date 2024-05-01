package fr.army.whereareyougoing;

import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.listener.ListenerLoader;
import fr.army.whereareyougoing.utils.loader.ConfigLoader;
import fr.army.whereareyougoing.utils.loader.exception.UnableLoadConfigException;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhereAreYouGoingPlugin extends JavaPlugin {

    public static WhereAreYouGoingPlugin plugin;

    private ConfigLoader configLoader;
    private Config config;

    @Override
    public void onEnable() {
        plugin = this;

        this.configLoader = new ConfigLoader(this);

        try {
            this.config = new Config(this.configLoader.initFile("config.yml"));
        } catch (UnableLoadConfigException e) {
            getLogger().severe("Unable to load config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        final ListenerLoader listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        getLogger().info("WhereAreYouGoingPlugin enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info("WhereAreYouGoingPlugin disabled");
    }

    public static WhereAreYouGoingPlugin getPlugin() {
        return plugin;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public Config getConfiguration() {
        return config;
    }
}
