package fr.army.leap.command;

import fr.army.leap.LeapPlugin;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    public CommandManager(LeapPlugin plugin) {
        PluginCommand singularityPluginCommand = plugin.getCommand("leap");

        LeapCommand leapCommand = new LeapCommand(plugin);

        if (singularityPluginCommand != null) {
            singularityPluginCommand.setExecutor(leapCommand);
            singularityPluginCommand.setTabCompleter(leapCommand);
        }
    }
}
