package fr.army.whereareyougoing.utils.network.packet;

import fr.army.whereareyougoing.utils.network.player.PlayerSenderWriter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PlayerSenderPacket {

    private final Player player;
    private final String serverName;

    public PlayerSenderPacket(@NotNull Player player, @NotNull String serverName) {
        this.player = player;
        this.serverName = serverName;
    }

    public Player getPlayer() {
        return player;
    }

    public byte[] writeData() {
        try {
            return new PlayerSenderWriter().write(serverName);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
