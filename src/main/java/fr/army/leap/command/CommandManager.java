package fr.army.leap.command;

import fr.army.leap.WhereAreYouGoingPlugin;
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
