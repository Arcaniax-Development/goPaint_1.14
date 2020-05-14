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

public class SplatterBrush extends Brush{

	@SuppressWarnings("deprecation")
	@Override
	public void paint(Location loc, Player p) {
		PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
		int size = pb.getBrushSize();
		int falloff = pb.getFalloffStrength();
		List<BlockType> pbBlocks = pb.getBlocks();
		if (pbBlocks.isEmpty()){return;}
		List<Block> blocks = gc.arcaniax.gopaint.utils.Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!pb.isSurfaceModeEnabled())||gc.arcaniax.gopaint.utils.Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!pb.isMaskEnabled())||(b.getType().equals(pb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==pb.getMask().getData()))){
                            Random r = new Random();
                            double rate = (b.getLocation().distance(loc) - ((double)size/2.0)*((100.0-(double)falloff)/100.0))/(((double)size/2.0) - ((double)size/2.0)*((100.0-(double)falloff)/100.0));
                            if (!(r.nextDouble()<=rate)){
                                int random = r.nextInt(pbBlocks.size());
                                placedBlocks.add(
                                        new BlockPlace(b.getLocation(), new BlockType(pb.getBlocks().get(random).getMaterial(), pb.getBlocks().get(random).getData())));
                            }
						}
					}
				}
		BlockPlacer bp = new BlockPlacer();
		bp.placeBlocks(placedBlocks, p);
	}
	@Override
	public String getName() {
		return "Splatter Brush";
	}
	@SuppressWarnings("deprecation")
	@Override
	public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
		int size = epb.getBrushSize();
        int falloff = epb.getFalloffStrength();
		List<BlockType> epbBlocks = epb.getBlocks();
		if (epbBlocks.isEmpty()){return;}
		List<Block> blocks = gc.arcaniax.gopaint.utils.Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!epb.isSurfaceModeEnabled())||gc.arcaniax.gopaint.utils.Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!epb.isMaskEnabled())||(b.getType().equals(epb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==epb.getMask().getData()))){
							Random r = new Random();
							double rate = (b.getLocation().distance(loc) - ((double)size/2.0)*((100.0-(double)falloff)/100.0))/(((double)size/2.0) - ((double)size/2.0)*((100.0-(double)falloff)/100.0));
							if (!(r.nextDouble()<=rate)){
							    int random = r.nextInt(epbBlocks.size());
							    placedBlocks.add(
							            new BlockPlace(b.getLocation(), new BlockType(epb.getBlocks().get(random).getMaterial(), epb.getBlocks().get(random).getData())));
							}
						}
				}
			}
		BlockPlacer bp = new BlockPlacer();
		bp.placeBlocks(placedBlocks, p);
			
		}
}
