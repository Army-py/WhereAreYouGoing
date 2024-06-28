package fr.army.whereareyougoing.utils.network.task.queue;

import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.utils.network.packet.PlayerPacket;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerSenderQueueManager {

    private final BlockingQueue<DataSenderTask> taskQueue;
    private final Map<UUID, BossBar> bossBars;

    private final ReentrantLock pauseLock;

    private final ExecutorService executorService;

    public PlayerSenderQueueManager() {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.bossBars = new HashMap<>();
        this.pauseLock = new ReentrantLock();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void processSingleTask() {
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

    public void enqueueTask(DataSenderTask task) throws InterruptedException {
        taskQueue.put(task);
    }

    public void shutdown() {
        executorService.shutdownNow();
        taskQueue.clear();
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public DataSenderTask[] getTaskQueue() {
        return taskQueue.toArray(new DataSenderTask[0]);
    }

    public void refreshPositionIndicator() {
        final DataSenderTask[] taskQueue = getTaskQueue();
        for (int i = 0; i < taskQueue.length; i++) {
            final DataSenderTask task = taskQueue[i];
            final PlayerPacket packet = task.getPacket();
            final Player player = packet.getPlayer();

            final BossBar bossBar = Config.waitingDestinationIndicator.createBossBar(i + 1);
            bossBar.setProgress(1.0 - (double) i / taskQueue.length);

            final BossBar retrievedBossBar;
            if ((retrievedBossBar = bossBars.get(player.getUniqueId())) != null) {
                retrievedBossBar.removeAll();
            }

            bossBars.put(player.getUniqueId(), bossBar);
            bossBar.addPlayer(player);
        }
    }
}
