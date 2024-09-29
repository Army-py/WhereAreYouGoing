package fr.army.whereareyougoing.external.simpleportals;

import org.bukkit.event.Listener;
import xzot1k.plugins.sp.SimplePortals;
import xzot1k.plugins.sp.api.events.PortalEnterEvent;

public class ExternalSimplePortalsListener implements Listener {

    private SimplePortals simplePortals;

    private void onPortalEnter(PortalEnterEvent event) {
        if (event.isCancelled()) {
            return;
        }
    }
}
