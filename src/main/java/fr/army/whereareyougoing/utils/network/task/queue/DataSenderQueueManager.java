package fr.army.whereareyougoing.utils.network.task.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DataSenderQueueManager {

    private static final int QUEUE_CAPACITY = 99;
    private static final BlockingQueue<DataSenderTask> taskQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private static final ReentrantLock pauseLock = new ReentrantLock();
    private static final Condition unpaused = pauseLock.newCondition();
    private static volatile boolean isPaused = true;

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void processSingleTask() {
        pauseLock.lock();
        try {
            if (!taskQueue.isEmpty()) {
                DataSenderTask task = taskQueue.take();
                task.run();
            }
            isPaused = true;
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

    public static void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void shutdown() {
        executorService.shutdownNow();
        taskQueue.clear();
    }

    public static boolean isEmpty() {
        return taskQueue.isEmpty();
    }
}
