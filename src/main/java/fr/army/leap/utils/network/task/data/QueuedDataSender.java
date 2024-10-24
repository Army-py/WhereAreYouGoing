package fr.army.leap.utils.network.task.data;

import fr.army.leap.utils.network.packet.PlayerPacket;
import fr.army.leap.utils.network.task.queue.DataSenderTask;
import fr.army.leap.utils.network.task.queue.PlayerSenderQueueManager;

public class QueuedDataSender {

    public void sendPluginMessage(PlayerSenderQueueManager playerSender, PlayerPacket packet) {
        try {
            final DataSenderTask task = new DataSenderTask(
                    new AsyncDataSender(),
                    (operation) -> operation.sendPluginMessage(packet),
                    packet);
            playerSender.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
