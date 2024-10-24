package fr.army.leap.listener;

import fr.army.leap.LeapPlugin;
import fr.army.leap.listener.impl.MenusListener;
import fr.army.leap.listener.impl.SelectorListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(LeapPlugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SelectorListener(), plugin);
        pluginManager.registerEvents(new MenusListener(), plugin);
    }
}
