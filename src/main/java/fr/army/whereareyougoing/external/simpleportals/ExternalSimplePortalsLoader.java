package fr.army.whereareyougoing.external.simpleportals;

import fr.army.whereareyougoing.external.ExternalLaoder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xzot1k.plugins.sp.SimplePortals;

public class ExternalSimplePortalsLoader implements ExternalLaoder {

    public static SimplePortals simplePortals = null;

    @Override
    public void load() {
        final Plugin simplePortalsPlugin = Bukkit.getPluginManager().getPlugin("SimplePortals");

        if (simplePortalsPlugin == null) {
            return;
        }
        if (!(simplePortalsPlugin instanceof SimplePortals)) {
            return;
        }

        simplePortals = SimplePortals.getPluginInstance();
    }

    @Override
    public void unload() {
        simplePortals = null;
    }
}
