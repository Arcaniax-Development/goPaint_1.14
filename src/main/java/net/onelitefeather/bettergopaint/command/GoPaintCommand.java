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
package net.onelitefeather.bettergopaint.command;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import net.onelitefeather.bettergopaint.objects.player.PlayerBrush;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GoPaintCommand extends Command implements PluginIdentifiableCommand {

    public static BetterGoPaint plugin;

    public GoPaintCommand(BetterGoPaint main) {
        super("gopaint");
        plugin = main;
    }

    @Override
    public boolean execute(
            @NotNull final CommandSender sender,
            @NotNull final String commandLabel,
            final @NotNull String[] args
    ) {
        if (!(sender instanceof final Player p)) {
            return false;
        }
        PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
        String prefix = Settings.settings().generic.PREFIX;
        if (!p.hasPermission("bettergopaint.use")) {
            p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>You are lacking the permission bettergopaint" +
                    ".use"));
            return true;
        }
        if (args.length == 0) {
            if (p.hasPermission("bettergopaint.admin")) {
                p.sendMessage(MiniMessage
                        .miniMessage()
                        .deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload"));
                return true;
            }
            p.sendMessage(MiniMessage
                    .miniMessage()
                    .deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>"));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("size")) {
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gp size [number]"));
                return true;
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (pb.isEnabled()) {
                    pb.toggleEnabled();
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>Disabled brush"));
                } else {
                    pb.toggleEnabled();
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<green>Enabled brush"));
                }
                return true;
            } else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) && p.hasPermission(
                    "bettergopaint.admin")) {
                plugin.reload();
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<green>Reloaded"));
                return true;
            } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<aqua>Created by: <gold>TheMeinerLP"));
                p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<aqua>Links: <gold><click:open_url:https" +
                        "://twitter.com/themeinerlp'><u>Twitter</u></click>"));
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
                    p.sendMessage(MiniMessage
                            .miniMessage()
                            .deserialize(prefix + "<gold>Size set to: <yellow>" + pb.getBrushSize()));
                    return true;
                } catch (Exception e) {
                    p.sendMessage(MiniMessage.miniMessage().deserialize(prefix + "<red>/gb size [number]"));
                    return true;
                }
            }
            if (p.hasPermission("bettergopaint.admin")) {
                p.sendMessage(MiniMessage
                        .miniMessage()
                        .deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload"));
                return true;
            }
            p.sendMessage(MiniMessage
                    .miniMessage()
                    .deserialize(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>"));
            return true;
        }
        return false;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

}
