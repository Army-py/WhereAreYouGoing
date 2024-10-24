package fr.army.leap.utils.builder.menu;

import fr.army.leap.LeapPlugin;
import fr.army.leap.menu.button.Button;
import fr.army.leap.menu.button.ButtonItem;
import fr.army.leap.menu.button.Buttons;
import fr.army.leap.menu.button.impl.BlankButton;
import fr.army.leap.menu.button.template.ButtonTemplate;
import fr.army.leap.menu.template.MenuTemplate;
import fr.army.leap.menu.view.AbstractMenuView;
import fr.army.leap.utils.loader.exception.UnableLoadConfigException;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MenuBuilder {

    private static final MenuBuilder INSTANCE = new MenuBuilder();

    private final LeapPlugin plugin = LeapPlugin.getPlugin();

    private final HashMap<String, ButtonItem> stateButtonItems = new HashMap<>();

    public static MenuBuilder getInstance() {
        return INSTANCE;
    }

    @NotNull
    public <T extends AbstractMenuView<T>> MenuBuilderResult<T> loadMenu(@NotNull String configName) {
        try {
            return buildMenu(plugin.getConfigLoader().initFile("menu/" + configName));
        } catch (UnableLoadConfigException e) {
            return buildEmptyMenu();
        }
    }

    @NotNull
    private <T extends AbstractMenuView<T>> MenuBuilderResult<T> buildMenu(@NotNull YamlConfiguration config) {
        final String title = config.getString("title");
        if (title == null){
            plugin.getLogger().severe("Unable to load menu title, please check your config file : " + config.getName());
            return buildEmptyMenu();
        }

        final boolean precede = config.getBoolean("previous");
        final String[] pattern = config.getStringList("pattern").toArray(String[]::new);
        final List<Button<? extends AbstractMenuView<?>>> buttons = new ArrayList<>();

        int size = 0;
        for (int row = 0; row < pattern.length && row < 6; row++) {
            final String line = pattern[row].replace(" ", "");

            for (int column = 0; column < line.length() && column < 9; column++) {
                final char character = line.charAt(column);

                final String path = "items." + character + ".";
                if (!config.contains(path)) {
                    plugin.getLogger().severe("Unable to load item for character " + character + " in menu " + config.getName());
                    return buildEmptyMenu();
                }

                final boolean pageComponent = config.getBoolean(path + "page-component");
                final Buttons buttonType = Buttons.getButtonType(config.getString(path + "button-type", null), pageComponent);
                final String material = config.getString(path + "material", "AIR");
                final String name = config.getString(path + "name", " ");
                final int amount = config.getInt(path + "amount", 1);
                final List<String> lore = config.getStringList(path + "lore");
                final boolean glow = config.getBoolean(path + "is-glowing");
                final String skullTexture = config.getString(path + "skull-texture", null);

                final ButtonItem buttonItem = new ButtonItem(Material.valueOf(material), name, amount, lore, glow, skullTexture);

                final ConfigurationSection metadataSection = config.getConfigurationSection(path + "meta");
                if (metadataSection != null) {
                    metadataSection.getKeys(false).forEach(key -> buttonItem.putMetadata(key, metadataSection.getString(key)));
                }

                final ButtonTemplate buttonTemplate = new ButtonTemplate(character, buttonItem);

                if (buttonType.equals(Buttons.BUTTON_SERVER_SELECTOR)) {
                    for (String state : new String[]{"maintenance", "full"}) {
                        if (config.isConfigurationSection("items." + character + "." + state)) {
                            ConfigurationSection stateSection = config.getConfigurationSection("items." + character + "." + state);
                            if (stateSection == null) {
                                plugin.getLogger().severe("Unable to load state " + state + " for button " + character + " in menu " + config.getName());
                                return buildEmptyMenu();
                            }

                            String stateMaterial = stateSection.getString("material", "AIR");
                            String stateName = stateSection.getString("name", " ");
                            int stateAmount = stateSection.getInt("amount", 1);
                            List<String> stateLore = stateSection.getStringList("lore");
                            boolean stateGlow = stateSection.getBoolean("is-glowing");
                            String stateSkullTexture = stateSection.getString("skull-texture", null);


                            ButtonItem stateButtonItem = new ButtonItem(Material.valueOf(stateMaterial), stateName, stateAmount, stateLore, stateGlow, stateSkullTexture);
                            if (metadataSection != null) {
                                metadataSection.getKeys(false).forEach(key -> stateButtonItem.putMetadata(key, metadataSection.getString(key)));
                            }
                            buttonTemplate.addState(state, stateButtonItem);
                        }
                    }
                }

                final Button<?> button = buttonType.createButton(buttonTemplate);
                buttons.add(button);
                size++;
            }
        }

        final MenuTemplate<T> menuTemplate = new MenuTemplate<>(title, precede, size);
        menuTemplate.addButtons(buttons.toArray(Button[]::new));

        return new MenuBuilderResult<>(menuTemplate, config);
    }

    private <T extends AbstractMenuView<T>> MenuBuilderResult<T> buildEmptyMenu(){
        final MenuTemplate<T> menuTemplate = new MenuTemplate<>("Empty", false, 9);
        final ButtonItem buttonItem = new ButtonItem(Material.valueOf("AIR"), "empty", 1, Collections.emptyList(), false, null);
        final ButtonTemplate buttonTemplate = new ButtonTemplate('!', buttonItem);
        final BlankButton button = new BlankButton(buttonTemplate);

        ArrayList<Button<? extends AbstractMenuView<?>>> buttons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            buttons.add(button);
        }

        menuTemplate.addButtons(buttons.toArray(Button[]::new));

        return new MenuBuilderResult<>(menuTemplate, null);
    }
}
