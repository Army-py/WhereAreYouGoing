package fr.army.leap.utils.loader.exception;

import fr.army.leap.WhereAreYouGoingPlugin;
import org.jetbrains.annotations.NotNull;

public class UnableLoadConfigException extends Exception {

    public UnableLoadConfigException(@NotNull String fileName, @NotNull String reason) {
        super("Unable to load config file " + fileName + ": " + reason);
        WhereAreYouGoingPlugin.getPlugin().getLogger().severe("Unable to load config file " + fileName + ": " + reason);
    }
}
