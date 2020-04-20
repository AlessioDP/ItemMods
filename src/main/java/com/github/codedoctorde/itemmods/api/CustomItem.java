package com.github.codedoctorde.itemmods.api;

import com.github.codedoctorde.itemmods.Main;
import com.github.codedoctorde.itemmods.config.ItemConfig;
import com.gitlab.codedoctorde.api.server.Version;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class CustomItem {
    private ItemStack itemStack;
    private ItemConfig config;

    public CustomItem(ItemConfig config) {
        this.config = config;
    }

    public String getData() {
        NamespacedKey key = new NamespacedKey(Main.getPlugin(), "data");
        if (Version.getVersion().isLowerThan(Version.v1_15))
            return Objects.requireNonNull(itemStack.getItemMeta()).getCustomTagContainer().getCustomTag(key, ItemTagType.STRING);
        return Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
