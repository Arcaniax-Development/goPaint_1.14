package net.arcaniax.gopaint.listeners;

import net.arcaniax.gopaint.*;
import net.arcaniax.gopaint.objects.player.*;
import net.arcaniax.gopaint.utils.*;
import net.arcaniax.gopaint.Main;
import net.arcaniax.gopaint.objects.player.ExportedPlayerBrush;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.XMaterial;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import java.util.*;

public class InteractListener implements Listener{
	  public Main plugin;

	  public InteractListener(Main main)
	  {
	    plugin = main;
	  }

	  @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOWEST)
		public void onClick(PlayerInteractEvent e){
	  		if (Main.nmsManager.isAtLeastVersion(1,9,0)){
				if (e.getHand() == EquipmentSlot.OFF_HAND) {
					return;
				}
			}
			if (!e.getPlayer().hasPermission("gopaint.use")){return;}
			if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
				if (e.getPlayer().getItemInHand().hasItemMeta()&&e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()&&e.getPlayer().getItemInHand().getItemMeta().getDisplayName().startsWith(" §b♦ ")&&e.getPlayer().getItemInHand().getItemMeta().hasLore()){
					final ExportedPlayerBrush epb = new ExportedPlayerBrush(e.getPlayer().getItemInHand().getItemMeta().getDisplayName(), e.getPlayer().getItemInHand().getItemMeta().getLore());
					final Player p = e.getPlayer();
					final Location loc;
					if (e.getAction().equals(Action.RIGHT_CLICK_AIR)){
						loc = p.getTargetBlock((Set<Material>) null, 250).getLocation().clone();
					}
					else {
						loc = e.getClickedBlock().getLocation().clone();
					}
					epb.getBrush().paint(loc, p, epb);
				}
			}
			if (e.getPlayer().getItemInHand().getType()== XMaterial.FEATHER.parseMaterial() &&(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
				e.setCancelled(true);
				final Player p = e.getPlayer();
				final Location loc;
				if (e.getAction().equals(Action.RIGHT_CLICK_AIR)){
					loc = p.getTargetBlock((Set<Material>) null, 250).getLocation().clone();
				}
				else {
					loc = e.getClickedBlock().getLocation().clone();
				}
				if ((!e.getPlayer().hasPermission("gopaint.world.bypass"))&&(Main.getSettings().getDisabledWorlds().contains(loc.getWorld().getName()))){
					return;
				}
				if (loc.getBlock().getType().equals(XMaterial.AIR.parseMaterial())){return;}
				final PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
				if (pb.isEnabled()){
					pb.getBrush().paint(loc, p);
				}
				else {
					p.sendMessage(Main.getSettings().getPrefix() + "§cyour brush is disabled, left click to enable the brush.");
				}
			}
			if (e.getPlayer().getItemInHand().getType()==XMaterial.FEATHER.parseMaterial()&&(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK))){
				e.setCancelled(true);
				Player p = e.getPlayer();
				PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
				p.openInventory(pb.getInventory());
			}
		}
}
