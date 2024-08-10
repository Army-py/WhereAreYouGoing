package fr.army.whereareyougoing.command.subcommand;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.command.SubCommand;
import fr.army.whereareyougoing.config.Config;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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

        final String server = args[1];

        if (!Config.servers.containsKey(server)) {
            sender.sendMessage("§cThis server does not exist.");
            return true;
        }

        Config.servers.get(server).setMaintenance(
                model -> {
                    if (model.isMaintenance())
                        sender.sendMessage("§aThe server " + server + " is now in maintenance mode.");
                    else
                        sender.sendMessage("§aThe server " + server + " is no longer in maintenance mode.");
                }
        );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(Config.servers.keySet());
    }

    @Override
    public String getPermission() {
        return "whereareyougoing.maintenance";
    }
}
