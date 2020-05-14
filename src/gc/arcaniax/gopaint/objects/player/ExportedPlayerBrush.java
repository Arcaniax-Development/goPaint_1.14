package gc.arcaniax.gopaint.objects.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import gc.arcaniax.gopaint.Main;
import gc.arcaniax.gopaint.objects.brush.Brush;
import gc.arcaniax.gopaint.objects.other.BlockType;

public class ExportedPlayerBrush {
	
	Boolean surfaceEnabled=false;
	Boolean maskEnabled=false;
	Brush b;
	int size = 10;
	int chance = 50;
	int thickness = 1;
	int angleDistance = 2;
	int fractureDistance = 2;
	int falloffStrength = 50;
	int mixingStrength = 50;
	Double minAngleHeightDifference = 2.5;
	String axis = "y";
	List<BlockType> blocks;
	BlockType mask;

	public ExportedPlayerBrush(String name ,List<String> lore){
		b = Main.getBrushManager().getBrush(name.replaceAll(" §b♦ ", ""));
		blocks = new ArrayList<>();
		for (String s : lore){
			if (s.startsWith("§8Size: ")){
				size = Integer.parseInt(s.replaceAll("§8Size: ", ""));
			}
			else if (s.startsWith("§8Chance: ")){
				chance = Integer.parseInt(s.replaceAll("§8Chance: ", "").replaceAll("%", ""));
			}
			if (s.startsWith("§8Thickness: ")){
				thickness = Integer.parseInt(s.replaceAll("§8Thickness: ", ""));
			}
			if (s.startsWith("§8Axis: ")){
				axis = s.replaceAll("§8Axis: ", "");
			}
			if (s.startsWith("§8FractureDistance: ")){
				fractureDistance = Integer.parseInt(s.replaceAll("§8FractureDistance: ", ""));
			}
			if (s.startsWith("§8AngleDistance: ")){
				angleDistance = Integer.parseInt(s.replaceAll("§8AngleDistance: ", ""));
			}
			if (s.startsWith("§8AngleHeightDifference: ")){
				minAngleHeightDifference = Double.parseDouble(s.replaceAll("§8AngleHeightDifference: ", ""));
			}
			if (s.startsWith("§8Mixing: ")){
				mixingStrength = Integer.parseInt(s.replaceAll("§8Mixing: ", ""));
			}
			if (s.startsWith("§8Falloff: ")){
				falloffStrength = Integer.parseInt(s.replaceAll("§8Falloff: ", ""));
			}
			if (s.startsWith("§8Blocks: ")){
				s = s.replaceAll("§8Blocks: ", "");
				if (!s.equals("none")){
					for (String s2 : s.split(" ")){
						String[] type = s2.split("\\:");
						Material mat = Material.getMaterial(type[0].toUpperCase());
						int data = Integer.parseInt(type[1]);
						blocks.add(new BlockType(mat, (short)data));
					}
				}
			}
			if (s.startsWith("§8Mask: ")){
				s = s.replaceAll("§8Mask: ", "");
				String[] type = s.split("\\:");
				Material mat = Material.getMaterial(type[0].toUpperCase());
				int data = Integer.parseInt(type[1]);
				mask = new BlockType(mat, (short)data);
				maskEnabled = true;
			}
			if (s.startsWith("§8Surface Mode")){
				surfaceEnabled = true;
			}
		}
	}
	
	public Brush getBrush(){
		return b;
	}
	
	public BlockType getMask(){
		return mask;
	}
	
	public List<BlockType> getBlocks(){
		return blocks;
	}
	
	public int getBrushSize(){
		return size;
	}

	public int getChance() {
		return chance;
	}

	public Double getMinHeightDifference(){
		return this.minAngleHeightDifference;
	}
	
	public int getAngleDistance(){
		return this.angleDistance;
	}
	
	public int getFractureDistance(){
		return this.fractureDistance;
	}
	
	public boolean isMaskEnabled() {
		return maskEnabled;
	}
	
	public boolean isSurfaceModeEnabled(){
		return surfaceEnabled;
	}

	public int getThickness() {
		return thickness;
	}


	public int getFalloffStrength(){return falloffStrength;}

	public int getMixingStrength(){return mixingStrength;}

	public String getAxis() {
		return axis;
	}	
}
