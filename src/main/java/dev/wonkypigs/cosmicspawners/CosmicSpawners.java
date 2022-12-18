package dev.wonkypigs.cosmicspawners;

import com.tchristofferson.configupdater.ConfigUpdater;
import dev.wonkypigs.cosmicspawners.listeners.PlayerBreakBlock;
import dev.wonkypigs.cosmicspawners.listeners.PlayerPlaceBlock;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class CosmicSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // if config version is old, update it to current version
        File configFile = new File(getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        if (config.getDouble("config-version") != 1.1) {
            config.set("config-version", 1.1);
            try {
                ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList("none"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // save changes
            try {
                config.save(configFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // reload config
            reloadConfig();
            getLogger().info("Updated config file to latest version");
        }


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
