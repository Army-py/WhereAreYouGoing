package fr.army.leap.config.message.exception;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.config.message.Messages;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Messages message, String fileName) {
        super(message.toString() + " not found in " + fileName);
        WhereAreYouGoingPlugin.getPlugin().getLogger().severe(message + " not found in " + fileName);
    }
}
