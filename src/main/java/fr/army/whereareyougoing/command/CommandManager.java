package fr.army.whereareyougoing.command;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    private final WAYGCommand waygCommand;

    public CommandManager(WhereAreYouGoingPlugin plugin) {
        PluginCommand singularityPluginCommand = plugin.getCommand("whereareyougoing");

        waygCommand = new WAYGCommand(plugin);

        if (singularityPluginCommand != null) {
            singularityPluginCommand.setExecutor(waygCommand);
            singularityPluginCommand.setTabCompleter(waygCommand);
        }
    }
}
