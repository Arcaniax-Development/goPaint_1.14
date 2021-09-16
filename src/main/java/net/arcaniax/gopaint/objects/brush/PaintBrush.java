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
 *                     Copyright (C) 2021 Arcaniax
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
import net.arcaniax.gopaint.utils.BlockUtils;
import net.arcaniax.gopaint.utils.Sphere;
import net.arcaniax.gopaint.utils.Surface;
import net.arcaniax.gopaint.utils.XMaterial;
import net.arcaniax.gopaint.utils.curve.BezierSpline;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PaintBrush extends Brush {

    private static final HashMap<String, List<Location>> selectedPoints = new HashMap<>();

    @Override
    public void paint(Location loc, Player p) {
        String prefix = GoPaintPlugin.getSettings().getPrefix();
        if (!selectedPoints.containsKey(p.getName())) {
            List<Location> locs = new ArrayList<>();
            locs.add(loc);
            selectedPoints.put(p.getName(), locs);
            p.sendMessage(prefix + " Paint brush point #1 set.");
        } else {
            if (!p.isSneaking()) {
                List<Location> locs = selectedPoints.get(p.getName());
                locs.add(loc);
                selectedPoints.put(p.getName(), locs);
                p.sendMessage(prefix + " Paint brush point #" + locs.size() + " set.");
                return;
            }
            List<Location> locs = selectedPoints.get(p.getName());
            locs.add(loc);
            selectedPoints.remove(p.getName());
            PlayerBrush pb = GoPaintPlugin.getBrushManager().getPlayerBrush(p);
            int size = pb.getBrushSize();
            int falloff = pb.getFalloffStrength();
            List<BlockType> pbBlocks = pb.getBlocks();
            if (pbBlocks.isEmpty()) {
                return;
            }
            List<Block> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), size);
            List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
            for (Block b : blocks) {
                Random r = new Random();
                int random = r.nextInt(pbBlocks.size());
                double rate = (b
                        .getLocation()
                        .distance(locs.get(0)) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) / (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));
                if (!(r.nextDouble() <= rate)) {
                    LinkedList<Location> newCurve = new LinkedList<>();
                    int amount = 0;
                    for (Location l : locs) {
                        if (amount == 0) {
                            newCurve.add(b.getLocation());
                        } else {
                            newCurve.add(b.getLocation().clone().add(
                                    l.getX() - locs.get(0).getX(),
                                    l.getY() - locs.get(0).getY(),
                                    l.getZ() - locs.get(0).getZ()
                            ));
                        }
                        amount++;
                    }
                    BezierSpline bs = new BezierSpline(newCurve);
                    double length = bs.getCurveLength();
                    int maxCount = (int) (length * 2.5) + 1;
                    for (int y = 0; y <= maxCount; y++) {
                        Location l = bs.getPoint(((double) y / (double) maxCount) * (locs.size() - 1)).getBlock().getLocation();
                        Location location = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
                        if (BlockUtils.isLoaded(location) && (!location
                                .getBlock()
                                .getType()
                                .equals(XMaterial.AIR.parseMaterial()))) {
                            if ((!pb.isSurfaceModeEnabled()) || Surface.isOnSurface(location, p.getLocation())) {
                                if ((!pb.isMaskEnabled()) || (b.getType().equals(pb
                                        .getMask()
                                        .getMaterial()) && (XMaterial.isNewVersion() || b.getData() == pb.getMask().getData()))) {
                                    placedBlocks.add(new BlockPlace(
                                            location,
                                            new BlockType(pbBlocks.get(random).getMaterial(), pbBlocks.get(random).getData())
                                    ));
                                }
                            }
                        }
                    }
                }


            }
            BlockPlacer bp = new BlockPlacer();
            bp.placeBlocks(placedBlocks, p);
        }
    }

    @Override
    public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
        String prefix = GoPaintPlugin.getSettings().getPrefix();
        if (!selectedPoints.containsKey(p.getName())) {
            List<Location> locs = new ArrayList<>();
            locs.add(loc);
            selectedPoints.put(p.getName(), locs);
            p.sendMessage(prefix + " Paint brush point #1 set.");
        } else {
            if (!p.isSneaking()) {
                List<Location> locs = selectedPoints.get(p.getName());
                locs.add(loc);
                selectedPoints.put(p.getName(), locs);
                p.sendMessage(prefix + " Paint brush point #" + locs.size() + " set.");
                return;
            }
            List<Location> locs = selectedPoints.get(p.getName());
            locs.add(loc);
            selectedPoints.remove(p.getName());
            int size = epb.getBrushSize();
            int falloff = epb.getFalloffStrength();
            List<BlockType> pbBlocks = epb.getBlocks();
            if (pbBlocks.isEmpty()) {
                return;
            }
            List<Block> blocks = Sphere.getBlocksInRadiusWithAir(locs.get(0), size);
            List<BlockPlace> placedBlocks = new ArrayList<>();
            for (Block b : blocks) {
                Random r = new Random();
                int random = r.nextInt(pbBlocks.size());
                double rate = (b
                        .getLocation()
                        .distance(locs.get(0)) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0)) / (((double) size / 2.0) - ((double) size / 2.0) * ((100.0 - (double) falloff) / 100.0));
                if (!(r.nextDouble() <= rate)) {
                    LinkedList<Location> newCurve = new LinkedList<>();
                    int amount = 0;
                    for (Location l : locs) {
                        if (amount == 0) {
                            newCurve.add(b.getLocation());
                        } else {
                            newCurve.add(b.getLocation().clone().add(
                                    l.getX() - locs.get(0).getX(),
                                    l.getY() - locs.get(0).getY(),
                                    l.getZ() - locs.get(0).getZ()
                            ));
                        }
                        amount++;
                    }
                    BezierSpline bs = new BezierSpline(newCurve);
                    double length = bs.getCurveLength();
                    int maxCount = (int) (length * 2.5) + 1;
                    for (int y = 0; y <= maxCount; y++) {
                        Location l = bs.getPoint(((double) y / (double) maxCount) * (locs.size() - 1)).getBlock().getLocation();
                        Location location = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
                        if (BlockUtils.isLoaded(location) && (!location
                                .getBlock()
                                .getType()
                                .equals(XMaterial.AIR.parseMaterial()))) {
                            if ((!epb.isSurfaceModeEnabled()) || Surface.isOnSurface(location, p.getLocation())) {
                                if ((!epb.isMaskEnabled()) || (b.getType().equals(epb
                                        .getMask()
                                        .getMaterial()) && (XMaterial.isNewVersion() || b.getData() == epb
                                        .getMask()
                                        .getData()))) {
                                    placedBlocks.add(new BlockPlace(
                                            location,
                                            new BlockType(pbBlocks.get(random).getMaterial(), pbBlocks.get(random).getData())
                                    ));
                                }
                            }
                        }
                    }
                }


            }
            BlockPlacer bp = new BlockPlacer();
            bp.placeBlocks(placedBlocks, p);
        }
    }

    @Override
    public String getName() {
        return "Paint Brush";
    }

}
