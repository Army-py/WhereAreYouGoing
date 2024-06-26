package fr.army.whereareyougoing.utils.network.channel;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.listener.impl.DataReceiverListener;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public static final String BUNGEECORD_CHANNEL = "BungeeCord";

    public void register(@NotNull WhereAreYouGoingPlugin plugin) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, BUNGEECORD_CHANNEL);
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, BUNGEECORD_CHANNEL, new DataReceiverListener(plugin));
    }

    public void unregister(@NotNull WhereAreYouGoingPlugin plugin) {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, BUNGEECORD_CHANNEL);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, BUNGEECORD_CHANNEL);
    }

}
