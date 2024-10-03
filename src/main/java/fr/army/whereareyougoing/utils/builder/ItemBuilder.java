package fr.army.whereareyougoing.utils.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.army.whereareyougoing.WhereAreYouGoingPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(@NotNull Material material){
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder setMaterial(@NotNull Material material){
        this.item.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(@Nullable String displayName){
        if (meta == null) return this;
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setLore(@Nullable List<String> lore){
        if (meta == null) return this;
        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore);
        }
        return this;
    }

    public ItemBuilder setGlow(boolean isGlow){
        if (meta == null) return this;
        if (!isGlow) return this;
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setSkullTexture(@Nullable String texture){
        if (meta == null) return this;
        if (texture == null || texture.isBlank()) return this;
        GameProfile profile = new GameProfile(toUUID(texture), (String) null);
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field mtd = meta.getClass().getDeclaredField("profile");
            mtd.setAccessible(true);
            mtd.set(meta, profile);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public ItemBuilder setPlayerHead(@Nullable OfflinePlayer player){
        final UUID playerUuid;
        final String playerName;
        if (player == null) {
            playerUuid = UUID.randomUUID();
            playerName = "Steve";
        } else {
            playerUuid = player.getUniqueId();
            playerName = player.getName();
        }
        GameProfile profile = new GameProfile(playerUuid, playerName);

        try {
            Field mtd = meta.getClass().getDeclaredField("profile");
            mtd.setAccessible(true);
            mtd.set(meta, profile);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public ItemBuilder hideItemAttributes(){
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder setPersistentData(@NotNull String key, @NotNull String value){
        meta.getPersistentDataContainer().set(new NamespacedKey(WhereAreYouGoingPlugin.getPlugin(), key), PersistentDataType.STRING, value);
        return this;
    }


    public ItemStack buildItem(){
        this.item.setItemMeta(this.meta);
        return this.item;
    }


    private UUID toUUID(String input) {
        long val = input.hashCode();
        return new UUID(val, val);
    }
}
