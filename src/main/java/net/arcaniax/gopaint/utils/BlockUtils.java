package net.arcaniax.gopaint.utils;

import org.bukkit.Location;

public class BlockUtils {
    public static boolean isLoaded(Location l){
        int x = l.getBlockX() >> 4;
        int z = l.getBlockZ() >> 4;
        if(l.getWorld().isChunkLoaded(x, z)){
            return true;
        }
        return false;
    }
}
