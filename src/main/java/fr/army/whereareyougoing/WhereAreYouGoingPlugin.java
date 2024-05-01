package fr.army.whereareyougoing;

import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.listener.ListenerLoader;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.utils.loader.ConfigLoader;
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
            this.config.load();
        } catch (Exception e) {
            getLogger().severe("Unable to load config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        final Menus menus = new Menus();
        menus.init();

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
