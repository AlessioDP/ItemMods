package com.github.codedoctorde.itemmods.config;

import com.github.codedoctorde.itemmods.api.CustomBlockTemplate;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockConfig {
    private String name;
    private String tag = "";
    private String displayName;
    private BlockData block;
    private boolean drop = true;
    private boolean moving = false;
    private ItemStack itemStack;
    private String data = null;
    private ArmorStandBlockConfig armorStand = null;
    private CustomBlockTemplate blockTemplate;
    private List<DropConfig> drops = new ArrayList<>();

    public boolean checkBlock(BlockState block) {
        return armorStand != null || block instanceof TileState;
    }

    public BlockConfig() {

    }

    public BlockConfig(String name) {
        this.name = name;
        this.displayName = name;
        this.tag = "itemmods:" + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockData getBlock() {
        return block;
    }

    public void setBlock(BlockData block) {
        this.block = block;
    }

    public boolean isArmorStand() {
        return armorStand != null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public CustomBlockTemplate getBlockTemplate() {
        return blockTemplate;
    }

    public void setBlockTemplate(CustomBlockTemplate blockTemplate) {
        this.blockTemplate = blockTemplate;
    }

    @Nullable
    public ArmorStandBlockConfig getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStandBlockConfig armorStand) {
        this.armorStand = armorStand;
    }

    public boolean isDrop() {
        return drop;
    }

    public List<DropConfig> getDrops() {
        return drops;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public CorrectResult correct() {
        if (block == null)
            return CorrectResult.NO_BLOCK;
        return CorrectResult.YES;
    }

    public enum CorrectResult {
        YES,
        ENTITY,
        NO_BLOCK
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
