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
package net.onelitefeather.bettergopaint.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import net.onelitefeather.bettergopaint.objects.player.ExportedPlayerBrush;
import net.onelitefeather.bettergopaint.objects.player.PlayerBrush;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public final class InteractListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.PHYSICAL)) {
            return;
        }
        if (EquipmentSlot.OFF_HAND.equals(event.getHand())) {
            return;
        }

        if (!event.getPlayer().hasPermission("bettergopaint.use")) {
            return;
        }

        if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            if (event.getPlayer().getItemInHand().hasItemMeta() && event
                    .getPlayer()
                    .getItemInHand()
                    .getItemMeta()
                    .hasDisplayName() && event
                    .getPlayer()
                    .getItemInHand()
                    .getItemMeta()
                    .getDisplayName()
                    .startsWith(" <aqua>♦ ") && event.getPlayer().getItemInHand().getItemMeta().hasLore()) {
                final ExportedPlayerBrush epb = new ExportedPlayerBrush(event
                        .getPlayer()
                        .getItemInHand()
                        .getItemMeta()
                        .getDisplayName(), event.getPlayer().getItemInHand().getItemMeta().getLore());
                final Player p = event.getPlayer();
                final Location loc;
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    loc = p.getTargetBlock(null, 250).getLocation().clone();
                } else {
                    loc = event.getClickedBlock().getLocation().clone();
                }
                BukkitAdapter.adapt(p).runAsyncIfFree(() -> epb.getBrush().paint(loc, p, epb));
            }
        }
        if (event.getPlayer().getItemInHand().getType() == XMaterial.FEATHER.parseMaterial() && (event
                .getAction()
                .equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            event.setCancelled(true);
            final Player p = event.getPlayer();
            final Location loc;
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                loc = p.getTargetBlock(null, 250).getLocation().clone();
            } else {
                loc = event.getClickedBlock().getLocation().clone();
            }
            if ((!event.getPlayer().hasPermission("bettergopaint.world.bypass")) && (Settings.settings().GENERIC.DISABLED_WORLDS
                    .contains(loc.getWorld().getName()))) {
                return;
            }
            if (loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
                return;
            }
            final PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);

            if (pb.isEnabled()) {
                BukkitAdapter.adapt(p).runAsyncIfFree(() -> pb.getBrush().paint(loc, p));
            } else {
                p.sendMessage(MiniMessage.miniMessage().deserialize(Settings.settings().GENERIC.PREFIX + "<red>Your brush is " +
                        "disabled, left click to enable the brush."));
            }
        }
        if (event.getPlayer().getItemInHand().getType() == XMaterial.FEATHER.parseMaterial() && (event
                .getAction()
                .equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            event.setCancelled(true);
            Player p = event.getPlayer();
            PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            p.openInventory(pb.getInventory());
        }
    }

}
