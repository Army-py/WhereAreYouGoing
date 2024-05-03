package fr.army.whereareyougoing.utils.network.task.queue;

import fr.army.whereareyougoing.utils.network.task.sender.AsyncDataSender;

import java.util.function.Consumer;

public class DataSenderTask implements Runnable {

    private final AsyncDataSender asyncDataSender;
    private final Consumer<AsyncDataSender> dataOperation;

    public DataSenderTask(AsyncDataSender asyncDataSender, Consumer<AsyncDataSender> dataOperation) {
        this.asyncDataSender = asyncDataSender;
        this.dataOperation = dataOperation;
    }

    @Override
    public void run() {
        dataOperation.accept(asyncDataSender);
    }
}
