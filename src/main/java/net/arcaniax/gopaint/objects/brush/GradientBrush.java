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
 *                     Copyright (C) 2022 Arcaniax
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
package net.arcaniax.gopaint.objects.brush;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.other.BlockPlace;
import net.arcaniax.gopaint.objects.other.BlockPlacer;
import net.arcaniax.gopaint.objects.other.BlockType;
import net.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Sphere;
import net.arcaniax.gopaint.utils.Surface;
import net.arcaniax.gopaint.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GradientBrush extends Brush {

    @SuppressWarnings({"deprecation"})
    @Override
    public void paint(Location loc, Player p) {
        PlayerBrush pb = GoPaintPlugin.getBrushManager().getPlayerBrush(p);
        int size = pb.getBrushSize();
        int falloff = pb.getFalloffStrength();
        int mixing = pb.getMixingStrength();
        List<BlockType> pbBlocks = pb.getBlocks();
        if (pbBlocks.isEmpty()) {
            return;
        }
        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        double y = loc.getBlockY() - ((double) size / 2.0);
        List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
        for (Block b : blocks) {
            if ((!pb.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                if ((!pb.isMaskEnabled()) || (b.getType().equals(pb
                        .getMask()
                        .getMaterial()) && (XMaterial.isNewVersion() || b.getData() == pb.getMask().getData()))) {
                    double _y = (b.getLocation().getBlockY() - y) / (double) size * pbBlocks.size();
                    Random r = new Random();
                    int block = (int) (_y + (r.nextDouble() * 2 - 1) * ((double) mixing / 100.0));
                    if (block == -1) {
                        block = 0;
                    }
                    if (block == pbBlocks.size()) {
                        block = pbBlocks.size() - 1;
                    }
                    double rate = (b
                            .getLocation()
                            .distance(loc) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) / (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));

                    if (!(r.nextDouble() <= rate)) {
                        placedBlocks.add(
                                new BlockPlace(
                                        b.getLocation(),
                                        new BlockType(pbBlocks.get(block).getMaterial(), pbBlocks.get(block).getData())
                                ));
                    }
                }
            }

        }
        BlockPlacer bp = new BlockPlacer();
        bp.placeBlocks(placedBlocks, p);
    }

    @Override
    public String getName() {
        return "Gradient Brush";
    }

    @SuppressWarnings("deprecation")
    @Override
    public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
        int size = epb.getBrushSize();
        List<BlockType> epbBlocks = epb.getBlocks();
        int falloff = epb.getFalloffStrength();
        int mixing = epb.getMixingStrength();
        if (epbBlocks.isEmpty()) {
            return;
        }
        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        double y = loc.getBlockY() - ((double) size / 2.0);
        List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
        for (Block b : blocks) {
            if ((!epb.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                if ((!epb.isMaskEnabled()) || (b.getType().equals(epb
                        .getMask()
                        .getMaterial()) && (XMaterial.isNewVersion() || b.getData() == epb.getMask().getData()))) {
                    double _y = (b.getLocation().getBlockY() - y) / (double) size * epbBlocks.size();
                    Random r = new Random();
                    int block = (int) (_y + (r.nextDouble() * 2 - 1) * ((double) mixing / 100.0));
                    if (block == -1) {
                        block = 0;
                    }
                    if (block == epbBlocks.size()) {
                        block = epbBlocks.size() - 1;
                    }
                    double rate = (b
                            .getLocation()
                            .distance(loc) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) / (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));

                    if (!(r.nextDouble() <= rate)) {
                        placedBlocks.add(
                                new BlockPlace(
                                        b.getLocation(),
                                        new BlockType(epbBlocks.get(block).getMaterial(), epbBlocks.get(block).getData())
                                ));
                    }
                }
            }
        }
        BlockPlacer bp = new BlockPlacer();
        bp.placeBlocks(placedBlocks, p);
    }

}
