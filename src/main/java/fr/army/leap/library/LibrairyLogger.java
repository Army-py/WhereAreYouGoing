package fr.army.leap.library;

import com.alessiodp.libby.logging.LogLevel;
import com.alessiodp.libby.logging.adapters.LogAdapter;
import fr.army.leap.LeapPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class LibrairyLogger implements LogAdapter {

    @Override
    public void log(@NotNull LogLevel logLevel, @Nullable String s) {

    }

    @Override
    public void log(@NotNull LogLevel logLevel, @Nullable String s, @Nullable Throwable throwable) {
        LeapPlugin.getPlugin().getLogger().severe(s);
        if (throwable != null) {
            LeapPlugin.getPlugin().getLogger().severe(throwable.getMessage());
            LeapPlugin.getPlugin().getLogger().severe(Arrays.toString(throwable.getStackTrace()));
        }
    }
}
