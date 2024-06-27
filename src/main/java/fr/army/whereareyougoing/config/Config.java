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
    public static DefaultSelectedSlot defaultSelectedSlot;
    public static DestinationSelector destinationSelector;
    public static Map<String, DestinationServer> servers = new HashMap<>();
    public static int checkServerCountInterval;

    public Config(YamlConfiguration config) {
        this.config = config;
    }

    public void load(){
        clearInventoryOnJoin = config.getBoolean("clear-inventory-on-join", true);

        final ConfigurationSection defaultSelectedSlotSection = Objects.requireNonNull(
                config.getConfigurationSection("default-selected-item"),
                "Unable to load default-selected-slot section"
        );
        getDefaultSelectedSlot(defaultSelectedSlotSection);

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

    private void getDefaultSelectedSlot(@NotNull ConfigurationSection section){
        final boolean enabled = section.getBoolean("enabled", false);
        final int slot = section.getInt("slot", 0);
        if (slot < 0 || slot > 8) {
            throw new IllegalArgumentException("Invalid slot number");
        }
        defaultSelectedSlot = new DefaultSelectedSlot(enabled, slot);
    }

    private void getDestinationServers(@NotNull ConfigurationSection section){
        for (String serverName : section.getKeys(false)) {
            final ConfigurationSection protocolVersionSection = Objects.requireNonNull(
                    section.getConfigurationSection(serverName + ".protocol-version"),
                    "Unable to load protocol-version section"
            );

            final ConfigurationSection protocolVersionMessageSection = Objects.requireNonNull(
                    protocolVersionSection.getConfigurationSection("message"),
                    "Unable to load protocol-version message section"
            );
            final ProtocolVersionMessage protocolVersionMessage = new ProtocolVersionMessage(
                    protocolVersionMessageSection.getBoolean("enabled", false),
                    protocolVersionMessageSection.getString("content", "Default content")
            );

            final ConfigurationSection protocolVersionTitleSection = Objects.requireNonNull(
                    protocolVersionSection.getConfigurationSection("title"),
                    "Unable to load protocol-version title section"
            );
            final ProtocolVersionTitle protocolVersionTitle = new ProtocolVersionTitle(
                    protocolVersionTitleSection.getBoolean("enabled", false),
                    protocolVersionTitleSection.getString("title", "Default title"),
                    protocolVersionTitleSection.getString("sub-title", "Default subtitle"),
                    protocolVersionTitleSection.getInt("fade-in", 20),
                    protocolVersionTitleSection.getInt("stay", 60),
                    protocolVersionTitleSection.getInt("fade-out", 20)
            );

            final int maxProtocolVersion = protocolVersionSection.getInt("max-protocol", Integer.MAX_VALUE);
            final DestinationProtocol destProtocol = new DestinationProtocol(
                    maxProtocolVersion == -1 ? Integer.MAX_VALUE : maxProtocolVersion,
                    protocolVersionSection.getInt("min-protocol", -1),
                    protocolVersionMessage,
                    protocolVersionTitle
            );
            System.out.println(destProtocol);

            final DestinationServer destServer = new DestinationServer(
                    serverName,
                    section.getInt(serverName + ".max-players", 0),
                    destProtocol
            );
            servers.put(serverName, destServer);
        }
    }
}
