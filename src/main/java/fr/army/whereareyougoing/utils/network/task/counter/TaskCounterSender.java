package fr.army.whereareyougoing.utils.network.task.counter;

import fr.army.whereareyougoing.utils.network.packet.impl.PlayerCountPacket;
import fr.army.whereareyougoing.utils.network.task.sender.AsyncDataSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TaskCounterSender implements Runnable {

    private final String serverName;

    public TaskCounterSender(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public void run() {
        final Player randomPlayer = Bukkit.getOnlinePlayers().stream().findAny().orElse(null);
        if (randomPlayer == null) return;

        final AsyncDataSender asyncDataSender = new AsyncDataSender();
        final PlayerCountPacket playerCountPacket = new PlayerCountPacket(randomPlayer, serverName);
        asyncDataSender.sendPluginMessage(playerCountPacket);
    }
}
