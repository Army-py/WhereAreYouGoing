package fr.army.whereareyougoing.listener;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.listener.impl.MenusListener;
import fr.army.whereareyougoing.listener.impl.SelectorListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(WhereAreYouGoingPlugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SelectorListener(), plugin);
        pluginManager.registerEvents(new MenusListener(), plugin);
    }
}
