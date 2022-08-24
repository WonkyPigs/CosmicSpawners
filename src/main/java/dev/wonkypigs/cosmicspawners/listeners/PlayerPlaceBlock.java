package dev.wonkypigs.cosmicspawners.listeners;

import dev.wonkypigs.cosmicspawners.CosmicSpawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlock implements Listener {

    private final CosmicSpawners plugin = CosmicSpawners.getPlugin(CosmicSpawners.class);

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getBlockPlaced().getType().equals(Material.SPAWNER)) {
            CreatureSpawner spawner = (CreatureSpawner) e.getBlockPlaced().getState();
            try{
                spawner.setSpawnedType(EntityType.valueOf(e.getItemInHand().getItemMeta().getLore().get(1)));
            }catch (Exception err){}
            spawner.update();
        }
    }
}
