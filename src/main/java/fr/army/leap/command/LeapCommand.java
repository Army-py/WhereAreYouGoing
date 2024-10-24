package fr.army.leap.command;

import fr.army.leap.LeapPlugin;
import fr.army.leap.command.subcommand.SubCmdMaintenance;
import fr.army.leap.config.message.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeapCommand implements CommandExecutor, TabCompleter {

    private final LeapPlugin plugin;
    private final Map<String, Object> subCommands;

    public LeapCommand(LeapPlugin plugin) {
        this.plugin = plugin;
        this.subCommands = new HashMap<>();

        initSubCommands(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Messages.COMMAND_INVALID.getMessage());
            return true;
        }

        // Delegate to a sub command
        if (subCommands.containsKey(args[0])) {
            final SubCommand subCmd = (SubCommand) subCommands.get(args[0]);

            if (!sender.hasPermission(subCmd.getPermission())) {
                sender.sendMessage(Messages.NO_PERMISSION.getMessage());
                return true;
            }

            try {
                return subCmd.execute(sender, args);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("§c" + e.getMessage());
                return true;
            }
        }

        sender.sendMessage(Messages.COMMAND_INVALID.getMessage());
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> result = new ArrayList<>();
            subCommands.forEach((key, value) -> {
                if (sender.hasPermission(((SubCommand) value).getPermission()) && key.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(key);
                }
            });
            return result;
        }
        if (args.length > 1 && subCommands.containsKey(args[0].toLowerCase()) && sender.hasPermission(((SubCommand) subCommands.get(args[0])).getPermission())) {
            return ((SubCommand) subCommands.get(args[0])).onTabComplete(sender, args);
        }
        return List.of();
    }

    private void initSubCommands(@NotNull LeapPlugin plugin) {
        subCommands.put("maintenance", new SubCmdMaintenance(plugin));
    }
}
