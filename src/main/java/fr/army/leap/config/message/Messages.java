package fr.army.leap.config.message;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.config.Config;
import fr.army.leap.config.message.exception.MessageNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public enum Messages {

    COMMAND_INVALID,
    SERVER_NOT_FOUND,
    NO_PERMISSION,
    MAINTENANCE_ON,
    MAINTENANCE_OFF,
    SERVER_IN_MAINTENANCE,
    ;

    public String getMessage(@NotNull Map<Placeholders, String> args) {
        final YamlConfiguration messages = WhereAreYouGoingPlugin.getPlugin().getMessages();

        String message = messages.getString(this.toString());
        if (message == null){
            throw new MessageNotFoundException(this, Config.language);
        }

        return PlaceholdersUtils.replace(message, args);
    }

    public String getMessage() {
        final YamlConfiguration messages = WhereAreYouGoingPlugin.getPlugin().getMessages();

        final String message = messages.getString(this.toString());
        if (message == null){
            throw new MessageNotFoundException(this, Config.language);
        }

        return message;
    }
}
