package fr.army.leap.command;

import fr.army.leap.LeapPlugin;
import fr.army.leap.database.repository.RepositoryProvider;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    protected final LeapPlugin plugin;
    protected final RepositoryProvider repositoryProvider;

    public SubCommand(LeapPlugin plugin) {
        this.plugin = plugin;
        this.repositoryProvider = plugin.getRepositoryProvider();
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getPermission();
}
