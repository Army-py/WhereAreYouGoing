package fr.army.whereareyougoing.command.subcommand;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.command.SubCommand;
import fr.army.whereareyougoing.config.Config;
import fr.army.whereareyougoing.external.simpleportals.ExternalSimplePortalsLoader;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import xzot1k.plugins.sp.SimplePortals;
import xzot1k.plugins.sp.api.Manager;
import xzot1k.plugins.sp.api.objects.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SubCmdMaintenance extends SubCommand {

    public SubCmdMaintenance(WhereAreYouGoingPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§cInvalid command.");
            return true;
        }

        final String serverName = args[1];

        if (!Config.servers.containsKey(serverName)) {
            sender.sendMessage("§cThis server does not exist.");
            return true;
        }

        Consumer<Boolean> finalSetPortalDisabled = setPortalDisabled(serverName);
        Config.servers.get(serverName).setMaintenance(
                model -> {
                    if (finalSetPortalDisabled != null)
                        finalSetPortalDisabled.accept(model.isMaintenance());

                    if (model.isMaintenance())
                        sender.sendMessage("§aThe server " + serverName + " is now in maintenance mode.");
                    else
                        sender.sendMessage("§aThe server " + serverName + " is no longer in maintenance mode.");
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
