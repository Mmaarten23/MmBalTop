package com.mmaarten.mmbaltop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * created on 29/04/2021 by Mmaarten. Project: MmBalTop
 */
public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mmbaltop.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MmBaltop.getInstance().getMainConfig().getNoPerms()));
            return true;
        }
        MmBaltop.getInstance().getCalculator().recalculate();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MmBaltop.getInstance().getMainConfig().getManual()));
        return true;
    }
}
