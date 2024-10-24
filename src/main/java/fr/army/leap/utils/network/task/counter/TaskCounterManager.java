package fr.army.leap.utils.network.task.counter;

import fr.army.leap.LeapPlugin;
import fr.army.leap.config.Config;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskCounterManager {

    private final LeapPlugin plugin;

    private final Map<String, BukkitRunnable> tasks;

    public TaskCounterManager(LeapPlugin plugin) {
        this.plugin = plugin;
        this.tasks = new ConcurrentHashMap<>();
    }

    public void startTaskCounterChecker(String serverName) {
        final int checkInterval = Config.checkServerCountInterval;

        final TaskCounterSender taskCounterSender = new TaskCounterSender(serverName);
        tasks.put(serverName, taskCounterSender);
        taskCounterSender.runTaskTimerAsynchronously(plugin, checkInterval, checkInterval);
    }

    public void stopTaskCounterChecker(String serverName) {
        final BukkitRunnable task = tasks.get(serverName);
        if (task != null) {
            task.cancel();
            tasks.remove(serverName);
        }
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

}