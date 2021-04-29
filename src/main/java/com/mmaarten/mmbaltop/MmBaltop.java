package com.mmaarten.mmbaltop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MmBaltop extends JavaPlugin {
    private static MmBaltop instance;
    private Economy econ;
    private Calculator calculator;
    private Config config;

    public MmBaltop() {
        instance = this;

    }

    public static MmBaltop getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        this.config = new Config();
        this.calculator = new Calculator();

        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("mmbaltop")).setExecutor(new BalTopCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("mmbaltopreload")).setExecutor(new ReloadCommand());

        long interval = this.config.getRefreshInterval();
        Bukkit.getScheduler().runTaskTimer(
                this,
                calculator::recalculate,
                0,
                interval * 60 * 20
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public Economy getEcon() {
        return econ;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public Config getMainConfig() {
        return config;
    }
}
