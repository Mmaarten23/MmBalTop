package com.mmaarten.mmbaltop;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * created on 29/04/2021 by Mmaarten. Project: MmBalTop
 */
public class BalanceWrapper implements Comparable<BalanceWrapper> {
    private final OfflinePlayer player;
    private final double balance;

    public BalanceWrapper(OfflinePlayer player, double balance) {
        this.player = player;
        this.balance = balance;
    }

    @Override
    public int compareTo(@NotNull BalanceWrapper other) {
        return -1 * Double.compare(this.balance, other.balance);
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public double getBalance() {
        return balance;
    }
}
