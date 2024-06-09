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
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.brush.ExportedPlayerBrush;
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public final class InteractListener implements Listener {

    private final BetterGoPaint plugin;

    public InteractListener(BetterGoPaint plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("bettergopaint.use")) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        if (event.getAction().isLeftClick() && item.getType().equals(Material.FEATHER)) {
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
            Block exact = player.getTargetBlockExact(250, FluidCollisionMode.NEVER);
            location = exact != null ? exact.getLocation().clone() : null;
        } else {
            location = event.getInteractionPoint();
        }

        if (location == null) {
            return;
        }

        if ((!player.hasPermission("bettergopaint.world.bypass")) && (Settings.settings().GENERIC.DISABLED_WORLDS
                .contains(location.getWorld().getName()))) {
            return;
        }

        BrushSettings brushSettings;

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null && itemMeta.displayName() instanceof TextComponent name
                && name.content().startsWith(" ♦ ") && itemMeta.hasLore()) {
            brushSettings = new ExportedPlayerBrush(plugin, name, Objects.requireNonNull(itemMeta.lore()));
        } else if (item.getType().equals(Material.FEATHER)) {
            brushSettings = plugin.getBrushManager().getBrush(player);
        } else {
            return;
        }

        if (brushSettings.getBlocks().isEmpty()) {
            return;
        }

        if (brushSettings.isEnabled()) {
            BukkitAdapter.adapt(player).runAsyncIfFree(() -> brushSettings.getBrush().paint(location, player, brushSettings));
        } else {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    Settings.settings().GENERIC.PREFIX + "<red>Your brush is disabled, left click to enable the brush."
            ));
        }
    }

}
