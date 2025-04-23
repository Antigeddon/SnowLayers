package me.antigeddon.snowlayers;

import org.bukkit.plugin.java.JavaPlugin;

public class sMain extends JavaPlugin {

    private static sMain instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new sSnow(this), this);
        log("initialized");
    }

    @Override
    public void onDisable() {
        log("is asleep ~ZZZzzzz...");
    }

    public void log(String message) {
        System.out.println("[SnowLayers] " + message);
    }

    public static sMain getInstance() {
        return instance;
    }
}
