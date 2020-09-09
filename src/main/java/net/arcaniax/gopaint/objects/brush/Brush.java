package net.arcaniax.gopaint.objects.brush;

import net.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Brush {
	public abstract void paint(Location loc, Player p);
	public abstract void paint(Location loc, Player p, ExportedPlayerBrush epb);
	public abstract String getName();
}
