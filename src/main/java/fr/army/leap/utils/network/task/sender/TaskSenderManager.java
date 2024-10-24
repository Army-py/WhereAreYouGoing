package fr.army.leap.utils.network.task.sender;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.config.Config;
import fr.army.leap.utils.network.task.queue.PlayerSenderQueueManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TaskSenderManager {

    private final WhereAreYouGoingPlugin plugin;
    private final Map<String, PlayerSenderQueueManager> playerSenderQueueManagerMap;

    public TaskSenderManager(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
        this.playerSenderQueueManagerMap = new HashMap<>();
    }

    public void startTasksSender() {
        final Set<String> servers = Config.servers.keySet();

        servers.forEach(server -> {
            final PlayerSenderQueueManager playerSenderQueueManager = new PlayerSenderQueueManager();
            playerSenderQueueManagerMap.put(server, playerSenderQueueManager);
        });
    }

    public PlayerSenderQueueManager getPlayerSenderQueueManager(String serverName) {
        return playerSenderQueueManagerMap.get(serverName);
    }
}
