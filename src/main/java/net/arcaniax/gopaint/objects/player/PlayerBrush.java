package net.arcaniax.gopaint.objects.player;

import java.util.ArrayList;
import java.util.List;

import net.arcaniax.gopaint.objects.brush.AngleBrush;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.DiscBrush;
import net.arcaniax.gopaint.objects.brush.FractureBrush;
import net.arcaniax.gopaint.objects.brush.GradientBrush;
import net.arcaniax.gopaint.objects.brush.OverlayBrush;
import net.arcaniax.gopaint.objects.brush.SplatterBrush;
import net.arcaniax.gopaint.objects.brush.SprayBrush;
import net.arcaniax.gopaint.utils.GUI;
import net.arcaniax.gopaint.utils.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.arcaniax.gopaint.Main;
import net.arcaniax.gopaint.objects.brush.*;
import net.arcaniax.gopaint.objects.other.BlockType;

public class PlayerBrush {
	
	Boolean surfaceEnabled;
	Boolean maskEnabled;
	Boolean enabled;
	int brushSize;
	int chance;
	int thickness;
	int fractureDistance;
	int angleDistance;
	int falloffStrength;
	int mixingStrength;
	double minAngleHeightDifference;
	String axis;
	Brush brush;
	Inventory gui;
	BlockType mask;
	List<BlockType> blocks;
	
	
	public PlayerBrush(){
		surfaceEnabled = Main.getSettings().isSurfaceModeEnabledDefault();
		maskEnabled = Main.getSettings().isMaskEnabledDefault();
		enabled = Main.getSettings().isEnabledDefault();
		chance = Main.getSettings().getDefaultChance();
		thickness = Main.getSettings().getDefaultThickness();
		fractureDistance  = Main.getSettings().getDefaultFractureDistance();
		angleDistance = Main.getSettings().getDefaultAngleDistance();
		minAngleHeightDifference = Main.getSettings().getDefaultAngleHeightDifference();
		falloffStrength = 50;
		mixingStrength = 50;
		axis = "y";
		brush = Main.getBrushManager().cycle(brush);
		brushSize = Main.getSettings().getDefaultSize();
		blocks = new ArrayList<>();
		blocks.add(new BlockType(XMaterial.STONE.parseMaterial(), (short)0));
		mask = new BlockType(XMaterial.SPONGE.parseMaterial(), (short)0);
		gui = GUI.Generate(this);
	}
	
	public void updateInventory(){
		GUI.Update(gui, this);
	}
	
	public Brush getBrush(){
		return brush;
	}

	public int getFalloffStrength(){
		return falloffStrength;
	}

	public int getMixingStrength(){
		return mixingStrength;
	}

	public void increaseFalloffStrength(){
		if (falloffStrength<=90){
			falloffStrength+=10;
		}
		updateInventory();
	}

	public void decreaseFalloffStrength(){
		if (falloffStrength>=10){
			falloffStrength-=10;
		}
		updateInventory();
	}

	public void increaseMixingStrength(){
		if (mixingStrength<=90){
			mixingStrength+=10;
		}
		updateInventory();
	}

	public void decreaseMixingStrength(){
		if (mixingStrength>=10){
			mixingStrength-=10;
		}
		updateInventory();
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
	
	public BlockType getMask(){
		return mask;
	}
	
	public void setMask(BlockType bt){
		mask = bt;
		updateInventory();
	}
	
	public List<BlockType> getBlocks(){
		return blocks;
	}
	
	public void addBlock(BlockType bt, int slot){
		if (blocks.size()>=slot){
			blocks.set(slot-1, bt);
		}
		else {
			blocks.add(bt);
		}
		updateInventory();
	}
	
	public void removeBlock(int slot){
		if (blocks.size()>=slot){
			blocks.remove(slot-1);
			updateInventory();
		}
	}
	
	public void cycleBrush(){
		brush = Main.getBrushManager().cycle(brush);
		updateInventory();
	}
	
	public void cycleBrushBackwards() {
		brush = Main.getBrushManager().cycleBack(brush);
		updateInventory();
	}
	
	public int getBrushSize(){
		return brushSize;
	}


	public Inventory getInventory() {
		return gui;
	}
	
	public void setBrushSize(int size) {
		if (size<=Main.getSettings().getMaxSize()&&size>0){
			brushSize=size;
		}
		else if (size>Main.getSettings().getMaxSize()){
			brushSize=Main.getSettings().getMaxSize();
		}
		else {
			brushSize=1;
		}
		updateInventory();
	}
	
	public void increaseBrushSize(boolean x10){
		if (x10){
			if (brushSize+10<=Main.getSettings().getMaxSize()){
				brushSize+=10;
			}
			else {
				brushSize = Main.getSettings().getMaxSize();
			}
		}
		else {
			if (brushSize<Main.getSettings().getMaxSize()){
				brushSize+=1;
			}
		}
		updateInventory();
	}
	
	public void decreaseBrushSize(boolean x10){
		if (x10){
			if (brushSize-10>=1){
				brushSize-=10;
			}
			else {
				brushSize=1;
			}
		}
		else {
			if (brushSize>1){
				brushSize-=1;
			}
		}
		updateInventory();
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void toggleEnabled(){
		if (enabled){
			enabled = false;
		}
		else {
			enabled = true;
		}
		updateInventory();
	}

	public int getChance() {
		return chance;
	}
	
	public void increaseChance(){
		if (chance<90){
			chance+=10;
		}
		updateInventory();
	}
	
	public void decreaseChance(){
		if (chance>10){
			chance-=10;
		}
		updateInventory();
	}
	
	public void increaseThickness(){
		if (thickness<Main.getSettings().getMaxThickness()){
			thickness+=1;
		}
		updateInventory();
	}
	
	public void decreaseThickness(){
		if (thickness>1){
			thickness-=1;
		}
		updateInventory();
	}
	
	public void increaseAngleDistance(){
		if (angleDistance<Main.getSettings().getMaxAngleDistance()){
			angleDistance+=1;
		}
		updateInventory();
	}
	
	public void decreaseAngleDistance(){
		if (angleDistance>1){
			angleDistance-=1;
		}
		updateInventory();
	}
	
	public void increaseFractureDistance(){
		if (this.fractureDistance<Main.getSettings().getMaxFractureDistance()){
			this.fractureDistance+=1;
		}
		updateInventory();
	}
	
	public void decreaseFractureDistance(){
		if (this.fractureDistance>1){
			this.fractureDistance-=1;
		}
		updateInventory();
	}
	
	public void increaseAngleHeightDifference(boolean d15){
		if (d15){
			minAngleHeightDifference+=15.0;
		}
		else{
			minAngleHeightDifference+=5.0;
		}
		if (minAngleHeightDifference>Main.getSettings().getMaxAngleHeightDifference()){
			minAngleHeightDifference = Main.getSettings().getMaxAngleHeightDifference();
		}
		updateInventory();
	}
	
	public void decreaseAngleHeightDifference(boolean d15){
		if (d15){
			minAngleHeightDifference-=15.0;
		}
		else{
			minAngleHeightDifference-=5.0;
		}
		if (minAngleHeightDifference<Main.getSettings().getMinAngleHeightDifference()){
			minAngleHeightDifference = Main.getSettings().getMinAngleHeightDifference();
		}
		updateInventory();
	}

	public boolean isMaskEnabled() {
		return maskEnabled;
	}
	
	public void toggleMask(){
		if (maskEnabled){
			maskEnabled = false;
		}
		else {
			maskEnabled = true;
		}
		updateInventory();
	}
	
	public boolean isSurfaceModeEnabled(){
		return surfaceEnabled;
	}
	
	public void toggleSurfaceMode(){
		if (surfaceEnabled){
			surfaceEnabled = false;
		}
		else {
			surfaceEnabled = true;
		}
		updateInventory();
	}

	public int getThickness() {
		return thickness;
	}

	public String getAxis() {
		return axis;
	}
	
	public void cycleAxis(){
		if (axis.equals("y")){
			axis = "z";
		}
		else if (axis.equals("z")){
			axis = "x";
			
		}
		else if (axis.equals("x")){
			axis = "y";
		}
		updateInventory();
	}

	public ItemStack export(ItemStack i){
		String lore = "___&8Size: " + brushSize;
		if (brush instanceof SplatterBrush ||brush instanceof SprayBrush){
			lore += "___&8Chance: " + chance + "%";
		}
		else if (brush instanceof OverlayBrush){
			lore += "___&8Thickness: " + thickness;
		}
		else if (brush instanceof DiscBrush){
			lore += "___&8Axis: " + axis;
		}
		else if (brush instanceof AngleBrush){
			lore += "___&8AngleDistance: " + this.angleDistance;
			lore += "___&8AngleHeightDifference: " + this.minAngleHeightDifference;
		}
		else if (brush instanceof GradientBrush){
			lore += "___&8Mixing: " + this.mixingStrength;
			lore += "___&8Falloff: " + this.falloffStrength;
		}
		else if (brush instanceof FractureBrush){
			lore += "___&8FractureDistance: " + this.fractureDistance;
		}
		lore += "___&8Blocks:";
		if (blocks.isEmpty()){
			lore += " none";
		}
		else {
			for (BlockType bt : blocks){
				lore += " " + bt.getMaterial().toString().toLowerCase() + ":" + bt.getData();
			}
		}	
		if (maskEnabled){
			lore += "___&8Mask: " + mask.getMaterial().toString().toLowerCase() + ":" + mask.getData();
		}
		if (surfaceEnabled){
			lore += "___&8Surface Mode";
		}
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(" §b♦ " + brush.getName() + " §b♦ ");
		if (lore != ""){
			String[] loreListArray = lore.split("___");
			List<String> loreList = new ArrayList<String>();
			for (String s : loreListArray) {loreList.add(s.replace("&", "§"));}
			im.setLore(loreList);
		}
		im.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		i.setItemMeta(im);
		return i;
	}

	public void setBrush(Brush b) {
		this.brush = b;
	}
	
}
