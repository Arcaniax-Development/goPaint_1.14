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

public class Height {

    public static int getHeight(Location location) {
        return location.getWorld().getHighestBlockYAt(location);
    }

    public static double getAverageHeightDiffFracture(Location location, int height, int distance) {
        double totalHeight = 0;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, -distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(0, 0, distance))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(-distance, 0, 0))) - height;
        totalHeight += Math.abs(getHeight(location.clone().add(distance, 0, 0))) - height;
        return (totalHeight / 8d) / distance;
    }

    public static double getAverageHeightDiffAngle(Location location, int distance) {
        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;
        double diff = Math.abs(getHeight(location.clone().add(distance, 0, -distance)) - getHeight(location.clone()
                .add(-distance, 0, distance)));
        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getHeight(location.clone().add(distance, 0, distance)) - getHeight(location.clone()
                .add(-distance, 0, -distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getHeight(location.clone().add(distance, 0, 0)) - getHeight(location.clone()
                .add(-distance, 0, 0)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math.abs(getHeight(location.clone().add(0, 0, -distance)) - getHeight(location.clone()
                .add(0, 0, distance)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (distance * 2d);
    }

}
