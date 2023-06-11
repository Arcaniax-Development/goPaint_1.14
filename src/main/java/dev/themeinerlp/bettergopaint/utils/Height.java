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
package dev.themeinerlp.bettergopaint.utils;

import dev.themeinerlp.bettergopaint.objects.other.BlockPlacer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Height {

    public static int getHeight(Player p, Location loc) {
        BlockPlacer bp = new BlockPlacer();
        if (loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
            while (loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
                loc.add(0, -1, 0);
                if (loc.getBlockY() < 0) {
                    return 1;
                }
            }
            return loc.getBlockY() + 1;
        } else {
            while (!(loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial()))) {
                loc.add(0, 1, 0);
                if (loc.getBlockY() > 254) {
                    return 254;
                }
            }
            return loc.getBlockY();
        }
    }

    public static double getAverageHeightDiffFracture(Location l, int height, int dis, Player p) {
        double totalHeight = 0;
        totalHeight += Math.abs(getHeight(p, l.clone().add(dis, 0, -dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(dis, 0, dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(-dis, 0, dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(-dis, 0, -dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(0, 0, -dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(0, 0, dis))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(-dis, 0, 0))) - height;
        totalHeight += Math.abs(getHeight(p, l.clone().add(dis, 0, 0))) - height;
        return (totalHeight / (double) 8) / (double) dis;
    }

    public static double getAverageHeightDiffAngle(Location l, int dis, Player p) {
        double maxHeightDiff = 0;
        double maxHeightDiff2 = 0;
        double diff = Math
                .abs(getHeight(p, l.clone().add(dis, 0, -dis)) - getHeight(p, l.clone().add(-dis, 0, dis)));
        if (diff >= maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(p, l.clone().add(dis, 0, dis)) - getHeight(p, l.clone().add(-dis, 0, -dis)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(p, l.clone().add(dis, 0, 0)) - getHeight(p, l.clone().add(-dis, 0, 0)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }
        diff = Math
                .abs(getHeight(p, l.clone().add(0, 0, -dis)) - getHeight(p, l.clone().add(0, 0, dis)));
        if (diff > maxHeightDiff) {
            maxHeightDiff = diff;
            maxHeightDiff2 = maxHeightDiff;
        }

        double height = (maxHeightDiff2 + maxHeightDiff) / 2.0;
        return height / (double) (dis * 2);
    }

    public static boolean isOnTop(Player p, Location loc, int thickness) {
        int height = getHeight(p, loc.clone());
        return height - loc.getBlockY() <= thickness;
    }

}
