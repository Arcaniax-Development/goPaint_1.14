package gc.arcaniax.gopaint.objects.other;

import org.bukkit.Location;

public class BlockPlace {
	Location l;
	BlockType bt;
	
	public BlockPlace(Location loc, BlockType block){
		l = loc;
		bt = block;
	}
	
	public BlockType getBlockType(){
		return bt;
	}
	
	public Location getLocation(){
		return l;
	}
}
