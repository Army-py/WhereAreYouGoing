package fr.army.whereareyougoing.utils.network.player.counter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PlayerCountReader {

    public PlayerCount write(byte[] data) throws IOException {
        final DataInputStream inDataStream = new DataInputStream(new ByteArrayInputStream(data));
        final String serverName = inDataStream.readUTF();
        final int playerCount = inDataStream.readInt();

        return new PlayerCount(serverName, playerCount);
    }
}
