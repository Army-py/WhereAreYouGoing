package fr.army.whereareyougoing.utils.network.packet.impl;

import fr.army.whereareyougoing.utils.network.packet.PlayerPacket;
import fr.army.whereareyougoing.utils.network.player.PlayerSenderWriter;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PlayerSenderPacket extends PlayerPacket {


    public PlayerSenderPacket(Player player, String serverName) {
        super(player, serverName);
    }

    public byte[] writeData() {
        try {
            return new PlayerSenderWriter().write(serverName);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
