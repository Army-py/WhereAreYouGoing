package fr.army.leap.database.repository.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseTaskQueueManager {

    private static final int QUEUE_CAPACITY = 99;
    private static final BlockingQueue<DatabaseTask> taskQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    static {
        startService();
    }

    private static void startService() {
        executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    taskQueue.take().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public static void enqueueTask(DatabaseTask task) throws InterruptedException {
        taskQueue.put(task);
    }

    public static void shutdown() {
        executorService.shutdownNow();
        taskQueue.clear();
    }
}
