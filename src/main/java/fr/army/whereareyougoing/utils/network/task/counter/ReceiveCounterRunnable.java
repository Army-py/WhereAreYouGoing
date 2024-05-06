package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCount;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCountReader;
import fr.army.whereareyougoing.utils.network.task.queue.DataSenderQueueManager;

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

        try {
            final PlayerCount playerCount = orderReader.write(data);
            final String serverName = playerCount.serverName();
            final int serverPlayerCount = playerCount.playerCount();

            final Map<String, Integer> serversMaxPlayers = Config.serversMaxPlayers;

            if (!serversMaxPlayers.containsKey(serverName)) return;

            if (serverPlayerCount < serversMaxPlayers.get(serverName))
                DataSenderQueueManager.processSingleTask();

        } catch (Exception e) {
            plugin.getLogger().severe("Error while reading player count data");
        }
    }
}
