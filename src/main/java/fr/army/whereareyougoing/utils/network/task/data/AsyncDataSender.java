package fr.army.whereareyougoing.utils.network.task.data;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.utils.network.packet.PlayerPacket;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(PlayerPacket packet) {
        final WhereAreYouGoingPlugin plugin = WhereAreYouGoingPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> packet.getPlayer().sendPluginMessage(
                plugin,
                "BungeeCord",
                packet.writeData()
        ));
    }
}
