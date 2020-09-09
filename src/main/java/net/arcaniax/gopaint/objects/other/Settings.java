package net.arcaniax.gopaint.objects.other;

import java.util.ArrayList;
import java.util.List;

import net.arcaniax.gopaint.Main;

public class Settings {
	int maxSize;
	int defaultSize;
	int maxHistory;
	int defaultChance;
	int defaultThickness;
	int defaultAngleDistance;
	int maxAngleDistance;
	int defaultFractureDistance;
	int maxFractureDistance;
	double minAngleHeightDifference;
	double defaultAngleHeightDifference;
	double maxAngleHeightDifference;
	int maxThickness;
	String prefix;
	List<String> disabledWorldNames;
	boolean enabledByDefault;
	boolean maskEnabled;
	boolean boundingBoxEnabled;
	boolean surfaceModeEnabled;
	
	public Settings(){
		defaultSize = 10;
		maxSize = 100;
		defaultChance = 50;
		defaultThickness = 1;
		maxThickness = 5;
		defaultAngleDistance = 2;
		defaultFractureDistance = 2;
		maxFractureDistance = 5;
		maxAngleDistance = 5;
		minAngleHeightDifference = 10.0;
		defaultAngleHeightDifference = 40.0;
		maxAngleHeightDifference = 85.0;
		maxHistory = 100;
		prefix = "§bgoPaint> ";
		disabledWorldNames = new ArrayList<>();
		enabledByDefault = false;
		maskEnabled = true;
		surfaceModeEnabled = false;
	}
	
	public void loadConfig(){
		maxSize = Main.getMain().getConfig().getInt("size.max");
		defaultSize = Main.getMain().getConfig().getInt("size.default");
		int chance = Main.getMain().getConfig().getInt("chance.default");
		if (chance>0&&chance<100&&chance%10==0){
			defaultChance = chance;
		}
		defaultThickness = Main.getMain().getConfig().getInt("thickness.default");
		
		defaultAngleDistance = Main.getMain().getConfig().getInt("angleDistance.default");
		
		maxAngleDistance = Main.getMain().getConfig().getInt("angleDistance.max");
		
		defaultFractureDistance = Main.getMain().getConfig().getInt("fractureDistance.default");
		
		maxFractureDistance = Main.getMain().getConfig().getInt("fractureDistance.max");
		
		double minAngle = Main.getMain().getConfig().getDouble("angleHeightDifference.min");
		if (minAngle>0&&minAngle<90&&minAngle%5==0){
			minAngleHeightDifference = minAngle;
		}
		
		double defaultAngle = Main.getMain().getConfig().getDouble("angleHeightDifference.default");
		if (defaultAngle>0&&defaultAngle<90&&defaultAngle%5==0){
			defaultAngleHeightDifference = defaultAngle;
		}
		double maxAngle = Main.getMain().getConfig().getDouble("angleHeightDifference.max");
		if (maxAngle>0&&maxAngle<90&&maxAngle%5==0){
			maxAngleHeightDifference = maxAngle;
		}
		
		maxThickness = Main.getMain().getConfig().getInt("thickness.max");
		maxHistory = Main.getMain().getConfig().getInt("history.max");
		
		disabledWorldNames = Main.getMain().getConfig().getStringList("disabledWorlds");
		enabledByDefault = Main.getMain().getConfig().getBoolean("toggles.enabledByDefault");
		maskEnabled = Main.getMain().getConfig().getBoolean("toggles.maskEnabled");
		boundingBoxEnabled = Main.getMain().getConfig().getBoolean("toggles.boundingBoxEnabled");
		surfaceModeEnabled = Main.getMain().getConfig().getBoolean("toggles.surfaceModeEnabled");
	}
	
	public int getDefaultSize(){
		return defaultSize;
	}
	
	public int getDefaultChance(){
		return defaultChance;
	}
	
	public int getDefaultThickness(){
		return defaultThickness;
	}
	
	public double getDefaultAngleHeightDifference(){
		return defaultAngleHeightDifference;
	}
	
	public int getDefaultAngleDistance(){
		return defaultAngleDistance;
	}
	
	public int getMaxSize(){
		return maxSize;
	}
	
	public int getMaxThickness(){
		return maxThickness;
	}
	
	public double getMinAngleHeightDifference(){
		return minAngleHeightDifference;
	}
	
	public double getMaxAngleHeightDifference(){
		return maxAngleHeightDifference;
	}
	
	public int getMaxAngleDistance(){
		return maxAngleDistance;
	}
	
	public String getPrefix(){
		return prefix;
	}

	public int getMaxHistory() {
		return maxHistory;
	}
	
	public List<String> getDisabledWorlds() {
		return disabledWorldNames;
	}
	
	public boolean isEnabledDefault(){
		return enabledByDefault;
	}
	
	public boolean isMaskEnabledDefault(){
		return maskEnabled;
	}
	
	public boolean isSurfaceModeEnabledDefault(){
		return surfaceModeEnabled;
	}

	public int getDefaultFractureDistance() {
		return this.defaultFractureDistance;
	}
	
	public int getMaxFractureDistance() {
		return this.maxFractureDistance;
	}
}
