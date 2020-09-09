package net.arcaniax.gopaint.objects.brush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.arcaniax.gopaint.utils.XMaterial;
import net.arcaniax.gopaint.Main;
import net.arcaniax.gopaint.objects.other.BlockPlace;
import net.arcaniax.gopaint.objects.other.BlockPlacer;
import net.arcaniax.gopaint.objects.other.BlockType;
import net.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.Height;
import net.arcaniax.gopaint.utils.Sphere;
import net.arcaniax.gopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AngleBrush extends Brush{

	@SuppressWarnings({ "deprecation" })
	@Override
	public void paint(Location loc, Player p) {
		PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
		int size = pb.getBrushSize();
		List<BlockType> pbBlocks = pb.getBlocks();
		if (pbBlocks.isEmpty()){return;}
		List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!pb.isSurfaceModeEnabled())|| Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!pb.isMaskEnabled())||(b.getType().equals(pb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==pb.getMask().getData()))){
							if (!(Height.getAverageHeightDiffAngle(b.getLocation(), 1, p)>=0.1&&
                  Height.getAverageHeightDiffAngle(b.getLocation(), pb.getAngleDistance(), p)>=Math.tan(Math.toRadians(pb.getMinHeightDifference())))){
								Random r = new Random();
								int random = r.nextInt(pbBlocks.size());
								placedBlocks.add(
										new BlockPlace(b.getLocation(), new BlockType(pbBlocks.get(random).getMaterial(), pbBlocks.get(random).getData())));
							}
						}
					}
		}
		BlockPlacer bp = new BlockPlacer();
		bp.placeBlocks(placedBlocks, p);
	}

	@Override
	public String getName() {
		return "Angle Brush";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void paint(Location loc, Player p, ExportedPlayerBrush epb) {
		int size = epb.getBrushSize();
		List<BlockType> epbBlocks = epb.getBlocks();
		if (epbBlocks.isEmpty()){return;}
		List<Block> blocks = Sphere.getBlocksInRadius(loc, size);
		List<BlockPlace> placedBlocks = new ArrayList<BlockPlace>();
		for (Block b : blocks){
					if ((!epb.isSurfaceModeEnabled())|| Surface.isOnSurface(b.getLocation(), p.getLocation())){
						if ((!epb.isMaskEnabled())||(b.getType().equals(epb.getMask().getMaterial())&&(XMaterial.isNewVersion()||b.getData()==epb.getMask().getData()))){
							if (!(Height.getAverageHeightDiffAngle(b.getLocation(), 1, p)>=0.1&&
                  Height.getAverageHeightDiffAngle(b.getLocation(), epb.getAngleDistance(), p)>=Math.tan(Math.toRadians(epb.getMinHeightDifference())))){
								Random r = new Random();
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
