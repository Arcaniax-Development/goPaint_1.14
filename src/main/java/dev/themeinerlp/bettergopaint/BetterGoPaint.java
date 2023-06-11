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
package dev.themeinerlp.bettergopaint;

import io.papermc.lib.PaperLib;
import dev.themeinerlp.bettergopaint.command.Handler;
import dev.themeinerlp.bettergopaint.listeners.ConnectListener;
import dev.themeinerlp.bettergopaint.listeners.InteractListener;
import dev.themeinerlp.bettergopaint.listeners.InventoryListener;
import dev.themeinerlp.bettergopaint.objects.other.NmsManager;
import dev.themeinerlp.bettergopaint.objects.other.Settings;
import dev.themeinerlp.bettergopaint.objects.player.PlayerBrushManager;
import dev.themeinerlp.bettergopaint.utils.DisabledBlocks;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.serverlib.ServerLib;


public class BetterGoPaint extends JavaPlugin implements Listener {

    private static final int BSTATS_ID = 10557;
    public static boolean plotSquaredEnabled;
    public static NmsManager nmsManager;
    private static PlayerBrushManager manager;
    private static BetterGoPaint betterGoPaint;
    private static Settings settings;
    public ConnectListener connectListener;
    public InteractListener interactListener;
    public InventoryListener inventoryListener;
    public Handler cmdHandler;

    public static BetterGoPaint getGoPaintPlugin() {
        return betterGoPaint;
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
        BetterGoPaint.getGoPaintPlugin().reloadConfig();
        manager = new PlayerBrushManager();
        settings = new Settings();
        settings.loadConfig();
    }

    public void onEnable() {

        // Check if we are in a safe environment
        ServerLib.checkUnsafeForks();
        ServerLib.isJavaSixteen();
        PaperLib.suggestPaper(this);

        this.saveDefaultConfig();
        betterGoPaint = this;
        manager = new PlayerBrushManager();
        settings = new Settings();
        settings.loadConfig();
        connectListener = new ConnectListener(betterGoPaint);
        interactListener = new InteractListener(betterGoPaint);
        inventoryListener = new InventoryListener(betterGoPaint);
        cmdHandler = new Handler(betterGoPaint);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(connectListener, this);
        pm.registerEvents(interactListener, this);
        pm.registerEvents(inventoryListener, this);
        pm.registerEvents(cmdHandler, this);
        getCommand("gopaint").setExecutor(cmdHandler);
        nmsManager = new NmsManager();
        DisabledBlocks.addBlocks();


        Metrics metrics = new Metrics(this, BSTATS_ID);

        metrics.addCustomChart(new SimplePie(
                "worldeditImplementation",
                () -> Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null ? "FastAsyncWorldEdit" : "WorldEdit"
        ));
    }

}
