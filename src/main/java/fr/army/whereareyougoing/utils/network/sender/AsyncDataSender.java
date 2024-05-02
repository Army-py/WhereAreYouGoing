package fr.army.whereareyougoing.utils.network.sender;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.utils.network.packet.PlayerSenderPacket;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(PlayerSenderPacket packet) {
        final WhereAreYouGoingPlugin plugin = WhereAreYouGoingPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            packet.getPlayer().sendPluginMessage(
                    plugin,
                    "BungeeCord",
                    packet.writeData()
            );
        });
    }
}
