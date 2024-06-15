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

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class Height {

    /**
     * Gets the height of the nearest non-empty block at a given location.
     *
     * @param location the location to check
     * @return the height of the nearest non-empty block at the location
     */
    public static int getNearestNonEmptyBlock(@NotNull Location location) {
        if (location.getBlock().getType().isEmpty()) {
            for (int y = location.getBlockY(); y >= location.getWorld().getMinHeight(); y--) {
                Block block = location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ());
                if (!block.isEmpty()) {
                    return y + 1;
                }
            }
            return location.getWorld().getMinHeight();
        } else {
            for (int y = location.getBlockY(); y <= location.getWorld().getMaxHeight(); y++) {
                Block block = location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ());
                if (block.isEmpty()) {
                    return y;
                }
            }
            return location.getWorld().getMaxHeight();
        }
    }

    /**
     * Calculates the average height difference of the surrounding blocks from a given location, within a specified distance.
     *
     * @param location the location to calculate the average height difference from
     * @param height   the reference height of the nearest non-empty block
     * @param distance the distance at which to calculate the average height difference
     * @return the average height difference of the surrounding blocks within the specified distance
     */
    public static double getAverageHeightDiffFracture(@NotNull Location location, int height, int distance) {
        double totalHeight = 0;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, -distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(-distance, 0, distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(-distance, 0, -distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(0, 0, -distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(0, 0, distance))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(-distance, 0, 0))) - height;
        totalHeight += Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, 0))) - height;
        return (totalHeight / 8d) / distance;
    }

    /**
     * Calculates the average height difference angle of the surrounding blocks from a given location within a specified distance.
     *
     * @param location the location to calculate the average height difference angle from
     * @param distance the distance at which to calculate the average height difference angle
     * @return the average height difference angle of the surrounding blocks within the specified distance
     */
    public static double getAverageHeightDiffAngle(@NotNull Location location, int distance) {
        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;
        double diff = Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, -distance))
                - getNearestNonEmptyBlock(location.clone().add(-distance, 0, distance)));
        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, distance))
                - getNearestNonEmptyBlock(location.clone().add(-distance, 0, -distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getNearestNonEmptyBlock(location.clone().add(distance, 0, 0))
                - getNearestNonEmptyBlock(location.clone().add(-distance, 0, 0)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getNearestNonEmptyBlock(location.clone().add(0, 0, -distance))
                - getNearestNonEmptyBlock(location.clone().add(0, 0, distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (distance * 2d);
    }

}
