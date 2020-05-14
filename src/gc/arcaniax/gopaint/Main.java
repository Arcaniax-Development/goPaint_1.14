package gc.arcaniax.gopaint;

import gc.arcaniax.gopaint.objects.other.NmsManager;
import gc.arcaniax.gopaint.utils.DisabledBlocks;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import gc.arcaniax.gopaint.command.Handler;
import gc.arcaniax.gopaint.listeners.ConnectListener;
import gc.arcaniax.gopaint.listeners.InteractListener;
import gc.arcaniax.gopaint.objects.other.Settings;
import gc.arcaniax.gopaint.listeners.InventoryListener;
import gc.arcaniax.gopaint.objects.player.PlayerBrushManager;


public class Main extends JavaPlugin implements Listener{
	private static PlayerBrushManager manager;
	private static Main main;
	private static Settings settings;
	public ConnectListener connectListener;
	public InteractListener interactListener;
	public InventoryListener inventoryListener;
	public Handler cmdHandler;
	public static boolean plotSquaredEnabled;
	public static NmsManager nmsManager;
	
	public void onEnable(){
		this.saveDefaultConfig();
		main = this;
		manager = new PlayerBrushManager();
		settings = new Settings();
		settings.loadConfig();
		connectListener = new ConnectListener(main);
		interactListener = new InteractListener(main);
		inventoryListener = new InventoryListener(main);
		cmdHandler = new Handler(main);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(connectListener, this);
		pm.registerEvents(interactListener, this);
		pm.registerEvents(inventoryListener, this);
		pm.registerEvents(cmdHandler, this);
		getCommand("gopaint").setExecutor(cmdHandler);
		nmsManager = new NmsManager();
		DisabledBlocks.addBlocks();
	}
	
	public static Main getMain(){
		return main;
	}
	
	public static Settings getSettings(){
		return settings;
	}
	
	public static PlayerBrushManager getBrushManager() {
		return manager;
	}
	
	public static boolean isPlotSQuaredEnabled(){
		return plotSquaredEnabled;
	}
	
	public static void reload(){
		Main.getMain().reloadConfig();
		manager = new PlayerBrushManager();
		settings = new Settings();
		settings.loadConfig();
	}
}
