package net.arcaniax.gopaint.listeners;

import net.arcaniax.gopaint.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener{
	  public Main plugin;
	  
	  public ConnectListener(Main main)
	  {
	    plugin = main;
	  }
	  
	  @EventHandler(priority=EventPriority.LOWEST)
		public void onQuit(PlayerQuitEvent e){
		  Main.getBrushManager().removePlayerBrush(e.getPlayer());
	  }
	  
}
