package fr.army.whereareyougoing.selector;

import fr.army.whereareyougoing.menu.button.ButtonItem;
import org.bukkit.inventory.ItemStack;

public class DestinationSelector {

    public static final String SELECTOR_IDENTIFIER = "wayg_destination_selector";

    private final ButtonItem buttonItem;

    public DestinationSelector(ButtonItem buttonItem){
        this.buttonItem = buttonItem;
    }

    public ItemStack getButtonItem(){
        return buttonItem
                .setIdentifier(SELECTOR_IDENTIFIER)
                .build();
    }
}
