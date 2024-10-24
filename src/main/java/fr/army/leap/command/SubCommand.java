package fr.army.leap.command;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.database.repository.RepositoryProvider;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    protected final WhereAreYouGoingPlugin plugin;
    protected final RepositoryProvider repositoryProvider;

    public SubCommand(WhereAreYouGoingPlugin plugin) {
        this.plugin = plugin;
        this.repositoryProvider = plugin.getRepositoryProvider();
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getPermission();
}
