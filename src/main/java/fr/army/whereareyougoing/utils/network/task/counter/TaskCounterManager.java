package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.Config;
import org.bukkit.Bukkit;

import java.util.Map;

public class TaskCounterManager {

    private final WhereAreYouGoingPlugin plugin;

    public TaskCounterManager(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    public void startTaskCounterSender() {
        final Map<String, Integer> serversMaxPlayers = Config.serversMaxPlayers;
        final int checkInterval = Config.checkServerCountInterval;

        serversMaxPlayers.forEach((serverName, maxPlayers) -> {
            final TaskCounterSender taskCounterSender = new TaskCounterSender(serverName);
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, taskCounterSender, checkInterval, checkInterval);
        });
    }

}
