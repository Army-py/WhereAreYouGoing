package fr.army.leap.config;

import fr.army.leap.LeapPlugin;
import fr.army.leap.cache.impl.ServerCache;
import fr.army.leap.database.model.impl.ServerModel;
import fr.army.leap.database.repository.callback.AsyncCallBackObject;
import fr.army.leap.database.repository.impl.ServerRepository;
import org.jetbrains.annotations.Nullable;

public class DestinationServer {

    private final String serverName;
    private final int maxPlayers;
    private final DestinationProtocol destinationProtocol;

    private final ServerRepository serverRepository;
    private final ServerCache serverCache;

    public DestinationServer(String serverName, int maxPlayers, DestinationProtocol destinationProtocol) {
        this.serverName = serverName;
        this.maxPlayers = maxPlayers;
        this.destinationProtocol = destinationProtocol;

        this.serverRepository = LeapPlugin.getPlugin().getRepositoryProvider().getRepository(ServerRepository.class);
        this.serverCache = LeapPlugin.getPlugin().getCacheProvider().getCache(ServerCache.class);
    }

    public void setMaintenance(AsyncCallBackObject<ServerModel> asyncCallBackObject) {
        final ServerModel serverModel = getCachedServer();
        if (serverModel == null) return;

        serverModel.setMaintenance(!serverModel.isMaintenance());
        serverRepository.update(serverModel, asyncCallBackObject);

        serverCache.putCachedObject(serverName, serverModel);
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

    @Nullable
    public ServerModel getCachedServer() {
        return serverCache.getCachedObject(serverName);
    }

    public boolean isFull() {
        final ServerModel cachedServer = getCachedServer();
        return cachedServer != null && cachedServer.getPlayerCount() != null && cachedServer.getPlayerCount() >= maxPlayers;
    }
}
