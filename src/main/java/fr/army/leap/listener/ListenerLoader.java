package fr.army.leap.listener;

import fr.army.leap.WhereAreYouGoingPlugin;
import fr.army.leap.listener.impl.MenusListener;
import fr.army.leap.listener.impl.SelectorListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(WhereAreYouGoingPlugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SelectorListener(), plugin);
        pluginManager.registerEvents(new MenusListener(), plugin);
    }
}
