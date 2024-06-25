package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.config.DestinationServer;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCount;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCountReader;
import fr.army.whereareyougoing.utils.network.task.queue.PlayerSenderQueueManager;
import fr.army.whereareyougoing.utils.network.task.sender.TaskSenderManager;

import java.io.IOException;
import java.util.Map;

public class ReceiveCounterRunnable implements Runnable {

    private final WhereAreYouGoingPlugin plugin;
    private final byte[] data;

    public ReceiveCounterRunnable(WhereAreYouGoingPlugin plugin, byte[] data) {
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

        final TaskCounterManager taskCounterManager = plugin.getTaskCounterManager();
        final TaskSenderManager taskSenderManager = plugin.getTaskSenderManager();
        final PlayerSenderQueueManager playerSender = taskSenderManager.getPlayerSenderQueueManager(serverName);

        System.out.println("Server: " + serverName + " - Players: " + serverPlayerCount);
        // System.out.println(destinationServers.get(serverName).getMaxPlayers());
        if (serverPlayerCount < destinationServers.get(serverName).getMaxPlayers()){
            System.out.println("Server: " + serverName + " - Players: " + serverPlayerCount + " - Max: " + destinationServers.get(serverName).getMaxPlayers() + " - Sending task");
            playerSender.processSingleTask();

            if (playerSender.isEmpty()){
                taskCounterManager.stopTaskCounterChecker(serverName);
                System.out.println("Task counter stopped");
            }
        }else{
            if (taskCounterManager.isEmpty()) {
                taskCounterManager.startTaskCounterChecker();
                System.out.println("Task counter started");
            }
        }
        playerSender.refreshPositionIndicator();
    }
}
