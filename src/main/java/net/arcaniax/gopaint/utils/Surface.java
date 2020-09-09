package net.arcaniax.gopaint.utils;

import org.bukkit.Location;
import org.bukkit.Material;

public class Surface {
	public static boolean isOnSurface(Location blockLoc, Location playerLoc){
		playerLoc.add(0, 1.5, 0);
		double distanceX = playerLoc.getX() - blockLoc.getX();
		double distanceY = playerLoc.getY() - blockLoc.getY();
		double distanceZ = playerLoc.getZ() - blockLoc.getZ();
		if (distanceX>1){
			blockLoc.add(1, 0, 0);
		}
		else if (distanceX>0){
			blockLoc.add(0.5, 0, 0);
		}
		if (distanceY>1){
			blockLoc.add(0, 1, 0);
		}
		else if (distanceY>0){
			blockLoc.add(0, 0.5, 0);
		}
		if (distanceZ>1){
			blockLoc.add(0, 0, 1);
		}
		else if (distanceZ>0){
			blockLoc.add(0, 0, 0.5);
		}
		
		double distance = blockLoc.distance(playerLoc);
		for (int x = 1; x<distance; x++){
			double moveX = distanceX * (x/distance);
			double moveY = distanceY * (x/distance);
			double moveZ = distanceZ * (x/distance);
			Location checkLoc = blockLoc.clone().add(moveX, moveY, moveZ);
			if (checkLoc.getBlock().getType()!=Material.AIR){
				return false;
			}
		}	
		return true;
	}
}
