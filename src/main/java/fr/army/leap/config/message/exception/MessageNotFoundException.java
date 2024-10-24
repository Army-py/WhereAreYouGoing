package fr.army.leap.config.message.exception;

import fr.army.leap.LeapPlugin;
import fr.army.leap.config.message.Messages;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Messages message, String fileName) {
        super(message.toString() + " not found in " + fileName);
        LeapPlugin.getPlugin().getLogger().severe(message + " not found in " + fileName);
    }
}
