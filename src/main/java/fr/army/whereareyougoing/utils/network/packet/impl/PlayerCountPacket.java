package fr.army.whereareyougoing.utils.network.packet.impl;

import fr.army.whereareyougoing.utils.network.packet.PlayerPacket;
import fr.army.whereareyougoing.utils.network.player.counter.PlayerCountWriter;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PlayerCountPacket extends PlayerPacket {

    public PlayerCountPacket(Player player, String serverName) {
        super(player, serverName);
    }

    public byte[] writeData() {
        try {
            return new PlayerCountWriter().write(serverName);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
