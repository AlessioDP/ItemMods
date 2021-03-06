package com.github.codedoctorde.itemmods.gui.choose.item;

import com.github.codedoctorde.itemmods.ItemMods;
import com.github.codedoctorde.itemmods.config.ItemConfig;
import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiEvent;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.ListGui;
import com.gitlab.codedoctorde.api.ui.template.events.GuiListEvent;
import com.google.gson.JsonObject;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.MessageFormat;

/**
 * @author CodeDoctorDE
 */
public class ChooseItemConfigGui {
    private final ChooseItemConfigEvent itemConfigEvent;

    public ChooseItemConfigGui(ChooseItemConfigEvent itemConfigEvent) {
        this.itemConfigEvent = itemConfigEvent;
    }

    public Gui[] createGui(Gui backGui) {
        JsonObject guiTranslation = ItemMods.getPlugin().getTranslationConfig().getJsonObject().getAsJsonObject("gui").getAsJsonObject("choose").getAsJsonObject("item").getAsJsonObject("config");
        return new ListGui(ItemMods.getPlugin(), new GuiListEvent() {
            @Override
            public String title(int index, int size) {
                return MessageFormat.format(guiTranslation.get("title").getAsString(), index + 1, size);
            }

            @Override
            public GuiItem[] pages(String s) {
                return ItemMods.getPlugin().getMainConfig().getItems().stream().filter(itemConfig -> itemConfig.getTag().contains(s)).map(itemConfig -> new GuiItem(itemConfig.getItemStack(), new GuiItemEvent() {
                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        itemConfigEvent.onEvent(itemConfig);
                    }
                })).toArray(GuiItem[]::new);
            }
        }, new GuiEvent() {
        }).createGui(guiTranslation, backGui);
    }

    public interface ChooseItemConfigEvent {
        void onEvent(ItemConfig itemConfig);
    }
}
