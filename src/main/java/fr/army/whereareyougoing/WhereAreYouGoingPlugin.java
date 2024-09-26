package fr.army.whereareyougoing;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import fr.army.whereareyougoing.cache.CacheProvider;
import fr.army.whereareyougoing.cache.impl.ServerCache;
import fr.army.whereareyougoing.command.CommandManager;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.config.Database;
import fr.army.whereareyougoing.database.EMFLoader;
import fr.army.whereareyougoing.database.exception.impl.DatabaseConnectionException;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.database.repository.RepositoryProvider;
import fr.army.whereareyougoing.database.repository.impl.ServerRepository;
import fr.army.whereareyougoing.library.LibrarySetup;
import fr.army.whereareyougoing.listener.ListenerLoader;
import fr.army.whereareyougoing.menu.Menus;
import fr.army.whereareyougoing.utils.loader.ConfigLoader;
import fr.army.whereareyougoing.utils.network.channel.ChannelRegistry;
import fr.army.whereareyougoing.utils.network.task.counter.TaskCounterManager;
import fr.army.whereareyougoing.utils.network.task.sender.TaskSenderManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class WhereAreYouGoingPlugin extends JavaPlugin {

    public static WhereAreYouGoingPlugin plugin;

    private LibrarySetup librarySetup;
    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private Database database;
    private TaskSenderManager taskSenderManager;
    private TaskCounterManager taskCounterManager;
    private EMFLoader emfLoader = null;
    private RepositoryProvider repositoryProvider;
    private CommandManager commandManager;
    private CacheProvider cacheProvider;

    @Override
    public void onEnable() {
        plugin = this;

        librarySetup = new LibrarySetup(this);
        librarySetup.loadLibraries();

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        configLoader = new ConfigLoader(this);

        try {
            database = new Database(configLoader.initFile("database.yml"));
            database.load();
        } catch (Exception e) {
            getLogger().severe("Unable to load database.yml");
            getLogger().severe(e.getMessage());
            getLogger().severe(Arrays.toString(e.getStackTrace()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        emfLoader = new EMFLoader();
        try {
            emfLoader.setupEntityManagerFactory(getDataFolder().getPath());
        } catch (DatabaseConnectionException e) {
            getLogger().severe("Unable to connect to the database");
            getLogger().severe(e.getCause().getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        repositoryProvider = new RepositoryProvider(emfLoader);
        cacheProvider = new CacheProvider();

        try {
            config = new Config(configLoader.initFile("config.yml"));
            config.load();
        } catch (Exception e) {
            getLogger().severe("Unable to load config.yml");
            getLogger().severe(e.getMessage());
            getLogger().severe(Arrays.toString(e.getStackTrace()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        final Menus menus = new Menus();
        menus.init();

        final ListenerLoader listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        if (Via.getAPI() == null) {
            getLogger().severe("ViaVersion is not installed");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        commandManager = new CommandManager(this);

        taskSenderManager = new TaskSenderManager(this);
        taskCounterManager = new TaskCounterManager(this);
        taskSenderManager.startTasksSender();

        // Save servers in database and store in cache
        final ServerRepository serverRepository = repositoryProvider.getRepository(ServerRepository.class);
        final ServerCache serverCache = cacheProvider.getCache(ServerCache.class);
        for (String serverName : Config.servers.keySet()) {
            serverRepository.getFromServerName(serverName, serverModel -> {
                if (serverModel == null) {
                    final ServerModel model = new ServerModel();
                    model.setName(serverName);
                    model.setMaintenance(false);
                    serverRepository.save(model);
                }
                serverCache.putCachedObject(serverName, serverModel);
            });

            taskCounterManager.startTaskCounterChecker(serverName);
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

    public EMFLoader getEMFLoader() {
        return emfLoader;
    }

    public RepositoryProvider getRepositoryProvider() {
        return repositoryProvider;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public ViaAPI<?> getViaAPI() {
        return Via.getAPI();
    }
}
