package fr.army.whereareyougoing.utils.network.task.sender;

import fr.army.whereareyougoing.utils.network.packet.PlayerPacket;
import fr.army.whereareyougoing.utils.network.task.queue.DataSenderQueueManager;
import fr.army.whereareyougoing.utils.network.task.queue.DataSenderTask;

public class QueuedDataSender {

    public void sendPluginMessage(PlayerPacket packet) {
        try {
            final DataSenderTask task = new DataSenderTask(
                    new AsyncDataSender(),
                    (operation) -> operation.sendPluginMessage(packet));
            DataSenderQueueManager.enqueueTask(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
