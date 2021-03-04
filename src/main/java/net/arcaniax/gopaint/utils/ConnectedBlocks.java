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
package net.arcaniax.gopaint.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ConnectedBlocks {

    @SuppressWarnings("deprecation")
    public static List<Block> getConnectedBlocks(Location loc, List<Block> blocks) {
        Block startBlock = loc.getBlock();
        Material mat = startBlock.getType();
        short data = startBlock.getData();
        List<Block> connectCheckBlocks = new ArrayList<Block>();
        List<Block> hasBeenChecked = new ArrayList<Block>();
        List<Block> connected = new ArrayList<Block>();
        int x = 0;
        connectCheckBlocks.add(startBlock);
        connected.add(startBlock);
        while (!connectCheckBlocks.isEmpty() && x < 5000) {
            Block b = connectCheckBlocks.get(0);
            for (Block block : getBlocksAround(b)) {
                if ((!connected.contains(block)) && (!hasBeenChecked.contains(block)) && blocks.contains(block) && block.getType() == mat && block.getData() == data) {
                    connectCheckBlocks.add(block);
                    connected.add(block);
                    x++;
                }
            }
            hasBeenChecked.add(b);
            connectCheckBlocks.remove(b);
        }
        return connected;
    }

    private static List<Block> getBlocksAround(Block b) {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(b.getLocation().clone().add(-1, 0, 0).getBlock());
        blocks.add(b.getLocation().clone().add(+1, 0, 0).getBlock());
        blocks.add(b.getLocation().clone().add(0, -1, 0).getBlock());
        blocks.add(b.getLocation().clone().add(0, +1, 0).getBlock());
        blocks.add(b.getLocation().clone().add(0, 0, -1).getBlock());
        blocks.add(b.getLocation().clone().add(0, 0, +1).getBlock());
        return blocks;
    }

}
