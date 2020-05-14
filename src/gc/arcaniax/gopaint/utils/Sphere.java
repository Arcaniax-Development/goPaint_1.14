package gc.arcaniax.gopaint.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Sphere {
	public static List<Block> getBlocksInRadius(Location middlePoint, double d){
		List<Block> blocks = new ArrayList<Block>();
		for (Block b : getBlocksInRadiusWithAir(middlePoint, d)){
			if (BlockUtils.isLoaded(b.getLocation())&&(!b.getType().equals(XMaterial.AIR.parseMaterial()))){
				blocks.add(b);
			}
		}
		return blocks;
	}

	public static List<Block> getBlocksInRadiusWithAir(Location middlePoint, double d){
		List<Block> blocks = new ArrayList<Block>();
		Location loc1 = middlePoint.clone().add(-d/2, -d/2, -d/2).getBlock().getLocation();
		Location loc2 = middlePoint.clone().add(+d/2, +d/2, +d/2).getBlock().getLocation();
		for(double x = loc1.getX(); x <= loc2.getX(); x++) {
			for(double y = loc1.getY(); y <= loc2.getY(); y++) {
				for(double z = loc1.getZ(); z <= loc2.getZ(); z++) {
					Location loc = new Location(loc1.getWorld(), x, y, z);
					if (loc.distance(middlePoint)<(d/2)){
						blocks.add(loc.getBlock());
					}
				}
			}
		}
		return blocks;
	}
}
