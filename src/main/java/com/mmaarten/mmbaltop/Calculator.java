package com.mmaarten.mmbaltop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created on 29/04/2021 by Mmaarten. Project: MmBalTop
 */
public class Calculator {
    private List<BalanceWrapper> currentBalTop;
    private LocalDateTime lastCalculated;

    public Calculator() {
        this.currentBalTop = new ArrayList<>();
    }

    public List<BalanceWrapper> getCurrentBalTop() {
        return currentBalTop;
    }

    public LocalDateTime getLastCalculated() {
        return lastCalculated;
    }

    public void recalculate() {
        MmBaltop.getInstance().getLogger().info("Recalculating baltop...");
        OfflinePlayer[] players = MmBaltop.getInstance().getServer().getOfflinePlayers();
        Bukkit.getScheduler().runTaskAsynchronously(
                MmBaltop.getInstance(),
                () -> {
                    List<BalanceWrapper> wrappers = new ArrayList<>(players.length);
                    Economy econ = MmBaltop.getInstance().getEcon();
                    for (OfflinePlayer player : players) {
                        wrappers.add(new BalanceWrapper(player, econ.getBalance(player)));
                    }
                    Collections.sort(wrappers);
                    this.currentBalTop = wrappers;
                    this.lastCalculated = LocalDateTime.now();
                    MmBaltop.getInstance().getLogger().info("Baltop Recalculated!");
                }
        );
    }
}
