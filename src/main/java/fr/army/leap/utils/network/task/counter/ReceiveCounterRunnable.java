package fr.army.leap.utils.network.task.counter;

import fr.army.leap.LeapPlugin;
import fr.army.leap.cache.impl.ServerCache;
import fr.army.leap.config.Config;
import fr.army.leap.config.DestinationServer;
import fr.army.leap.database.model.impl.ServerModel;
import fr.army.leap.utils.network.player.counter.PlayerCount;
import fr.army.leap.utils.network.player.counter.PlayerCountReader;
import fr.army.leap.utils.network.task.queue.PlayerSenderQueueManager;
import fr.army.leap.utils.network.task.sender.TaskSenderManager;

import java.io.IOException;
import java.util.Map;

public class ReceiveCounterRunnable implements Runnable {

    private final LeapPlugin plugin;
    private final byte[] data;

    public ReceiveCounterRunnable(LeapPlugin plugin, byte[] data) {
        this.plugin = plugin;
        this.data = data;
    }

    @Override
    public void run() {
        final PlayerCountReader orderReader = new PlayerCountReader();

        final PlayerCount playerCount;

        try {
            playerCount = orderReader.write(data);
        } catch (IOException e) {
            return;
        }

        final String serverName = playerCount.serverName();
        final int serverPlayerCount = playerCount.playerCount();

        final Map<String, DestinationServer> destinationServers = Config.servers;
        if (!destinationServers.containsKey(serverName)) return;

        final ServerCache serverCache = plugin.getCacheProvider().getCache(ServerCache.class);
        final TaskSenderManager taskSenderManager = plugin.getTaskSenderManager();
        final PlayerSenderQueueManager playerSender = taskSenderManager.getPlayerSenderQueueManager(serverName);

        if (serverPlayerCount < destinationServers.get(serverName).getMaxPlayers()){
            playerSender.processSingleTask();
        }

        playerSender.refreshPositionIndicator();

        final ServerModel serverModel = serverCache.getCachedObject(serverName);
        if (serverModel != null) {
            serverModel.setPlayerCount(serverPlayerCount);
        }

        serverCache.putCachedObject(serverName, serverModel);
    }
}
