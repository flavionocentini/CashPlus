package io.github.gizmo0odev.cashPlus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class CashPlus extends JavaPlugin {

    private static Economy economy;
    private static CashPlus instance;
    private static FileConfiguration config;

    public static String prefix;
    public static Material cashMaterial;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if (!setupEconomy()) {
            getLogger().severe("[Cash+] Vault not found or no economy plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        saveDefaultConfig();
        config = getConfig();
        Utils.init(this);
        prefix = CashPlus.getPluginConfig().getString("prefix");
        cashMaterial = Material.valueOf(CashPlus.getPluginConfig().getString("cash-material"));
        Commands commands = new Commands();
        getCommand("withdraw").setExecutor(commands);
        getCommand("deposit").setExecutor(commands);
        getCommand("cashplus").setExecutor(commands);
        getLogger().info("--------------------");
        getLogger().info("");
        getLogger().info("Cash+ Enabled!");
        getLogger().info("");
        getLogger().info("Author: Gizmo0o");
        getLogger().info("");
        getLogger().info("--------------------");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("--------------------");
        getLogger().info("");
        getLogger().info("Cash+ Disabled!");
        getLogger().info("");
        getLogger().info("Author: Gizmo0o");
        getLogger().info("");
        getLogger().info("--------------------");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        config = getConfig();
    }

    public static CashPlus getInstance() {
        return instance;
    }

    public static FileConfiguration getPluginConfig() {
        return config;
    }

}
