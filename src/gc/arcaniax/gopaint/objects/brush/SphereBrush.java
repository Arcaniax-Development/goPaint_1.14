package gc.arcaniax.gopaint.objects.brush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gc.arcaniax.gopaint.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import gc.arcaniax.gopaint.Main;
import gc.arcaniax.gopaint.objects.other.BlockPlace;
import gc.arcaniax.gopaint.objects.other.BlockPlacer;
import gc.arcaniax.gopaint.objects.other.BlockType;
import gc.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import gc.arcaniax.gopaint.objects.player.PlayerBrush;


public class SphereBrush extends Brush{

	@SuppressWarnings({"deprecation"})
	@Override
	public void paint(Location loc, Player p) {
		PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
		int size = pb.getBrushSize();
		List<BlockType> pbBlocks = pb.getBlocks();
		if (pbBlocks.isEmpty()){return;}
		List<Block> blocks = gc.arcaniax.gopaint.utils.Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!pb.isSurfaceModeEnabled())||gc.arcaniax.gopaint.utils.Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!pb.isMaskEnabled())||(b.getType().equals(pb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==pb.getMask().getData()))){
							Random r = new Random();
							int random = r.nextInt(pbBlocks.size());
							placedBlocks.add(
									new BlockPlace(b.getLocation(), 
									new BlockType(pbBlocks.get(random).getMaterial(), pbBlocks.get(random).getData())));
						}
					}
			
		}
		BlockPlacer bp = new BlockPlacer();
		bp.placeBlocks(placedBlocks, p);
	}
	
	@Override
	public String getName() {
		return "Sphere Brush";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
		int size = epb.getBrushSize();
		List<BlockType> epbBlocks = epb.getBlocks();
		if (epbBlocks.isEmpty()){return;}
		List<Block> blocks = gc.arcaniax.gopaint.utils.Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!epb.isSurfaceModeEnabled())||gc.arcaniax.gopaint.utils.Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!epb.isMaskEnabled())||(b.getType().equals(epb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==epb.getMask().getData()))){
							Random r = new Random();
							int random = r.nextInt(epbBlocks.size());
							placedBlocks.add(
									new BlockPlace(b.getLocation(), 
									new BlockType(epb.getBlocks().get(random).getMaterial(), epb.getBlocks().get(random).getData())));
						}
			}
		}
		BlockPlacer bp = new BlockPlacer();
		bp.placeBlocks(placedBlocks, p);
	}
}
