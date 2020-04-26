package com.github.codedoctorde.itemmods.gui.choose.item;

import com.github.codedoctorde.itemmods.Main;
import com.github.codedoctorde.itemmods.gui.ItemGui;
import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiEvent;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.ListGui;
import com.gitlab.codedoctorde.api.ui.template.events.GuiListEvent;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.MessageFormat;

/**
 * @author CodeDoctorDE
 */
public class ChooseItemAddonGui {
    private final int itemIndex;

    public ChooseItemAddonGui(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public Gui[] createGui() {
        JsonObject guiTranslation = Main.getPlugin().getTranslationConfig().getJsonObject().getAsJsonObject("gui").getAsJsonObject("item").getAsJsonObject("addon");
        return new ListGui(Main.getPlugin(), new GuiListEvent() {
            @Override
            public String title(int index, int size) {
                return MessageFormat.format(guiTranslation.get("title").getAsString(), itemIndex, index, size);
            }

            @Override
            public GuiItem[] pages(String s) {
                return Main.getPlugin().getApi().getAddons().stream().filter(addon -> addon.getName().contains(s)).map(addon -> new GuiItem(addon.getIcon(), new GuiItemEvent() {
                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        new ChooseItemTemplateGui(itemIndex, addon).createGui()[0].open((Player) event.getWhoClicked());
                    }
                })).toArray(GuiItem[]::new);
            }
        }, new GuiEvent() {
            @Override
            public void onClose(Gui gui, Player player) {
                Main.getPlugin().getBaseCommand().getPlayerGuiHashMap().put(player, gui);
            }
        }).createGui(guiTranslation, new ItemGui(itemIndex).createGui());
    }
}
