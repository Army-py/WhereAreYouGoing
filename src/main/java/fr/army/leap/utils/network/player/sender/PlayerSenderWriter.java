package fr.army.leap.utils.network.player.sender;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerSenderWriter {

    public byte @NotNull [] write(@NotNull String serverName) throws IOException {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        outDataStream.writeUTF("Connect");
        outDataStream.writeUTF(serverName);
        return outByteStream.toByteArray();
    }
}
