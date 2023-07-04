/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.themeinerlp.bettergopaint.command;

import dev.themeinerlp.bettergopaint.BetterGoPaint;
import dev.themeinerlp.bettergopaint.objects.other.Settings;
import dev.themeinerlp.bettergopaint.objects.player.PlayerBrush;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Handler implements Listener, CommandExecutor {

    public static BetterGoPaint plugin;

    public Handler(BetterGoPaint main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gopaint") || cmd.getName().equalsIgnoreCase("gp")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player p = (Player) sender;
            PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            String prefix = Settings.settings().GENERIC.PREFIX;
            if (!p.hasPermission("bettergopaint.use")) {
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>You are lacking the permission bettergopaint" +
                        ".use"));
                return true;
            }
            if (args.length == 0) {
                if (p.hasPermission("bettergopaint.admin")) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload"));
                    return true;
                }
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>"));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("size")) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "§<red>/gp size [number]"));
                    return true;
                } else if (args[0].equalsIgnoreCase("toggle")) {
                    if (pb.isEnabled()) {
                        pb.toggleEnabled();
                        p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "§cDisabled brush"));
                    } else {
                        pb.toggleEnabled();
                        p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "§aEnabled brush"));
                    }
                    return true;
                } else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) && p.hasPermission(
                        "bettergopaint.admin")) {
                    plugin.reload();
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<green>Reloaded"));
                    return true;
                } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix+ " <aqua>Created by: <gold>TheMeinerLP"));
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix+ " <aqua>Links: <gold><click:url_open:'https" +
                            "://twitter.com/themeinerlp'>Twitter      " +
                            "</click>"));
                    return true;
                }
                if (p.hasPermission("bettergopaint.admin")) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray" +
                            ">|<red>info" +
                            "<gray>" +
                            "|<red>reload"));
                    return true;
                }
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info"));
                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("s")) {
                    try {
                        int sizeAmount = Integer.parseInt(args[1]);
                        pb.setBrushSize(sizeAmount);
                        p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<gold>Size set to: <yellow>" + pb.getBrushSize()));
                        return true;
                    } catch (Exception e) {
                        p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gb size [number]"));
                        return true;
                    }
                }
                if (p.hasPermission("bettergopaint.admin")) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload"));
                    return true;
                }
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>"));
                return true;
            }
        }
        return false;
    }

}
