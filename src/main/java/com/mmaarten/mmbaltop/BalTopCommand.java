package com.mmaarten.mmbaltop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * created on 29/04/2021 by Mmaarten. Project: MmBalTop
 */
public class BalTopCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mmbaltop.use")) {
            sender.sendMessage(MmBaltop.getInstance().getMainConfig().getNoPerms());
            return true;
        }

        List<BalanceWrapper> order = MmBaltop.getInstance().getCalculator().getCurrentBalTop();
        int pageNr = 1;
        if (args.length > 0)
            try {
                pageNr = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
            }
        int pageSize = MmBaltop.getInstance().getMainConfig().getPageSize();
        if (pageNr < 1 || pageNr > (order.size() / pageSize) + 1) pageNr = 1;
        printBalTop(
                sender,
                order.subList((pageNr - 1) * pageSize, Math.min(pageNr * pageSize, order.size())),
                (pageNr - 1) * pageSize + 1
        );
        return true;
    }

    private void printBalTop(CommandSender sender, List<BalanceWrapper> toSend, int startIndex) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime lastCalculated = MmBaltop.getInstance().getCalculator().getLastCalculated();
        String[] header = ChatColor.translateAlternateColorCodes(
                '&',
                MmBaltop.getInstance().getMainConfig().getHeader()
                        .replace("%lastRecalculated%", dtf.format(lastCalculated))
        ).split("\n");
        sender.sendMessage(header);
        String pattern = MmBaltop.getInstance().getMainConfig().getPattern();
        for (BalanceWrapper bw : toSend) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    pattern.replace("%index%", String.valueOf(startIndex++))
                            .replace("%name%", bw.getPlayer().getName())
                            .replace("%balance%", String.valueOf(bw.getBalance()))
            ));
        }
    }
}
