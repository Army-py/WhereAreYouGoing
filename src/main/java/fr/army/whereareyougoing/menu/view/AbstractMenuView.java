package fr.army.whereareyougoing.menu.view;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.menu.WAYGMenu;
import fr.army.whereareyougoing.menu.template.MenuTemplate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMenuView<T extends AbstractMenuView<T>> implements IMenuView<T> {

    protected final static WhereAreYouGoingPlugin plugin = WhereAreYouGoingPlugin.getPlugin();

    protected final Player viewer;
    protected final WAYGMenu<T> menu;

    protected Inventory inventory;

    protected AbstractMenuView(Player viewer, WAYGMenu<T> menu) {
        this.viewer = viewer;
        this.menu = menu;
    }

    public abstract Inventory createInventory();

    public void open(){
        viewer.openInventory(createInventory());
    }

    public void onClose(InventoryCloseEvent closeEvent){
        final Player player = (Player) closeEvent.getPlayer();
        final MenuTemplate<T> menuTemplate = menu.getMenuBuilderResult().menuTemplate();
        final WAYGMenu<T> precedingMenu = menuTemplate.getPrecedingMenu();

        if (menuTemplate.canPrecede() && precedingMenu != null){
            new BukkitRunnable() {
                @Override
                public void run(){
                    precedingMenu.createView(player);
                }
            }.runTaskLater(plugin, 1);
        }
    }

    public Player getViewer() {
        return viewer;
    }

    public WAYGMenu<T> getMenu() {
        return menu;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
