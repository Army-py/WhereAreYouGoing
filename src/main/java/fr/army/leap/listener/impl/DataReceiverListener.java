package fr.army.leap.listener.impl;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.utils.network.task.counter.AsyncCounterReceiver;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class DataReceiverListener implements PluginMessageListener {

    private final WhereAreYouGoingPlugin plugin;

    public DataReceiverListener(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] data) {
        if (!channel.equals("BungeeCord")) return;

        final AsyncCounterReceiver asyncCounterReceiver = new AsyncCounterReceiver();
        asyncCounterReceiver.receiveCounter(plugin, data);
    }
}
