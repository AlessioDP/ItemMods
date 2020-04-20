package com.github.codedoctorde.itemmods.gui;

import com.github.codedoctorde.itemmods.Main;
import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;

public class KnowledgeGui {
    public Gui createGui() {
        JsonObject guiTranslation = Main.getPlugin().getTranslationConfig().getJsonObject().getAsJsonObject("gui").getAsJsonObject("knowledge");
        return new Gui(Main.getPlugin(), guiTranslation.get("title").getAsString(), 5) {{
            getGuiItems().put(9 + 4, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("comingsoon"))));
        }};
    }
}
