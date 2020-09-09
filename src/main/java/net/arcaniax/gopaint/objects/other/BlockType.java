package net.arcaniax.gopaint.objects.other;

import org.bukkit.Material;

public class BlockType {

	private Material material;
	private short data;
	
	public BlockType(Material mat, short metadata){
		material = mat;
		data = metadata;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getData(){
		return data;
	}
}
