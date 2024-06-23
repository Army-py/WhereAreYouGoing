package fr.army.whereareyougoing;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.listener.ListenerLoader;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.utils.loader.ConfigLoader;
import fr.army.whereareyougoing.utils.network.channel.ChannelRegistry;
import fr.army.whereareyougoing.utils.network.task.counter.TaskCounterManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhereAreYouGoingPlugin extends JavaPlugin {

    public static WhereAreYouGoingPlugin plugin;

    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private TaskCounterManager taskCounterManager;
    private ViaAPI<?> viaAPI;

    @Override
    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

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

        taskCounterManager = new TaskCounterManager(this);
        taskCounterManager.startTaskCounterChecker();

        this.viaAPI = Via.getAPI();
        if (viaAPI == null) {
            getLogger().severe("ViaVersion is not installed");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println(viaAPI);

        getLogger().info("WhereAreYouGoingPlugin enabled");
    }

    @Override
    public void onDisable() {

        channelRegistry.unregister(this);

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

    public TaskCounterManager getTaskCounterManager() {
        return taskCounterManager;
    }

    public ViaAPI<?> getViaAPI() {
        return Via.getAPI();
    }
}
