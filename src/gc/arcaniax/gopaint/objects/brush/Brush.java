package gc.arcaniax.gopaint.objects.brush;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import gc.arcaniax.gopaint.objects.player.ExportedPlayerBrush;

public abstract class Brush {
	public abstract void paint(Location loc, Player p);
	public abstract void paint(Location loc, Player p, ExportedPlayerBrush epb);
	public abstract String getName();
}
