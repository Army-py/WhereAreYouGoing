package fr.army.whereareyougoing.config;

public class DestinationServer {

    private final String serverName;
    private final int maxPlayers;
    private final DestinationProtocol destinationProtocol;

    public DestinationServer(String serverName, int maxPlayers, DestinationProtocol destinationProtocol) {
        this.serverName = serverName;
        this.maxPlayers = maxPlayers;
        this.destinationProtocol = destinationProtocol;
    }

    public String getServerName() {
        return serverName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public DestinationProtocol getDestinationProtocol() {
        return destinationProtocol;
    }
}
