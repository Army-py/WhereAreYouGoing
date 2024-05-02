package fr.army.whereareyougoing.utils.network.channel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public static final String BUNGEECORD_CHANNEL = "BungeeCord";

    public void register(@NotNull Plugin plugin) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, BUNGEECORD_CHANNEL);
    }

    public void unregister(@NotNull Plugin plugin) {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, BUNGEECORD_CHANNEL);
    }

}
