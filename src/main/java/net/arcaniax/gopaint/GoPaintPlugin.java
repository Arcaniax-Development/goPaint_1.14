/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint;

import io.papermc.lib.PaperLib;
import net.arcaniax.gopaint.command.Handler;
import net.arcaniax.gopaint.listeners.ConnectListener;
import net.arcaniax.gopaint.listeners.InteractListener;
import net.arcaniax.gopaint.listeners.InventoryListener;
import net.arcaniax.gopaint.objects.other.Settings;
import net.arcaniax.gopaint.objects.player.PlayerBrushManager;
import net.arcaniax.gopaint.utils.DisabledBlocks;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.serverlib.ServerLib;


public class GoPaintPlugin extends JavaPlugin implements Listener {

    private static final int BSTATS_ID = 10557;
    public static boolean plotSquaredEnabled;
    private static PlayerBrushManager manager;
    private static GoPaintPlugin goPaintPlugin;
    private static Settings settings;
    public ConnectListener connectListener;
    public InteractListener interactListener;
    public InventoryListener inventoryListener;
    public Handler cmdHandler;

    public static GoPaintPlugin getGoPaintPlugin() {
        return goPaintPlugin;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static PlayerBrushManager getBrushManager() {
        return manager;
    }

    public static boolean isPlotSquaredEnabled() {
        return plotSquaredEnabled;
    }

    public static void reload() {
        GoPaintPlugin.getGoPaintPlugin().reloadConfig();
        manager = new PlayerBrushManager();
        settings = new Settings();
        settings.loadConfig();
    }

    public void onEnable() {
        this.saveDefaultConfig();
        goPaintPlugin = this;
        manager = new PlayerBrushManager();
        settings = new Settings();
        settings.loadConfig();
        connectListener = new ConnectListener(goPaintPlugin);
        interactListener = new InteractListener(goPaintPlugin);
        inventoryListener = new InventoryListener(goPaintPlugin);
        cmdHandler = new Handler(goPaintPlugin);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(connectListener, this);
        pm.registerEvents(interactListener, this);
        pm.registerEvents(inventoryListener, this);
        pm.registerEvents(cmdHandler, this);
        getCommand("gopaint").setExecutor(cmdHandler);
        DisabledBlocks.addBlocks();
        // Check if we are in a safe environment
        ServerLib.checkUnsafeForks();
        ServerLib.isJavaSixteen();
        PaperLib.suggestPaper(this);

        Metrics metrics = new Metrics(this, BSTATS_ID);

        metrics.addCustomChart(new SimplePie(
                "worldeditImplementation",
                () -> Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null ? "FastAsyncWorldEdit" : "WorldEdit"
        ));
    }

}
