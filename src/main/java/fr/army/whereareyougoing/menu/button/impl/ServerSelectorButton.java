package fr.army.whereareyougoing.menu.button.impl;

import com.viaversion.viaversion.api.ViaAPI;
import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.cache.impl.ServerCache;
import fr.army.whereareyougoing.config.*;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.template.LockableButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import fr.army.whereareyougoing.menu.view.impl.MenuView;
import fr.army.whereareyougoing.utils.network.packet.impl.PlayerCountPacket;
import fr.army.whereareyougoing.utils.network.packet.impl.PlayerSenderPacket;
import fr.army.whereareyougoing.utils.network.task.data.AsyncDataSender;
import fr.army.whereareyougoing.utils.network.task.data.QueuedDataSender;
import fr.army.whereareyougoing.utils.network.task.queue.PlayerSenderQueueManager;
import fr.army.whereareyougoing.utils.network.task.sender.TaskSenderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class ServerSelectorButton extends Button<MenuView, LockableButtonTemplate> {

    private final ViaAPI<?> viaAPI;
    private final ServerCache serverCache;

    public ServerSelectorButton(LockableButtonTemplate buttonTemplate) {
        super(buttonTemplate);

        this.viaAPI = WhereAreYouGoingPlugin.getPlugin().getViaAPI();
        this.serverCache = WhereAreYouGoingPlugin.getPlugin().getCacheProvider().getCache(ServerCache.class);
    }

    @Override
    public void onClick(InventoryClickEvent clickEvent) {
        final String serverName = buttonTemplate.getButtonItem().getMetadata().get("server");
        final ServerModel cachedServer = serverCache.getCachedObject(serverName);
        if (cachedServer != null && cachedServer.isMaintenance()) return;

        final Player player = (Player) clickEvent.getWhoClicked();

        if (serverName == null) return;

        final DestinationServer destinationServer = Config.servers.get(serverName);
        final DestinationProtocol destinationProtocol = destinationServer.getDestinationProtocol();

        if (viaAPI.getPlayerVersion(player.getUniqueId()) < destinationProtocol.minProtocolVersion() ||
                viaAPI.getPlayerVersion(player.getUniqueId()) > destinationProtocol.maxProtocolVersion()) {

            final ProtocolVersionMessage protocolVersionMessage = destinationProtocol.protocolVersionMessage();
            final ProtocolVersionTitle protocolVersionTitle = destinationProtocol.protocolVersionTitle();
            if (protocolVersionMessage.enabled())
                player.sendMessage(protocolVersionMessage.content());
            if (protocolVersionTitle.enabled())
                player.sendTitle(
                        protocolVersionTitle.title(),
                        protocolVersionTitle.subtitle(),
                        protocolVersionTitle.fadeIn(),
                        protocolVersionTitle.stay(),
                        protocolVersionTitle.fadeOut()
                );
            player.closeInventory();
            return;
        }

        final QueuedDataSender queuedDataSender = new QueuedDataSender();
        final AsyncDataSender asyncDataSender = new AsyncDataSender();
        final PlayerSenderPacket playerSenderPacket = new PlayerSenderPacket(player, serverName);
        final TaskSenderManager taskSenderManager = WhereAreYouGoingPlugin.getPlugin().getTaskSenderManager();
        final PlayerSenderQueueManager playerSenderQueueManager = taskSenderManager.getPlayerSenderQueueManager(serverName);

        player.closeInventory();
        queuedDataSender.sendPluginMessage(playerSenderQueueManager, playerSenderPacket);

        final PlayerCountPacket playerCountPacket = new PlayerCountPacket(player, serverName);
        asyncDataSender.sendPluginMessage(playerCountPacket);
    }

    @Override
    public @NotNull Button<? extends AbstractMenuView<?>, LockableButtonTemplate> get(@NotNull LockableButtonTemplate buttonTemplate) {
        return new ServerSelectorButton(buttonTemplate);
    }
}
