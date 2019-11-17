package eu.vangora.itemmods.main;

import eu.vangora.itemmods.config.BlockConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomBlockManager {
    private final List<BlockConfig> blockConfigs;

    public CustomBlockManager(List<BlockConfig> blockConfigs) {
        this.blockConfigs = blockConfigs;
    }

    @Nullable
    public CustomBlock getCustomBlock(final Location location) {
        Location entityLocation = location.clone().add(0.5, 0, 0.5);
        List<Entity> entities = new ArrayList<>(entityLocation.getNearbyEntitiesByType(ArmorStand.class, 0.01, 0.001, 0.01));
        Bukkit.broadcastMessage(String.valueOf(entities));
        Bukkit.broadcastMessage(String.valueOf(blockConfigs));
        for (BlockConfig block : Main.getPlugin().getMainConfig().getBlocks()) {
            for (Entity entity : entities) {
                Bukkit.broadcastMessage(String.valueOf(block.getTag()));
                Bukkit.broadcastMessage(String.valueOf(entity.getScoreboardTags()));
                Bukkit.broadcastMessage(String.valueOf(entityLocation.distance(entity.getLocation())));
                if (entity.getType() == EntityType.ARMOR_STAND) if (entity.getScoreboardTags().contains(block.getTag()))
                    if (entityLocation.distance(entity.getLocation()) <= 0.5)
                        return new CustomBlock(block, location, (ArmorStand) entity);
            }
        }
        return null;
    }

    @Nullable
    public CustomBlock getCustomBlock(final Block block) {
        return getCustomBlock(block.getLocation());
    }

    public List<BlockConfig> getBlockConfigs() {
        return blockConfigs;
    }
}
