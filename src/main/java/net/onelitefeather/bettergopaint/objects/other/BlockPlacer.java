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
package net.onelitefeather.bettergopaint.objects.other;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class BlockPlacer {

    public static void placeBlocks(Collection<BlockPlace> blocks, final Player player) {
        BukkitPlayer wrapped = BukkitAdapter.adapt(player);
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(wrapped);
        try (EditSession editsession = localSession.createEditSession(wrapped)) {
            try {
                editsession.setFastMode(false);
                for (BlockPlace placement : blocks) {
                    Location l = placement.getLocation();
                    Vector3 v = Vector3.at(l.getBlockX(), l.getBlockY(), l.getBlockZ());
                    if (localSession.getMask() != null && !localSession.getMask().test(v.toBlockPoint())) {
                        continue;
                    }
                    try {
                        editsession.setBlock(
                                l.getBlockX(), l.getBlockY(), l.getBlockZ(),
                                BukkitAdapter.asBlockType(placement.bt.getMaterial()).getDefaultState()
                        );
                    } catch (MaxChangedBlocksException ignored) {
                        break;
                    }
                }
            } finally {
                localSession.remember(editsession);
            }
        }
    }

}
