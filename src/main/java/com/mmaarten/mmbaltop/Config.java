package com.mmaarten.mmbaltop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

/**
 * created on 29/04/2021 by Mmaarten. Project: MmBalTop
 */
public class Config {
    private FileConfiguration mainConfig = null;
    private File mainConfigFile = null;

    private Integer refreshInterval;
    private Integer pageSize;
    private String header;
    private String pattern;
    private String manual;
    private String noPerms;

    public Config() {
        saveDefaultConfig();
        reloadMainConfig();
    }

    public @NotNull FileConfiguration getMainConfig() {
        if (mainConfig == null) {
            reloadMainConfig();
        }
        return mainConfig;
    }

    public void reloadMainConfig() {
        if (mainConfigFile == null) {
            mainConfigFile = new File(MmBaltop.getInstance().getDataFolder(), "Config.yml");
        }
        mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(MmBaltop.getInstance().getResource("Config.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        mainConfig.setDefaults(defConfig);
    }

    public void saveDefaultConfig() {
        if (mainConfigFile == null) {
            mainConfigFile = new File(MmBaltop.getInstance().getDataFolder(), "Config.yml");
        }
        if (!mainConfigFile.exists()) {
            MmBaltop.getInstance().saveResource("Config.yml", false);
        }
    }

    public void saveMainConfig() {
        if (mainConfig == null || mainConfigFile == null) {
            return;
        }
        try {
            getMainConfig().save(mainConfigFile);
        } catch (IOException ex) {
            MmBaltop.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + mainConfigFile, ex);
        }
    }

    public int getRefreshInterval() {
        if (this.refreshInterval == null)
            this.refreshInterval = this.mainConfig.getInt("refresh-interval");
        return refreshInterval;
    }

    public int getPageSize() {
        if (this.pageSize == null)
            this.pageSize = this.mainConfig.getInt("player-amount-per-page");
        return pageSize;
    }

    public String getHeader() {
        if (this.header == null)
            this.header = this.mainConfig.getString("header");
        return header;
    }

    public String getPattern() {
        if (this.pattern == null)
            this.pattern = this.mainConfig.getString("pattern");
        return pattern;
    }

    public String getManual() {
        if (this.manual == null)
            this.manual = this.mainConfig.getString("manual-reload");
        return manual;
    }

    public String getNoPerms() {
        if (this.noPerms == null)
            this.noPerms = this.mainConfig.getString("no-permission");
        return noPerms;
    }
}
