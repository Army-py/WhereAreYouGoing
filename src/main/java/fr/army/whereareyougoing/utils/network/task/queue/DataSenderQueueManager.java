package fr.army.whereareyougoing.utils.network.task.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DataSenderQueueManager {

    private static final BlockingQueue<DataSenderTask> taskQueue = new LinkedBlockingQueue<>();

    private static final ReentrantLock pauseLock = new ReentrantLock();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void processSingleTask() {
        pauseLock.lock();
        try {
            if (!taskQueue.isEmpty()) {
                DataSenderTask task = taskQueue.take();
                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            pauseLock.unlock();
        }
    }

    public static void enqueueTask(DataSenderTask task) throws InterruptedException {
        taskQueue.put(task);
    }

    public static void shutdown() {
        executorService.shutdownNow();
        taskQueue.clear();
    }

    public static boolean isEmpty() {
        return taskQueue.isEmpty();
    }
}
