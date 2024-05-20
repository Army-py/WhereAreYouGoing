package fr.army.whereareyougoing.config;

import fr.army.whereareyougoing.menu.button.ButtonItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {

    private final YamlConfiguration config;

    public static boolean clearInventoryOnJoin;
    public static DestinationSelector destinationSelector;
    public static Map<String, DestinationServer> servers = new HashMap<>();
    public static int checkServerCountInterval;

    public Config(YamlConfiguration config) {
        this.config = config;
    }

    public void load(){
        clearInventoryOnJoin = config.getBoolean("clear-inventory-on-join", true);

        final ConfigurationSection selectorSection = Objects.requireNonNull(
                config.getConfigurationSection("destination-selector"),
                "Unable to load destination-selector section"
        );
        getDestinationSelector(selectorSection);

        final ConfigurationSection serversSection = Objects.requireNonNull(
                config.getConfigurationSection("servers"),
                "Unable to load servers-max-players section"
        );
        getDestinationServers(serversSection);

        checkServerCountInterval = config.getInt("check-server-count-interval", 20);
    }

    private void getDestinationSelector(@NotNull ConfigurationSection section){
        final int slot = section.getInt("slot", 4);
        final Material material = Material.getMaterial(section.getString("material", "STONE"));
        final String name = section.getString("name", "Default name");
        final String skullTexture = section.getString("skull-texture", null);
        final int amount = section.getInt("amount", 1);
        final boolean glow = section.getBoolean("is-glowing", false);
        final List<String> lore = section.getStringList("lore");

        destinationSelector = new DestinationSelector(
                new ButtonItem(material, name, amount, lore, glow, skullTexture),
                slot
        );
    }

    private void getDestinationServers(@NotNull ConfigurationSection section){
        for (String serverName : section.getKeys(false)) {
            final DestinationServer destServer = new DestinationServer(
                    serverName,
                    section.getInt(serverName + ".max-players", 0),
                    section.getInt(serverName + ".max-protocol-version", Integer.MAX_VALUE),
                    section.getInt(serverName + ".min-protocol-version", -1)
            );
            servers.put(serverName, destServer);
        }
    }
}
