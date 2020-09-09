package net.arcaniax.gopaint.objects.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.arcaniax.gopaint.objects.brush.AngleBrush;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.BucketBrush;
import net.arcaniax.gopaint.objects.brush.DiscBrush;
import net.arcaniax.gopaint.objects.brush.FractureBrush;
import net.arcaniax.gopaint.objects.brush.GradientBrush;
import net.arcaniax.gopaint.objects.brush.OverlayBrush;
import net.arcaniax.gopaint.objects.brush.PaintBrush;
import net.arcaniax.gopaint.objects.brush.SphereBrush;
import net.arcaniax.gopaint.objects.brush.SplatterBrush;
import net.arcaniax.gopaint.objects.brush.SprayBrush;
import org.bukkit.entity.Player;

import net.arcaniax.gopaint.objects.brush.*;

public class PlayerBrushManager {
	private HashMap<String, PlayerBrush> playerBrushes;
	private List<Brush> brushes;
	
	public PlayerBrushManager(){
		playerBrushes = new HashMap<>();
		brushes = new ArrayList<>();
		brushes.add(new SphereBrush());
		brushes.add(new SprayBrush());
		brushes.add(new SplatterBrush());
		brushes.add(new DiscBrush());
		brushes.add(new BucketBrush());
		brushes.add(new AngleBrush());
		brushes.add(new OverlayBrush());
		brushes.add(new FractureBrush());
		brushes.add(new GradientBrush());
		brushes.add(new PaintBrush());
	}
	
	public PlayerBrush getPlayerBrush(Player p){
		if (playerBrushes.containsKey(p.getName())){
			return playerBrushes.get(p.getName());
		}
		else {
			PlayerBrush pb = new PlayerBrush();
			playerBrushes.put(p.getName(), pb);
			return pb;
		}
	}
	
	public String getBrushLore(String name){
		// &eSphere Brush___&8Spray Brush___&8Splatter Brush___&8Disc Brush___&8Bucket Brush___&8Angle Brush___&8Overlay Brush
		String s = "";
		for (Brush b : brushes){
			if (b.getName().equalsIgnoreCase(name)){
				s += "&e" + b.getName() + "___";
			}
			else{
				s += "&8" + b.getName() + "___";
			}
		}
		return s.substring(0, s.length()-3);
	}
	
	public Brush getBrush(String name){
		for (Brush b : brushes){
			if (b.getName().equalsIgnoreCase(name)){
				return b;
			}
		}
		return brushes.get(0);
	}
	
	public List<Brush> getBrushes(){
		return brushes;
	}
	
	public void removePlayerBrush(Player p){
		if (playerBrushes.containsKey(p.getName())){
			playerBrushes.remove(playerBrushes.get(p.getName()));
		}
	}
	
	public Brush cycle(Brush b){
		if (b==null){return brushes.get(0);}
		int next = brushes.indexOf(b)+1;
		if (next < brushes.size()){
			return brushes.get(next);
		}
		return brushes.get(0);
	}
	
	public Brush cycleBack(Brush b){
		if (b==null){return brushes.get(0);}
		int back = brushes.indexOf(b)-1;
		if (back >= 0){
			return brushes.get(back);
		}
		return brushes.get(brushes.size()-1);
	}
}
