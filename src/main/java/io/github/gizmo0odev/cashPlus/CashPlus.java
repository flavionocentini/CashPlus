package io.github.gizmo0odev.cashPlus;

import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class CashPlus extends JavaPlugin {

    private static Economy economy;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy()) {
            getLogger().severe("[Cash+] Vault not found or no economy plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getCommand("withdraw").setExecutor(new Commands());
        this.getCommand("deposit").setExecutor(new Commands());
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

}
