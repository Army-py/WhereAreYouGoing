package fr.army.leap.utils.network.packet;

import org.bukkit.entity.Player;

public abstract class PlayerPacket {

    protected final Player player;
    protected final String serverName;

    public PlayerPacket(Player player, String serverName) {
        this.player = player;
        this.serverName = serverName;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract byte[] writeData();
}
