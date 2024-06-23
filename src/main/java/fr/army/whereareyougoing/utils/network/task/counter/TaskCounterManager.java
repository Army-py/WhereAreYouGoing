package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.config.DestinationServer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskCounterManager {

    private final WhereAreYouGoingPlugin plugin;

    private final List<BukkitRunnable> tasks;

    public TaskCounterManager(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
        this.tasks = new ArrayList<>();
    }

    public void startTaskCounterChecker() {
        final Map<String, DestinationServer> destinationServers = Config.servers;
        final int checkInterval = Config.checkServerCountInterval;

        destinationServers.forEach((serverName, destServer) -> {
            final TaskCounterSender taskCounterSender = new TaskCounterSender(serverName);
            tasks.add(taskCounterSender);
            taskCounterSender.runTaskTimerAsynchronously(plugin, checkInterval, checkInterval);
        });
    }

    public void stopTaskCounterChecker() {
        for (BukkitRunnable task : tasks) {
            if (task != null) {
                task.cancel();
            }
        }
        tasks.clear();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

}
