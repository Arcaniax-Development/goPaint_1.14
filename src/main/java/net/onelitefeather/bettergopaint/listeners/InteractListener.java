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
import net.kyori.adventure.text.TextComponent;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.brush.ExportedPlayerBrush;
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public final class InteractListener implements Listener {

    private final BetterGoPaint plugin;

    public InteractListener(BetterGoPaint plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(BetterGoPaint.USE_PERMISSION)) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        if (event.getAction().isLeftClick() && item.getType().equals(Settings.settings().GENERIC.DEFAULT_BRUSH)) {
            PlayerBrush brush = plugin.getBrushManager().getBrush(player);
            player.openInventory(brush.getInventory());
            event.setCancelled(true);
            return;
        }

        if (!event.getAction().isRightClick()) {
            return;
        }

        Location location;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Block targetBlock = player.getTargetBlockExact(250, FluidCollisionMode.NEVER);
            if (targetBlock == null) {
                return;
            }
            location = targetBlock.getLocation().clone();
        } else if (event.getClickedBlock() != null) {
            location = event.getClickedBlock().getLocation();
        } else {
            return;
        }

        if (!player.hasPermission(BetterGoPaint.WORLD_BYPASS_PERMISSION) && Settings.settings().GENERIC.DISABLED_WORLDS
                .contains(location.getWorld().getName())) {
            return;
        }

        BrushSettings brushSettings;

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null && itemMeta.hasLore() && itemMeta.displayName() instanceof TextComponent name) {

            Optional<Brush> brush = plugin.getBrushManager().getBrushHandler(name.content());

            //noinspection removal
            brushSettings = brush.map(current -> ExportedPlayerBrush.parse(current, itemMeta)).orElse(null);
        } else if (item.getType().equals(Settings.settings().GENERIC.DEFAULT_BRUSH)) {
            brushSettings = plugin.getBrushManager().getBrush(player);
        } else {
            return;
        }

        if (brushSettings == null || brushSettings.blocks().isEmpty()) {
            return;
        }

        if (brushSettings.enabled()) {
            BukkitAdapter.adapt(player).runAction(
                    () -> brushSettings.brush().paint(location, player, brushSettings), false, true
            );
        } else {
            player.sendRichMessage(
                    Settings.settings().GENERIC.PREFIX + "<red>Your brush is disabled, left click to enable the brush."
            );
        }
    }

}
