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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public final class ConnectedBlocks {

    private static final BlockFace[] faces = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.UP,
            BlockFace.DOWN,
    };

    private ConnectedBlocks() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Returns a stream of connected blocks starting from a given location, based on a list of blocks.
     * Only blocks of the same type as the start block are considered.
     *
     * @param loc    the starting location
     * @param blocks the list of blocks to check for connectivity
     * @return a stream of connected blocks
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Stream<Block> getConnectedBlocks(@NotNull Location loc, @NotNull List<Block> blocks) {
        if (blocks.isEmpty()) return Stream.empty();
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

        return connected.stream();
    }
}
