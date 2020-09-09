/*
 *                             _____      _       _
 *                            |  __ \    (_)     | |
 *                  __ _  ___ | |__) |_ _ _ _ __ | |_
 *                 / _` |/ _ \|  ___/ _` | | '_ \| __|
 *                | (_| | (_) | |  | (_| | | | | | |_
 *                 \__, |\___/|_|   \__,_|_|_| |_|\__|
 *                  __/ |
 *                 |___/
 *
 *    goPaint is designed to simplify painting inside of Minecraft.
 *                     Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.arcaniax.gopaint.objects.other;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BlockPlacer {

    public BlockPlacer() {
    }

    public boolean isGmask(Player player, BlockVector3 v) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(new BukkitPlayer(player));
		return localSession.getMask() == null || localSession.getMask().test(v);
	}

    public void placeBlocks(Collection<BlockPlace> blocks, final Player p) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(new BukkitPlayer(p));
        try (EditSession editsession = localSession.createEditSession(new BukkitPlayer(p))) {
            try {
                editsession.setFastMode(false);
                for (BlockPlace bp : blocks) {
                    Location l = bp.getLocation();
                    Vector3 v = Vector3.at(l.getBlockX(), l.getBlockY(), l.getBlockZ());
                    if (isGmask(p, v.toBlockPoint())) {
                        try {
                            editsession.setBlock(Vector3.at(l.getBlockX(), l.getBlockY(), l.getBlockZ()).toBlockPoint(), BukkitAdapter.asBlockType(bp.bt.getMaterial()).getDefaultState());
                        } catch (Exception ignored) {
                        }
                    }
                }
            } finally {
                localSession.remember(editsession);
            }
        }
    }
}
