package fr.army.leap.utils.network.task.data;

import fr.army.leap.LeapPlugin;
import fr.army.leap.utils.network.packet.PlayerPacket;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(PlayerPacket packet) {
        final LeapPlugin plugin = LeapPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> packet.getPlayer().sendPluginMessage(
                plugin,
                "BungeeCord",
                packet.writeData()
        ));
    }
}
