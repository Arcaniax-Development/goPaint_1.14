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
package net.onelitefeather.bettergopaint.utils;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Sphere {

    public static Stream<Block> getBlocksInRadius(Location middlePoint, int radius, @Nullable Axis axis, boolean air) {
        List<Block> blocks = new ArrayList<>();
        Location loc1 = middlePoint.clone().add(-radius / 2d, -radius / 2d, -radius / 2d).getBlock().getLocation();
        Location loc2 = middlePoint.clone().add(radius / 2d, radius / 2d, radius / 2d).getBlock().getLocation();

        switch (axis) {
            case Y:
                for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
                    for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                        Location location = new Location(loc1.getWorld(), x, middlePoint.getBlockY(), z);
                        if (passesDefaultChecks(location, middlePoint, radius)) {
                            blocks.add(location.getBlock());
                        }
                    }
                }
                break;
            case X:
                for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
                    for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                        Location location = new Location(loc1.getWorld(), middlePoint.getBlockX(), y, z);
                        if (passesDefaultChecks(location, middlePoint, radius)) {
                            blocks.add(location.getBlock());
                        }
                    }
                }
                break;
            case Z:
                for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
                    for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
                        Location location = new Location(loc1.getWorld(), x, y, middlePoint.getBlockZ());
                        if (passesDefaultChecks(location, middlePoint, radius)) {
                            blocks.add(location.getBlock());
                        }
                    }
                }
                break;
            case null:
                for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
                    for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
                        for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                            Location location = new Location(loc1.getWorld(), x, y, z);
                            if (passesDefaultChecks(location, middlePoint, radius)) {
                                blocks.add(location.getBlock());
                            }
                        }
                    }
                }
                break;
        }
        if (air) return blocks.stream();
        return blocks.stream().filter(block -> !block.isEmpty());
    }

    private static boolean passesDefaultChecks(Location location, Location middlePoint, int radius) {
        return location.getChunk().isLoaded() && location.distance(middlePoint) < radius / 2d;
    }

}
