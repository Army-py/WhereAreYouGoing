package fr.army.whereareyougoing.command;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    public CommandManager(WhereAreYouGoingPlugin plugin) {
        PluginCommand singularityPluginCommand = plugin.getCommand("whereareyougoing");

        WAYGCommand waygCommand = new WAYGCommand(plugin);

        if (singularityPluginCommand != null) {
            singularityPluginCommand.setExecutor(waygCommand);
            singularityPluginCommand.setTabCompleter(waygCommand);
        }
    }
}
