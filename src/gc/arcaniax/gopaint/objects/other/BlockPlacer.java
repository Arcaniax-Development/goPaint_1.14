package gc.arcaniax.gopaint.objects.other;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.math.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class BlockPlacer {



	public BlockPlacer(){
	}

	public boolean isGmask(Player player, BlockVector3 v){
		LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(new BukkitPlayer(player));
		if (localSession.getMask()==null||localSession.getMask().test(v)){
			return true;
		}
		return false;
	}

	public void placeBlocks(Collection<BlockPlace> blocks, final Player p){
		LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(new BukkitPlayer(p));
		try (EditSession editsession = localSession.createEditSession(new BukkitPlayer(p))) {
			try {
				editsession.setFastMode(false);
				for (BlockPlace bp : blocks) {
					Location l = bp.getLocation();
					Vector3 v = Vector3.at(l.getBlockX(), l.getBlockY(), l.getBlockZ());
					if (isGmask(p, v.toBlockPoint())) {
						try {
							editsession.setBlock(Vector3.at(l.getBlockX(), l.getBlockY(), l.getBlockZ()).toBlockPoint(), BukkitAdapter.asBlockType(bp.bt.getMaterial()).getDefaultState());
						} catch (Exception e) {
						}
					}
				}
			} finally {
				localSession.remember(editsession);
			}
		}
	}
}
