package fr.army.whereareyougoing.config;

public class DestinationServer {

    private final String serverName;
    private final int maxPlayers;
    private final int maxProtocolVersion;
    private final int minProtocolVersion;

    public DestinationServer(String serverName, int maxPlayers, int maxProtocolVersion, int minProtocolVersion) {
        this.serverName = serverName;
        this.maxPlayers = maxPlayers;
        this.maxProtocolVersion = maxProtocolVersion;
        this.minProtocolVersion = minProtocolVersion;
    }

    public String getServerName() {
        return serverName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMaxProtocolVersion() {
        return maxProtocolVersion;
    }

    public int getMinProtocolVersion() {
        return minProtocolVersion;
    }
}
