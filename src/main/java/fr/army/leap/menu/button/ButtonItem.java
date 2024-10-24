package fr.army.leap.menu.button;

import fr.army.leap.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class ButtonItem {

    private Material material;
    private String name;
    private int amount;
    private List<String> lore;
    private boolean glow;
    private String skullTexture;

    private final HashMap<String, String> metadata = new HashMap<>();
    private String identifier = null;


    public ButtonItem(Material material, String name, int amount, List<String> lore, boolean glow, String skullTexture) {
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.lore = lore;
        this.glow = glow;
        this.skullTexture = skullTexture;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean isGlow() {
        return glow;
    }

    public String getSkullTexture() {
        return skullTexture;
    }

    @Nullable
    public String getIdentifier() {
        return identifier;
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public ButtonItem setMaterial(@NotNull Material material) {
        this.material = material;
        return this;
    }

    public ButtonItem setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    public ButtonItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ButtonItem setLore(@NotNull List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ButtonItem setGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ButtonItem setSkullTexture(@NotNull String skullTexture) {
        this.skullTexture = skullTexture;
        return this;
    }

    public ButtonItem setIdentifier(@NotNull String identifier) {
        this.identifier = identifier;
        return this;
    }

    public ButtonItem putMetadata(@NotNull String key, @Nullable String value) {
        if (value == null) return this;
        metadata.put(key, value);
        return this;
    }

    public ItemStack build(){
        final ItemBuilder itemBuilder = new ItemBuilder(material)
            .setDisplayName(name)
            .setAmount(amount)
            .setLore(lore)
            .setGlow(glow)
            .setSkullTexture(skullTexture);

        if (identifier != null)
            itemBuilder.setPersistentData("identifier", identifier);

        return itemBuilder.buildItem();
    }
}