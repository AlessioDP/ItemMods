package eu.vangora.itemmods.listener;

import eu.vangora.itemmods.config.BlockConfig;
import eu.vangora.itemmods.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockListener implements Listener {

    @EventHandler
    public void onCustomBlockPlaced(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        for (BlockConfig block :
                Main.getPlugin().getMainConfig().getBlocks()) {
            if (event.getItem().isSimilar(block.getItemStack())) {
                if (event.getItem().getAmount() < block.getItemStack().getAmount()) return;
                event.setCancelled(true);
                Player player = event.getPlayer();
                Location location = event.getClickedBlock().getLocation();
                switch (event.getBlockFace()) {
                    case UP:
                        location.add(0, 1, 0);
                        break;
                    case DOWN:
                        location.add(0, -1, 0);
                        break;
                    case EAST:
                        location.add(1, 0, 0);
                        break;
                    case WEST:
                        location.add(-1, 0, 0);
                        break;
                    case NORTH:
                        location.add(0, 0, -1);
                        break;
                    case SOUTH:
                        location.add(0, 0, 1);
                        break;
                }
                if (location.getNearbyPlayers(0.5).contains(event.getPlayer())) return;
                if (!location.getBlock().isEmpty() || location.getBlock().getState().equals(block.getBlock())) return;
                location.getWorld().getBlockAt(location).setBlockData(block.getBlock());
                ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                if (armorStand.getEquipment() == null)
                    return;
                EntityEquipment equipment = armorStand.getEquipment();
                armorStand.setBasePlate(block.isBasePlate());
                equipment.setHelmet(block.getHelmet());
                equipment.setChestplate(block.getChestplate());
                equipment.setLeggings(block.getLeggings());
                equipment.setBoots(block.getBoots());
                equipment.setItemInMainHand(block.getMainHand());
                equipment.setItemInOffHand(block.getOffHand());
                armorStand.setSmall(block.isSmall());
                armorStand.setMarker(block.isMarker());
                armorStand.setInvulnerable(block.isInvulnerable());
                armorStand.setCustomNameVisible(block.isCustomNameVisible());
                armorStand.setCustomName(block.getCustomName());
                armorStand.setVisible(!block.isInvisible());
                armorStand.getScoreboardTags().add(block.getTag());
                armorStand.setGravity(false);
                event.getItem().setAmount(event.getItem().getAmount() - block.getItemStack().getAmount());
            }
        }
    }

    @EventHandler
    public void onCustomBlockBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation().add(0.5, 0, 0.5);
        List<Entity> entities = new ArrayList<>(location.getNearbyEntitiesByType(ArmorStand.class, 0.01, 0.001, 0.01));
        if (entities.size() < 1) return;
        Main.getPlugin().getMainConfig().getBlocks().forEach(block -> entities.stream().filter(entity -> entity.getType() == EntityType.ARMOR_STAND).map(entity -> (ArmorStand) entity).filter(armorStand -> armorStand.getScoreboardTags().contains(block.getTag())).forEach(armorStand -> {
            if (location.distance(armorStand.getLocation()) <= 0.5) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(armorStand.getLocation().toString());
                event.getBlock().setType(Material.AIR);
                event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), block.getItemStack());
                armorStand.remove();
            }
        }));
    }

    @EventHandler
    public void onCustomBlockMove(BlockFromToEvent event) {
        List<Entity> entities = new ArrayList<>(event.getBlock().getLocation().add(0.5, 0.1, 0.5).getNearbyLivingEntities(0.05, 0.001));
        if (entities.size() < 1) return;
        Main.getPlugin().getMainConfig().getBlocks().forEach(block -> entities.stream().filter(entity -> entity.getType() == EntityType.ARMOR_STAND).map(entity -> (ArmorStand) entity).filter(armorStand -> armorStand.getScoreboardTags().contains(block.getTag())).forEach(armorStand -> {
            armorStand.teleport(event.getToBlock().getLocation());
        }));
    }

    @EventHandler
    public void onCustomBlockPistonMove(BlockPistonExtendEvent event) {
        List<Block> blocks = event.getBlocks();
        Vector direction = event.getDirection().getDirection();
        for (Block block :
                blocks) {
            List<Entity> entities = new ArrayList<>(block.getLocation().add(0.5, 0.1, 0.5).getNearbyLivingEntities(0.05, 0.001));
            if (entities.size() < 1) return;
            Main.getPlugin().getMainConfig().getBlocks().forEach(blockConfig -> entities.stream().filter(entity -> entity.getType() == EntityType.ARMOR_STAND).map(entity -> (ArmorStand) entity).filter(armorStand -> armorStand.getScoreboardTags().contains(blockConfig.getTag())).forEach(armorStand -> {
                event.setCancelled(true);
            }));
        }
    }

    @EventHandler
    public void onCustomBlockPistonMove(BlockPistonRetractEvent event) {
        List<Block> blocks = event.getBlocks();
        Vector direction = event.getDirection().getDirection();
        for (Block block :
                blocks) {
            List<Entity> entities = new ArrayList<>(block.getLocation().add(0.5, 0.1, 0.5).getNearbyLivingEntities(0.05, 0.001));
            if (entities.size() < 1) return;
            Main.getPlugin().getMainConfig().getBlocks().forEach(blockConfig -> entities.stream().filter(entity -> entity.getType() == EntityType.ARMOR_STAND).map(entity -> (ArmorStand) entity).filter(armorStand -> armorStand.getScoreboardTags().contains(blockConfig.getTag())).forEach(armorStand -> {
                event.setCancelled(true);
            }));
        }
    }

    @EventHandler
    public void onCustomBlockFall(EntityChangeBlockEvent event) {
        List<Entity> entities = new ArrayList<>(event.getBlock().getLocation().add(0.5, 0, 0.5).getNearbyLivingEntities(0.1, 0.01));
        if (entities.size() < 1) return;
        Main.getPlugin().getMainConfig().getBlocks().forEach(block -> entities.stream().filter(entity -> entity.getType() == EntityType.ARMOR_STAND).map(entity -> (ArmorStand) entity).filter(armorStand -> armorStand.getScoreboardTags().contains(block.getTag())).forEach(armorStand -> {
            event.setCancelled(true);
        }));
    }
}
