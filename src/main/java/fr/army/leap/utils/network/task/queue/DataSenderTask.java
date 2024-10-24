package fr.army.leap.utils.network.task.queue;

import fr.army.leap.utils.network.packet.PlayerPacket;
import fr.army.leap.utils.network.task.data.AsyncDataSender;

import java.util.function.Consumer;

public class DataSenderTask implements Runnable {

    private final AsyncDataSender asyncDataSender;
    private final Consumer<AsyncDataSender> dataOperation;
    private final PlayerPacket packet;

    public DataSenderTask(AsyncDataSender asyncDataSender, Consumer<AsyncDataSender> dataOperation, PlayerPacket packet) {
        this.asyncDataSender = asyncDataSender;
        this.dataOperation = dataOperation;
        this.packet = packet;
    }

    @Override
    public void run() {
        dataOperation.accept(asyncDataSender);
    }

    public PlayerPacket getPacket() {
        return packet;
    }
}
