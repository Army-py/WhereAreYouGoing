package fr.army.leap;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import fr.army.leap.cache.CacheProvider;
import fr.army.leap.cache.impl.ServerCache;
import fr.army.leap.command.CommandManager;
import fr.army.leap.config.Config;
import fr.army.leap.config.Database;
import fr.army.leap.database.EMFLoader;
import fr.army.leap.database.exception.impl.DatabaseConnectionException;
import fr.army.leap.database.model.impl.ServerModel;
import fr.army.leap.database.repository.RepositoryProvider;
import fr.army.leap.database.repository.impl.ServerRepository;
import fr.army.leap.external.ExternalManager;
import fr.army.leap.library.LibrarySetup;
import fr.army.leap.listener.ListenerLoader;
import fr.army.leap.menu.Menus;
import fr.army.leap.utils.loader.ConfigLoader;
import fr.army.leap.utils.loader.exception.UnableLoadConfigException;
import fr.army.leap.utils.network.channel.ChannelRegistry;
import fr.army.leap.utils.network.task.counter.TaskCounterManager;
import fr.army.leap.utils.network.task.sender.TaskSenderManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class LeapPlugin extends JavaPlugin {

    public static LeapPlugin plugin;

    private LibrarySetup librarySetup;
    private ExternalManager externalManager;
    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private Database database;
    private YamlConfiguration messages;
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

        externalManager = new ExternalManager();
        externalManager.load();

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
        } catch (UnableLoadConfigException e) {
            getLogger().severe("Unable to load config.yml");
            getLogger().severe(e.getMessage());
            getLogger().severe(Arrays.toString(e.getStackTrace()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            this.messages = this.configLoader.initFile("lang/" + Config.language + ".yml");
        } catch (UnableLoadConfigException e) {
            getLogger().severe("Unable to load " + Config.language + ".yml");
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

        // Save servers in the database and store in cache
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

        getLogger().info("Leap enabled");
    }

    @Override
    public void onDisable() {

        channelRegistry.unregister(this);

        if (externalManager != null) {
            externalManager.unload();
        }

        getLogger().info("Leap disabled");
    }

    public static LeapPlugin getPlugin() {
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

    public Database getDatabase() {
        return database;
    }

    public YamlConfiguration getMessages() {
        return messages;
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
