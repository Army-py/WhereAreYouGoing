package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
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
        final Map<String, Integer> serversMaxPlayers = Config.serversMaxPlayers;
        final int checkInterval = Config.checkServerCountInterval;

        serversMaxPlayers.forEach((serverName, maxPlayers) -> {
            final TaskCounterSender taskCounterSender = new TaskCounterSender(serverName);
            tasks.add(taskCounterSender);
            taskCounterSender.runTaskTimerAsynchronously(plugin, checkInterval, checkInterval);
        });
    }

    public void stopTaskCounterChecker() {
        tasks.forEach(BukkitRunnable::cancel);
        tasks.clear();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

}
