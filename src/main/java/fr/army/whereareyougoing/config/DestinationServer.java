package fr.army.whereareyougoing.config;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.cache.impl.ServerCache;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.database.repository.callback.AsyncCallBackObject;
import fr.army.whereareyougoing.database.repository.impl.ServerRepository;
import fr.army.whereareyougoing.utils.network.task.data.AsyncDataSender;
import org.bukkit.Bukkit;
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

        this.serverRepository = WhereAreYouGoingPlugin.getPlugin().getRepositoryProvider().getRepository(ServerRepository.class);
        this.serverCache = WhereAreYouGoingPlugin.getPlugin().getCacheProvider().getCache(ServerCache.class);
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
        return cachedServer != null && cachedServer.getPlayerCount() >= maxPlayers;
    }
}
