package fr.army.whereareyougoing.listener;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.listener.impl.InventoryClickListener;
import fr.army.whereareyougoing.listener.impl.PlayerInteractListener;
import fr.army.whereareyougoing.listener.impl.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(WhereAreYouGoingPlugin plugin) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(plugin), plugin);
        pluginManager.registerEvents(new InventoryClickListener(plugin), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(plugin), plugin);
    }
}
