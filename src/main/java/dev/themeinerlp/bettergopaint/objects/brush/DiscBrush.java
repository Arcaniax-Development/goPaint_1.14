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
package dev.themeinerlp.bettergopaint.objects.brush;

import com.cryptomorin.xseries.XMaterial;
import dev.themeinerlp.bettergopaint.BetterGoPaint;
import dev.themeinerlp.bettergopaint.objects.other.BlockPlace;
import dev.themeinerlp.bettergopaint.objects.other.BlockPlacer;
import dev.themeinerlp.bettergopaint.objects.other.BlockType;
import dev.themeinerlp.bettergopaint.objects.player.ExportedPlayerBrush;
import dev.themeinerlp.bettergopaint.utils.Sphere;
import dev.themeinerlp.bettergopaint.utils.Surface;
import dev.themeinerlp.bettergopaint.utils.XMaterial;
import dev.themeinerlp.bettergopaint.objects.player.PlayerBrush;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscBrush extends Brush {

    @SuppressWarnings("deprecation")
    @Override
    public void paint(Location loc, Player p) {
        PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
        int size = pb.getBrushSize();
        List<BlockType> pbBlocks = pb.getBlocks();
        if (pbBlocks.isEmpty()) {
            return;
        }
        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
        for (Block b : blocks) {
            if ((pb.getAxis().equals("y") && b.getLocation().getBlockY() == loc.getBlockY()) || (pb.getAxis().equals("x") && b
                    .getLocation()
                    .getBlockX() == loc.getBlockX()) || (pb.getAxis().equals("z") && b
                    .getLocation()
                    .getBlockZ() == loc.getBlockZ())) {
                if ((!pb.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                    if ((!pb.isMaskEnabled()) || (b.getType().equals(pb
                            .getMask()
                            .getMaterial()) && (XMaterial.supports(13) || b.getData() == pb.getMask().getData()))) {
                        Random r = new Random();
                        int random = r.nextInt(pbBlocks.size());
                        placedBlocks.add(
                                new BlockPlace(
                                        b.getLocation(),
                                        new BlockType(pbBlocks.get(random).getMaterial(), pbBlocks.get(random).getData())
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
        return "Disc Brush";
    }

    @SuppressWarnings("deprecation")
    @Override
    public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
        int size = epb.getBrushSize();
        List<BlockType> epbBlocks = epb.getBlocks();
        if (epbBlocks.isEmpty()) {
            return;
        }
        List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
        List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
        for (Block b : blocks) {
            if ((epb.getAxis().equals("y") && b.getLocation().getBlockY() == loc.getBlockY()) || (epb.getAxis().equals("x") && b
                    .getLocation()
                    .getBlockX() == loc.getBlockX()) || (epb.getAxis().equals("z") && b
                    .getLocation()
                    .getBlockZ() == loc.getBlockZ())) {
                if ((!epb.isSurfaceModeEnabled()) || Surface.isOnSurface(b.getLocation(), p.getLocation())) {
                    if ((!epb.isMaskEnabled()) || (b.getType().equals(epb
                            .getMask()
                            .getMaterial()) && (XMaterial.supports(13) || b.getData() == epb.getMask().getData()))) {
                        Random r = new Random();
                        int random = r.nextInt(epbBlocks.size());
                        placedBlocks.add(
                                new BlockPlace(
                                        b.getLocation(),
                                        new BlockType(
                                                epb.getBlocks().get(random).getMaterial(),
                                                epb.getBlocks().get(random).getData()
                                        )
                                ));
                    }
                }
            }
        }
        BlockPlacer bp = new BlockPlacer();
        bp.placeBlocks(placedBlocks, p);
    }

}
