package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCount;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCountReader;
import fr.army.whereareyougoing.utils.network.task.queue.DataSenderQueueManager;

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

            if (playerCount.playerCount() < 60)
                DataSenderQueueManager.processSingleTask();

        } catch (Exception e) {
            plugin.getLogger().severe("Error while reading player count data");
        }
    }
}
