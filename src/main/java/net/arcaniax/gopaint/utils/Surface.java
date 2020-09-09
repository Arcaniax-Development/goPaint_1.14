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

package net.arcaniax.gopaint.utils;

import org.bukkit.Location;
import org.bukkit.Material;

public class Surface {

    public static boolean isOnSurface(Location blockLoc, Location playerLoc) {
        playerLoc.add(0, 1.5, 0);
        double distanceX = playerLoc.getX() - blockLoc.getX();
        double distanceY = playerLoc.getY() - blockLoc.getY();
        double distanceZ = playerLoc.getZ() - blockLoc.getZ();
        if (distanceX > 1) {
            blockLoc.add(1, 0, 0);
        } else if (distanceX > 0) {
            blockLoc.add(0.5, 0, 0);
        }
        if (distanceY > 1) {
            blockLoc.add(0, 1, 0);
        } else if (distanceY > 0) {
            blockLoc.add(0, 0.5, 0);
        }
        if (distanceZ > 1) {
            blockLoc.add(0, 0, 1);
        } else if (distanceZ > 0) {
            blockLoc.add(0, 0, 0.5);
        }

        double distance = blockLoc.distance(playerLoc);
        for (int x = 1; x < distance; x++) {
            double moveX = distanceX * (x / distance);
            double moveY = distanceY * (x / distance);
            double moveZ = distanceZ * (x / distance);
            Location checkLoc = blockLoc.clone().add(moveX, moveY, moveZ);
            if (checkLoc.getBlock().getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }
}
