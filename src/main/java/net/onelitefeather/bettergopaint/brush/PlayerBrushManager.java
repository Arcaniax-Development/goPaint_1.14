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
package net.onelitefeather.bettergopaint.brush;

import net.onelitefeather.bettergopaint.objects.brush.AngleBrush;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import net.onelitefeather.bettergopaint.objects.brush.BucketBrush;
import net.onelitefeather.bettergopaint.objects.brush.DiscBrush;
import net.onelitefeather.bettergopaint.objects.brush.FractureBrush;
import net.onelitefeather.bettergopaint.objects.brush.GradientBrush;
import net.onelitefeather.bettergopaint.objects.brush.OverlayBrush;
import net.onelitefeather.bettergopaint.objects.brush.PaintBrush;
import net.onelitefeather.bettergopaint.objects.brush.SphereBrush;
import net.onelitefeather.bettergopaint.objects.brush.SplatterBrush;
import net.onelitefeather.bettergopaint.objects.brush.SprayBrush;
import net.onelitefeather.bettergopaint.objects.brush.UnderlayBrush;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBrushManager {

    private final HashMap<String, PlayerBrush> playerBrushes = new HashMap<>();
    private final List<Brush> brushes = new ArrayList<>();

    public PlayerBrushManager() {
        brushes.add(new SphereBrush());
        brushes.add(new SprayBrush());
        brushes.add(new SplatterBrush());
        brushes.add(new DiscBrush());
        brushes.add(new BucketBrush());
        brushes.add(new AngleBrush());
        brushes.add(new OverlayBrush());
        brushes.add(new UnderlayBrush());
        brushes.add(new FractureBrush());
        brushes.add(new GradientBrush());
        brushes.add(new PaintBrush());
    }

    public PlayerBrush getBrush(Player player) {
        if (playerBrushes.containsKey(player.getName())) {
            return playerBrushes.get(player.getName());
        } else {
            PlayerBrush pb = new PlayerBrush(this);
            playerBrushes.put(player.getName(), pb);
            return pb;
        }
    }

    public String getBrushLore(String name) {
        StringBuilder lore = new StringBuilder();
        for (Brush brush : brushes) {
            if (brush.getName().equalsIgnoreCase(name)) {
                lore.append("&e").append(brush.getName()).append("\n");
            } else {
                lore.append("&8").append(brush.getName()).append("\n");
            }
        }
        return lore.substring(0, lore.length());
    }

    public Brush getBrushHandler(String name) {
        for (Brush brush : brushes) {
            if (brush.getName().equalsIgnoreCase(name)) {
                return brush;
            }
        }
        return brushes.getFirst();
    }

    public List<Brush> getBrushes() {
        return brushes;
    }

    public void removeBrush(Player player) {
        playerBrushes.remove(player.getName());
    }

    public Brush cycleForward(Brush brush) {
        if (brush == null) {
            return brushes.getFirst();
        }
        int next = brushes.indexOf(brush) + 1;
        if (next < brushes.size()) {
            return brushes.get(next);
        }
        return brushes.getFirst();
    }

    public Brush cycleBack(Brush brush) {
        if (brush == null) {
            return brushes.getFirst();
        }
        int back = brushes.indexOf(brush) - 1;
        if (back >= 0) {
            return brushes.get(back);
        }
        return brushes.getLast();
    }
}
