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
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(Config.servers.keySet());
    }

    @Override
    public String getPermission() {
        return "";
    }
}
