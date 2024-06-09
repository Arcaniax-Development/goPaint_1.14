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
import org.bukkit.block.BlockFace;

public class Surface {

    public static boolean isOnSurface(Block block, Location playerLoc) {

        return block.getRelative(BlockFace.UP).isEmpty();

        // keep the old logic, implement tri state surface mode
        /*
        Location location = block.getLocation();

        playerLoc.add(0, 1.5, 0);
        double distanceX = playerLoc.getX() - block.getX();
        double distanceY = playerLoc.getY() - block.getY();
        double distanceZ = playerLoc.getZ() - block.getZ();
        if (distanceX > 1) {
            location.add(1, 0, 0);
        } else if (distanceX > 0) {
            location.add(0.5, 0, 0);
        }
        if (distanceY > 1) {
            location.add(0, 1, 0);
        } else if (distanceY > 0) {
            location.add(0, 0.5, 0);
        }
        if (distanceZ > 1) {
            location.add(0, 0, 1);
        } else if (distanceZ > 0) {
            location.add(0, 0, 0.5);
        }

        double distance = location.distance(playerLoc);
        for (int x = 1; x < distance; x++) {
            double moveX = distanceX * (x / distance);
            double moveY = distanceY * (x / distance);
            double moveZ = distanceZ * (x / distance);
            Location checkLoc = location.add(moveX, moveY, moveZ);
            if (!checkLoc.getBlock().isEmpty()) {
                return false;
            }
        }
        return true;
         */
    }

}
