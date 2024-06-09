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
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoPaintCommand extends Command implements PluginIdentifiableCommand {

    private final BetterGoPaint plugin;

    public GoPaintCommand(BetterGoPaint main) {
        super("gopaint", "goPaint command", "/gp size|toggle|info|reload", List.of("gp"));
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
        PlayerBrush pb = plugin.getBrushManager().getBrush(p);
        String prefix = Settings.settings().GENERIC.PREFIX;
        if (!p.hasPermission("bettergopaint.use")) {
            p.sendRichMessage(prefix + "<red>You are lacking the permission bettergopaint.use");
            return true;
        }
        if (args.length == 0) {
            if (p.hasPermission("bettergopaint.admin")) {
                p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload");
                return true;
            }
            p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>");
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("size")) {
                p.sendRichMessage(prefix + "<red>/gp size [number]");
                return true;
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (pb.isEnabled()) {
                    pb.toggle();
                    p.sendRichMessage(prefix + "<red>Disabled brush");
                } else {
                    pb.toggle();
                    p.sendRichMessage(prefix + "<green>Enabled brush");
                }
                return true;
            } else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) && p.hasPermission(
                    "bettergopaint.admin")) {
                plugin.reloadConfig();
                p.sendRichMessage(prefix + "<green>Reloaded");
                return true;
            } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
                p.sendRichMessage(prefix + "<aqua>Created by: <gold>TheMeinerLP");
                p.sendRichMessage(prefix + "<aqua>Links: <gold><click:open_url:https://twitter.com/themeinerlp'><u>Twitter</u></click>");
                return true;
            }
            if (p.hasPermission("bettergopaint.admin")) {
                p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload");
                return true;
            }
            p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info");
            return true;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("s")) {
                try {
                    int sizeAmount = Integer.parseInt(args[1]);
                    pb.setSize(sizeAmount);
                    p.sendRichMessage(prefix + "<gold>Size set to: <yellow>" + pb.getSize());
                    return true;
                } catch (Exception e) {
                    p.sendRichMessage(prefix + "<red>/gb size [number]");
                    return true;
                }
            }
            if (p.hasPermission("bettergopaint.admin")) {
                p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>|<red>reload");
                return true;
            }
            p.sendRichMessage(prefix + "<red>/gp size<gray>|<red>toggle<gray>|<red>info<gray>");
            return true;
        }
        return false;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return plugin;
    }

}
