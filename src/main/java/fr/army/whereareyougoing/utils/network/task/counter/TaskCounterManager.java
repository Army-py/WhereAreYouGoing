package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.config.DestinationServer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskCounterManager {

    private final WhereAreYouGoingPlugin plugin;

    private final Map<String, BukkitRunnable> tasks;

    public TaskCounterManager(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
        this.tasks = new ConcurrentHashMap<>();
    }

    public void startTaskCounterChecker() {
        final Map<String, DestinationServer> destinationServers = Config.servers;
        final int checkInterval = Config.checkServerCountInterval;

        destinationServers.forEach((serverName, destServer) -> {
            final TaskCounterSender taskCounterSender = new TaskCounterSender(serverName);
            tasks.put(serverName, taskCounterSender);
            taskCounterSender.runTaskTimerAsynchronously(plugin, checkInterval, checkInterval);
        });
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