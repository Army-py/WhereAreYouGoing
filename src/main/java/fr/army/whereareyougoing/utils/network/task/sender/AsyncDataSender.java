package fr.army.whereareyougoing.utils.network.task.sender;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.utils.network.packet.impl.PlayerSenderPacket;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(PlayerSenderPacket packet) {
        final WhereAreYouGoingPlugin plugin = WhereAreYouGoingPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> packet.getPlayer().sendPluginMessage(
                plugin,
                "BungeeCord",
                packet.writeData()
        ));
    }
}
