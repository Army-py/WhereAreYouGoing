package fr.army.whereareyougoing.config;

import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import fr.army.whereareyougoing.menu.button.ButtonItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DestinationSelector {

    public static final String SELECTOR_IDENTIFIER = "wayg_destination_selector";

    private final ButtonItem buttonItem;
    private final int slot;

    public DestinationSelector(ButtonItem buttonItem, int slot){
        this.buttonItem = buttonItem;
        this.slot = slot;
    }

    public ItemStack getButtonItem(){
        return buttonItem
                .setIdentifier(SELECTOR_IDENTIFIER)
                .build();
    }

    public int getSlot() {
        return slot;
    }

    public static boolean isDestinationSelector(ItemStack item){
        final ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        return itemMeta.getPersistentDataContainer().has(
                new NamespacedKey(WhereAreYouGoingPlugin.getPlugin(), "identifier"),
                PersistentDataType.STRING
        );
    }
}
