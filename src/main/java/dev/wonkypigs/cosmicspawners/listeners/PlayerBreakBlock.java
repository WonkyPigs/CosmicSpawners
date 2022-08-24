package dev.wonkypigs.cosmicspawners.listeners;

import dev.wonkypigs.cosmicspawners.CosmicSpawners;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerBreakBlock implements Listener {

    private final CosmicSpawners plugin = CosmicSpawners.getPlugin(CosmicSpawners.class);

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.SPAWNER)
                && e.getPlayer().getGameMode() != GameMode.CREATIVE
                && (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial("STONE_PICKAXE"))
                || e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial("IRON_PICKAXE"))
                || e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial("GOLDEN_PICKAXE"))
                || e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial("DIAMOND_PICKAXE"))
                || e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial("NETHERITE_PICKAXE")))){
            if(plugin.getConfigValue("silk-touch-needed").equals("true") && !e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                return;
            }

            // get location of spawner
            Location blockLoc = e.getBlock().getLocation();
            CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();

            // don't drop exp
            e.setExpToDrop(0);

            // get item and set name
            ItemStack spawnerItem = new ItemStack(e.getBlock().getType(), 1);
            ItemMeta spawnerMeta = spawnerItem.getItemMeta();
            spawnerMeta.setDisplayName(ChatColor.RESET + "" + spawner.getSpawnedType() + " Spawner");

            // set item lore
            ArrayList lore = new ArrayList();
            lore.add("Mob Type Spawned:");
            lore.add(spawner.getSpawnedType().toString());
            spawnerMeta.setLore(lore);
            spawnerItem.setItemMeta(spawnerMeta);

            // drop spawner
            blockLoc.getWorld().dropItemNaturally(blockLoc, spawnerItem);
        }
    }
}