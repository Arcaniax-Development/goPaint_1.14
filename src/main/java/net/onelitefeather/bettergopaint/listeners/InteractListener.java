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

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import net.onelitefeather.bettergopaint.brush.ExportedPlayerBrush;
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class InteractListener implements Listener {

    private final BetterGoPaint plugin;

    public InteractListener(BetterGoPaint plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent event) {
        if (EquipmentSlot.OFF_HAND.equals(event.getHand())) {
            return;
        }

        if (!event.getPlayer().hasPermission("bettergopaint.use")) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }


        if (event.getAction().isLeftClick() && event.getMaterial().equals(Material.FEATHER)) {
            event.setCancelled(true);
            PlayerBrush brush = plugin.getBrushManager().getBrush(event.getPlayer());
            event.getPlayer().openInventory(brush.getInventory());
            return;
        }

        if (!event.getAction().isRightClick()) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null
                && itemMeta.hasDisplayName()
                && itemMeta.getDisplayName().startsWith(" <aqua>♦ ")
                && itemMeta.hasLore()) {
            ExportedPlayerBrush brush = new ExportedPlayerBrush(plugin, itemMeta.getDisplayName(), itemMeta.getLore());
            Player player = event.getPlayer();
            Location location;
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                location = player.getTargetBlock(null, 250).getLocation().clone();
            } else {
                location = event.getInteractionPoint();
            }

            if (brush.getBlocks().isEmpty()) {
                return;
            }
            BukkitAdapter.adapt(player).runAsyncIfFree(() -> brush.getBrush().paint(location, player, brush));
            return;
        }

        if (item.getType() == Material.FEATHER) {
            event.setCancelled(true);
            final Player player = event.getPlayer();
            final Location location;
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                location = player.getTargetBlock(null, 250).getLocation().clone();
            } else {
                location = event.getClickedBlock().getLocation().clone();
            }
            if ((!event.getPlayer().hasPermission("bettergopaint.world.bypass")) && (Settings.settings().GENERIC.DISABLED_WORLDS
                    .contains(location.getWorld().getName()))) {
                return;
            }
            if (location.getBlock().isEmpty()) {
                return;
            }
            final PlayerBrush brush = plugin.getBrushManager().getBrush(player);

            if (brush.isEnabled()) {
                if (brush.getBlocks().isEmpty()) {
                    return;
                }
                BukkitAdapter.adapt(player).runAsyncIfFree(() -> brush.getBrush().paint(location, player, brush));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        Settings.settings().GENERIC.PREFIX + "<red>Your brush is disabled, left click to enable the brush."
                ));
            }
        }
    }

}
