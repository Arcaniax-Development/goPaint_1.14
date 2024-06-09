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

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ConnectedBlocks {

    private static final BlockFace[] faces = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.UP,
            BlockFace.DOWN,
    };

    public static Set<Block> getConnectedBlocks(Location loc, List<Block> blocks) {
        Block startBlock = loc.getBlock();
        Set<Block> connected = new HashSet<>();
        Queue<Block> toCheck = new LinkedList<>();

        toCheck.add(startBlock);
        connected.add(startBlock);

        while (!toCheck.isEmpty() && connected.size() < blocks.size()) {
            Block current = toCheck.poll();
            List<Block> neighbors = Arrays.stream(faces)
                    .map(current::getRelative)
                    .filter(relative -> relative.getType().equals(startBlock.getType())
                            && !connected.contains(relative)
                            && blocks.contains(relative))
                    .toList();

            connected.addAll(neighbors);
            toCheck.addAll(neighbors);
        }

        return connected;
    }

}
