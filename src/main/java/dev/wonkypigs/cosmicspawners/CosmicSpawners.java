package dev.wonkypigs.cosmicspawners;

import dev.wonkypigs.cosmicspawners.listeners.PlayerBreakBlock;
import dev.wonkypigs.cosmicspawners.listeners.PlayerPlaceBlock;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmicSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(), this);
        getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(), this);

        int pluginId = 17092; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        UpdateChecker updateChecker = new UpdateChecker();
        updateChecker.check();
        getServer().getPluginManager().registerEvents(updateChecker, this);

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

    public static CosmicSpawners getInstance() {
        return getPlugin(CosmicSpawners.class);
    }
}
