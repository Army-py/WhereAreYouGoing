package fr.army.whereareyougoing.command;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    protected final WhereAreYouGoingPlugin plugin;

    public SubCommand(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getPermission();
}
