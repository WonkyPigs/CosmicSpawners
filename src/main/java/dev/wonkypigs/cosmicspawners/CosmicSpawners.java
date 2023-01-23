package dev.wonkypigs.cosmicspawners;

import dev.wonkypigs.cosmicspawners.listeners.PlayerBreakBlock;
import dev.wonkypigs.cosmicspawners.listeners.PlayerPlaceBlock;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class CosmicSpawners extends JavaPlugin {

    public double confVersion = 1.1;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // if config version is old, update it to current version
        updateConfig();


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

    public void updateConfig() {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));

        if (config.getDouble("config-version") <= 1.0) {
            // rename config.yml to old-config.yml
            File oldConfig = new File(getDataFolder(), "old-config.yml");
            File configFile = new File(getDataFolder(), "config.yml");
            configFile.renameTo(oldConfig);

            // create new config.yml
            saveDefaultConfig();
            getConfig().set("config-version", confVersion);
            getLogger().severe("==========================");
            getLogger().info("You were using an old format of");
            getLogger().info("the config.yml file. It has been");
            getLogger().info("updated to the current version.");
            getLogger().info("Make sure to update all values!");
            getLogger().severe("==========================");
            return;
        }

        config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));

        if (config.getDouble("config-version") != confVersion) {
            try {
                new ConfigUpdater(this, "config.yml", "config-updater.yml").update();
            } catch (IOException e) {
                getLogger().severe("Could not update config.yml!");
                e.printStackTrace();
            }
        }
        reloadConfig();
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
