package io.github.gizmo0odev.cashPlus;

import org.bukkit.plugin.java.JavaPlugin;

public final class CashPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
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
        getLogger().info("Cash+ Disabled!"); //a
        getLogger().info("");
        getLogger().info("Author: Gizmo0o");
        getLogger().info("");
        getLogger().info("--------------------");
    }
}
