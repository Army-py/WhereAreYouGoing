package fr.army.whereareyougoing;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.library.LibrarySetup;
import fr.army.whereareyougoing.listener.ListenerLoader;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.utils.loader.ConfigLoader;
import fr.army.whereareyougoing.utils.network.channel.ChannelRegistry;
import fr.army.whereareyougoing.utils.network.task.counter.TaskCounterManager;
import fr.army.whereareyougoing.utils.network.task.sender.TaskSenderManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhereAreYouGoingPlugin extends JavaPlugin {

    public static WhereAreYouGoingPlugin plugin;

    private LibrarySetup librarySetup;
    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private TaskSenderManager taskSenderManager;
    private TaskCounterManager taskCounterManager;

    @Override
    public void onEnable() {
        plugin = this;

        librarySetup = new LibrarySetup(this);
        librarySetup.loadLibraries();

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        this.configLoader = new ConfigLoader(this);

        try {
            this.config = new Config(this.configLoader.initFile("config.yml"));
            this.config.load();
        } catch (Exception e) {
            getLogger().severe("Unable to load config.yml");
            getLogger().severe(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        final Menus menus = new Menus();
        menus.init();

        final ListenerLoader listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        taskSenderManager = new TaskSenderManager(this);
        taskSenderManager.startTasksSender();

        taskCounterManager = new TaskCounterManager(this);

        if (Via.getAPI() == null) {
            getLogger().severe("ViaVersion is not installed");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

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

    public LibrarySetup getLibrarySetup() {
        return librarySetup;
    }

    public ChannelRegistry getChannelRegistry() {
        return channelRegistry;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public Config getConfiguration() {
        return config;
    }

    public TaskSenderManager getTaskSenderManager() {
        return taskSenderManager;
    }

    public TaskCounterManager getTaskCounterManager() {
        return taskCounterManager;
    }

    public ViaAPI<?> getViaAPI() {
        return Via.getAPI();
    }
}
