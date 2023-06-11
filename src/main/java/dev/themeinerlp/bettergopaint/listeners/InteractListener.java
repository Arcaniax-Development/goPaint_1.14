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
package dev.themeinerlp.bettergopaint.listeners;

import dev.themeinerlp.bettergopaint.BetterGoPaint;
import dev.themeinerlp.bettergopaint.objects.player.ExportedPlayerBrush;
import dev.themeinerlp.bettergopaint.objects.player.PlayerBrush;
import dev.themeinerlp.bettergopaint.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InteractListener implements Listener {

    public BetterGoPaint plugin;

    public InteractListener(BetterGoPaint main) {
        plugin = main;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent e) {
        if (BetterGoPaint.nmsManager.isAtLeastVersion(1, 9, 0)) {
            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
        }
        if (!e.getPlayer().hasPermission("gopaint.use")) {
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            if (e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e
                    .getPlayer()
                    .getItemInHand()
                    .getItemMeta()
                    .getDisplayName()
                    .startsWith(" §b♦ ") && e.getPlayer().getItemInHand().getItemMeta().hasLore()) {
                final ExportedPlayerBrush epb = new ExportedPlayerBrush(e
                        .getPlayer()
                        .getItemInHand()
                        .getItemMeta()
                        .getDisplayName(), e.getPlayer().getItemInHand().getItemMeta().getLore());
                final Player p = e.getPlayer();
                final Location loc;
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    loc = p.getTargetBlock(null, 250).getLocation().clone();
                } else {
                    loc = e.getClickedBlock().getLocation().clone();
                }
                epb.getBrush().paint(loc, p, epb);
            }
        }
        if (e.getPlayer().getItemInHand().getType() == XMaterial.FEATHER.parseMaterial() && (e
                .getAction()
                .equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            e.setCancelled(true);
            final Player p = e.getPlayer();
            final Location loc;
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                loc = p.getTargetBlock(null, 250).getLocation().clone();
            } else {
                loc = e.getClickedBlock().getLocation().clone();
            }
            if ((!e.getPlayer().hasPermission("gopaint.world.bypass")) && (BetterGoPaint
                    .getSettings()
                    .getDisabledWorlds()
                    .contains(loc.getWorld().getName()))) {
                return;
            }
            if (loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
                return;
            }
            final PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            if (pb.isEnabled()) {
                pb.getBrush().paint(loc, p);
            } else {
                p.sendMessage(BetterGoPaint
                        .getSettings()
                        .getPrefix() + "§cYour brush is disabled, left click to enable the brush.");
            }
        }
        if (e.getPlayer().getItemInHand().getType() == XMaterial.FEATHER.parseMaterial() && (e
                .getAction()
                .equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            p.openInventory(pb.getInventory());
        }
    }

}
