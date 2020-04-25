package com.github.codedoctorde.itemmods.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class ItemModsApi {
    private CustomItemManager customItemManager = new CustomItemManager();
    private CustomBlockManager customBlockManager = new CustomBlockManager();
    private List<ItemModsAddon> addons = new ArrayList<>();

    public CustomBlockManager getCustomBlockManager() {
        return customBlockManager;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

    public void registerAddon(ItemModsAddon addon) {
        addons.add(addon);
    }

    public List<CustomBlockTemplate> getCustomBlockTemplates() {
        List<CustomBlockTemplate> customBlockTemplates = new ArrayList<>();
        addons.stream().map(ItemModsAddon::getBlockTemplates).forEach(customBlockTemplates::addAll);
        return customBlockTemplates;
    }

    public List<CustomItemTemplate> getCustomItemTemplates() {
        List<CustomItemTemplate> customItemTemplates = new ArrayList<>();
        addons.stream().map(ItemModsAddon::getItemTemplates).forEach(customItemTemplates::addAll);
        return customItemTemplates;
    }

    public void unregisterAddon(ItemModsAddon addon) {
        addons.remove(addon);
    }

    public List<ItemModsAddon> getAddons() {
        return addons;
    }

    public CustomBlockTemplate getBlockTemplate(Class<? extends CustomBlockTemplate> templateClass) {
        return getCustomBlockTemplates().stream().filter(template -> template.getClass().equals(templateClass)).findFirst().orElse(null);
    }

    public CustomBlockTemplate getBlockTemplate(String templateClass) throws ClassNotFoundException {
        return getBlockTemplate((Class<? extends CustomBlockTemplate>) Class.forName(templateClass));
    }

    public CustomItemTemplate getItemTemplate(Class<? extends CustomItemTemplate> templateClass) {
        return getCustomItemTemplates().stream().filter(template -> template.getClass().equals(templateClass)).findFirst().orElse(null);
    }

    public CustomItemTemplate getItemTemplate(String templateClass) throws ClassNotFoundException {
        return getItemTemplate((Class<? extends CustomItemTemplate>) Class.forName(templateClass));
    }
}
