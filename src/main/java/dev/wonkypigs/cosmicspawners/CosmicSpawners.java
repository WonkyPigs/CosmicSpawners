package dev.wonkypigs.cosmicspawners;

import dev.wonkypigs.cosmicspawners.listeners.PlayerBreakBlock;
import dev.wonkypigs.cosmicspawners.listeners.PlayerPlaceBlock;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmicSpawners extends JavaPlugin {

    public String prefix = ChatColor.translateAlternateColorCodes('&', "&8&l[&6CosmicSpawners&8]&r ");

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(), this);
        getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(), this);

        getLogger().info("CosmicSpawners has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CosmicSpawners has been disabled!");
    }

    // Getting values from config with color coding
    public String getConfigValue(String key) {
        String ans = getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }
}
