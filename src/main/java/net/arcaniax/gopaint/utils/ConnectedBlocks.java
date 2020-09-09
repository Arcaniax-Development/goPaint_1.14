package net.arcaniax.gopaint.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ConnectedBlocks {

	@SuppressWarnings("deprecation")
	public static List<Block> getConnectedBlocks(Location loc, List<Block> blocks){
		Block startBlock = loc.getBlock();
		Material mat = startBlock.getType();
		short data = startBlock.getData();
		List<Block> connectCheckBlocks = new ArrayList<Block>();
		List<Block> hasBeenChecked = new ArrayList<Block>();
		List<Block> connected = new ArrayList<Block>();
		int x = 0;
		connectCheckBlocks.add(startBlock);
		connected.add(startBlock);
		while(!connectCheckBlocks.isEmpty()&&x<5000){
			Block b = connectCheckBlocks.get(0);
			for (Block block : getBlocksAround(b)){
				if((!connected.contains(block))&&(!hasBeenChecked.contains(block))&&blocks.contains(block)&&block.getType()==mat&&block.getData()==data){
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
	
	private static List<Block> getBlocksAround(Block b){
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
