package fr.army.leap.command.subcommand;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.command.SubCommand;
import fr.army.leap.config.Config;
import fr.army.leap.config.message.Messages;
import fr.army.leap.config.message.Placeholders;
import fr.army.leap.external.simpleportals.ExternalSimplePortalsLoader;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import xzot1k.plugins.sp.SimplePortals;
import xzot1k.plugins.sp.api.Manager;
import xzot1k.plugins.sp.api.objects.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class SubCmdMaintenance extends SubCommand {

    public SubCmdMaintenance(WhereAreYouGoingPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Messages.COMMAND_INVALID.getMessage());
            return true;
        }

        final String serverName = args[1];

        if (!Config.servers.containsKey(serverName)) {
            sender.sendMessage(Messages.SERVER_NOT_FOUND.getMessage());
            return true;
        }

        Consumer<Boolean> finalSetPortalDisabled = setPortalDisabled(serverName);
        Config.servers.get(serverName).setMaintenance(
                model -> {
                    if (finalSetPortalDisabled != null)
                        finalSetPortalDisabled.accept(model.isMaintenance());

                    if (model.isMaintenance())
                        sender.sendMessage(Messages.MAINTENANCE_ON.getMessage(new HashMap<>(){{
                            put(Placeholders.SERVER, serverName);
                        }}));
                    else
                        sender.sendMessage(Messages.MAINTENANCE_OFF.getMessage(new HashMap<>(){{
                            put(Placeholders.SERVER, serverName);
                        }}));
                }
        );

        return true;
    }

    private static @Nullable Consumer<Boolean> setPortalDisabled(String serverName) {
        final SimplePortals simplePortals = ExternalSimplePortalsLoader.simplePortals;

        Consumer<Boolean> setPortalDisabled;
        if (simplePortals != null) {
            final Manager manager = simplePortals.getManager();
            final Portal portal = manager.getPortal(serverName);
            setPortalDisabled = (isDisabled) -> {
                if (portal != null) {
                    portal.setDisabled(isDisabled);
                    portal.save();
                }
            };
        } else {
            setPortalDisabled = null;
        }

        return setPortalDisabled;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(Config.servers.keySet());
    }

    @Override
    public String getPermission() {
        return "wayg.maintenance";
    }
}
