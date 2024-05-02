package fr.army.whereareyougoing.utils.network.player;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerCountWriter {

    public byte @NotNull [] write(@NotNull String serverName) throws IOException {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        outDataStream.writeUTF("PlayerCount");
        outDataStream.writeUTF(serverName);
        return outByteStream.toByteArray();
    }
}
