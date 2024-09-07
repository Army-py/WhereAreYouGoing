package fr.army.whereareyougoing.menu.view.impl;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.cache.impl.ServerCache;
import fr.army.whereareyougoing.database.model.impl.ServerModel;
import fr.army.whereareyougoing.menu.WAYGMenu;
import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.impl.ServerSelectorButton;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.button.template.LockableButtonTemplate;
import fr.army.whereareyougoing.menu.template.MenuTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuView extends AbstractMenuView<MenuView> {

    private final ServerCache serverCache;

    public MenuView(Player player, WAYGMenu<MenuView> menu) {
        super(player, menu);

        this.serverCache = WhereAreYouGoingPlugin.getPlugin().getCacheProvider().getCache(ServerCache.class);
    }

    @Override
    public Inventory createInventory() {
        final MenuTemplate<MenuView> menuTemplate = menu.getMenuBuilderResult().getMenuTemplate();
        final Inventory inventory = Bukkit.createInventory(this, menuTemplate.getSize(), menuTemplate.getTitle());

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            final Button<MenuView, ? extends ButtonTemplate> button = menuTemplate.getButton(slot).setMenuView(this);
            final ButtonTemplate buttonTemplate = button.getButtonTemplate();

            ItemStack itemStack = buttonTemplate.getButtonItem().build();
            if (button instanceof ServerSelectorButton && buttonTemplate instanceof LockableButtonTemplate) {
                final String serverName = buttonTemplate.getButtonItem().getMetadata().get("server");

                ServerModel cachedServer;
                if (serverName != null && (cachedServer = serverCache.getCachedObject(serverName)) != null && cachedServer.isMaintenance()) {
                    itemStack = ((LockableButtonTemplate) buttonTemplate).getLockedButtonItem().build();
                } else {
                    itemStack = buttonTemplate.getButtonItem().build();
                }
            }

            inventory.setItem(slot, itemStack);
        }

        return inventory;
    }
}
