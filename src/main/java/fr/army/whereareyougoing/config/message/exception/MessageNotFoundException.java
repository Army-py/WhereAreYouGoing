package fr.army.whereareyougoing.config.message.exception;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.config.message.Messages;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Messages message, String fileName) {
        super(message.toString() + " not found in " + fileName);
        WhereAreYouGoingPlugin.getPlugin().getLogger().severe(message + " not found in " + fileName);
    }
}
