package fr.army.whereareyougoing.menu.template;

import fr.army.whereareyougoing.menu.WAYGMenu;
import fr.army.whereareyougoing.menu.button.Button;
import fr.army.whereareyougoing.menu.button.ComponentButton;
import fr.army.whereareyougoing.menu.button.template.ButtonTemplate;
import fr.army.whereareyougoing.menu.view.AbstractMenuView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MenuTemplate<T extends AbstractMenuView<T>> {

    private final String title;
    private final boolean precede;
    private final int size;
    private final Button<T, ? extends ButtonTemplate>[] buttons;

    private WAYGMenu<T> precedingMenu;

    public MenuTemplate(@NotNull String title, boolean precede, int size) {
        this.title = title;
        this.size = size;
        this.precede = precede;
        this.buttons = new Button[size];

        this.precedingMenu = null;
    }

    @NotNull
    public Button<T, ? extends ButtonTemplate> getButton(int slot) {
        return buttons[slot];
    }

    public void addButton(Button<T, ? extends ButtonTemplate> button, int slot){
        this.buttons[slot] = button;
    }

    public void addButtons(Button<T, ? extends ButtonTemplate>[] buttons){
        System.arraycopy(buttons, 0, this.buttons, 0, buttons.length);
    }

//    public void mapButton(int slot, Button<T> button) {
//        if (button.getButtonTemplate().getButtonItem() != null)
//            this.buttons[slot] = button;
//        else
//            this.buttons[slot] = button.setButtonItem(this.buttons[slot].getButtonTemplate().getButtonItem());
//    }

//    public void mapButtons(int[] slots, Button<T> button) {
//        for (int slot : slots) {
//            mapButton(slot, button);
//        }
//    }

    public int[] getSlots(char itemSection){
        List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < buttons.length; i++) {
            final ButtonTemplate buttonTemplate = buttons[i].getButtonTemplate();
            if (buttonTemplate.getCharacter() == itemSection)
                slots.add(i);
        }

        return slots.stream().mapToInt(i -> i).toArray();
    }

    public int getSlot(char itemSection){
        for (int i = 0; i < buttons.length; i++) {
            final ButtonTemplate buttonTemplate = buttons[i].getButtonTemplate();
            if (buttonTemplate.getCharacter() == itemSection)
                return i;
        }

        return -1;
    }

    public int getComponentCount(){
        int count = 0;
        for (Button<T, ? extends ButtonTemplate> button : buttons) {
            if (button instanceof ComponentButton)
                count++;
        }

        return count;
    }


    @NotNull
    public String getTitle() {
        return title;
    }

    public boolean canPrecede(){
        return precede;
    }

    public int getSize() {
        return size;
    }

    @NotNull
    public Button<T, ? extends ButtonTemplate>[] getButtons() {
        return buttons;
    }

    @Nullable
    public WAYGMenu<T> getPrecedingMenu() {
        return precedingMenu;
    }

    public void setPrecedingMenu(@NotNull WAYGMenu<T> precedingMenu) {
        this.precedingMenu = precedingMenu;
    }
}
