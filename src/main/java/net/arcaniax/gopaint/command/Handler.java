package net.arcaniax.gopaint.command;

import net.arcaniax.gopaint.Main;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Handler implements Listener, CommandExecutor
{
	  public static Main plugin;
	  
	  public Handler(Main main)
	  {
	    plugin = main;
	  }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gopaint")||cmd.getName().equalsIgnoreCase("gp")){
			if (!(sender instanceof Player)){return false;}
			Player p = (Player)sender;
			PlayerBrush pb = Main.getBrushManager().getPlayerBrush(p);
			String prefix = Main.getSettings().getPrefix();
			if (!p.hasPermission("gopaint.use")){
				p.sendMessage(prefix + "§cyou are not creative enough, sorry.");
				return true;}
			if (args.length==0){
				if (p.hasPermission("gopaint.admin")){
					p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
					return true;
				}
				p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
				return true;
			}
			else if (args.length==1){
				if (args[0].equalsIgnoreCase("size")){
					p.sendMessage(prefix + "§c/gp size [number]");
					return true;
				}
				else if (args[0].equalsIgnoreCase("toggle")){
					if (pb.isEnabled()){
						pb.toggleEnabled();
						p.sendMessage(prefix + "§cdisabled brush");
					}
					else {
						pb.toggleEnabled();
						p.sendMessage(prefix + "§aenabled brush");
					}
					return true;
				}
				else if ((args[0].equalsIgnoreCase("reload")||args[0].equalsIgnoreCase("r"))&&p.hasPermission("gobrush.admin")){
					Main.reload();
					p.sendMessage(prefix + "§areloaded");
					return true;
				}
				else if (args[0].equalsIgnoreCase("info")||args[0].equalsIgnoreCase("i")){
					
					p.spigot().sendMessage( new ComponentBuilder( "goPaint> " ).color( ChatColor.AQUA )
							.append( "Created by: " ).color( ChatColor.GOLD )
							.append( "Arcaniax" ).color( ChatColor.YELLOW ).create());
							
					
					p.spigot().sendMessage( new ComponentBuilder( "goPaint> " ).color( ChatColor.AQUA )
							.append( "Links: " ).color( ChatColor.GOLD )
							.append( "Twitter" ).color( ChatColor.DARK_AQUA ).color(ChatColor.UNDERLINE)
							.event(new ClickEvent( ClickEvent.Action.OPEN_URL, "https://twitter.com/Arcaniax")).append("     ")
							.append( "Spigot" ).color( ChatColor.YELLOW ).color(ChatColor.UNDERLINE)
							.event(new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/authors/arcaniax.47444/")).create());
					return true;
				}
				if (p.hasPermission("gopaint.admin")){
					p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
					return true;
				}
				p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
				return true;
			}
			else if (args.length==2){
				if (args[0].equalsIgnoreCase("size")||args[0].equalsIgnoreCase("s")){
					try {
						Integer sizeAmount = Integer.parseInt(args[1]);
						pb.setBrushSize(sizeAmount);
						p.sendMessage(prefix + "§6size set to: §e" + pb.getBrushSize());
						return true;
					}
					catch (Exception e){
						p.sendMessage(prefix + "§c/gb size [number]");
						return true;
					}
				}
				if (p.hasPermission("gopaint.admin")){
					p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo§7|§creload");
					return true;
				}
				p.sendMessage(prefix + "§c/gp size§7|§ctoggle§7|§cinfo");
				return true;
			}
		}
		return false;
	}
}
