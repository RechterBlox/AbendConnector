package de.abendreden.abendconnector.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.DARK_AQUA + "AbendConnector " + ChatColor.GRAY + "| " + ChatColor.AQUA + "Version 1.0");
        return false;
    }
}
