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
package net.onelitefeather.bettergopaint.objects.player;

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
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerBrushManager {

    private final HashMap<String, PlayerBrush> playerBrushes;
    private final List<Brush> brushes;

    public PlayerBrushManager() {
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

    public PlayerBrush getPlayerBrush(Player p) {
        if (playerBrushes.containsKey(p.getName())) {
            return playerBrushes.get(p.getName());
        } else {
            PlayerBrush pb = new PlayerBrush();
            playerBrushes.put(p.getName(), pb);
            return pb;
        }
    }

    public String getBrushLore(String name) {
        // &eSphere Brush___&8Spray Brush___&8Splatter Brush___&8Disc Brush___&8Bucket Brush___&8Angle Brush___&8Overlay Brush
        StringBuilder s = new StringBuilder();
        for (Brush b : brushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                s.append("&e").append(b.getName()).append("___");
            } else {
                s.append("&8").append(b.getName()).append("___");
            }
        }
        return s.substring(0, s.length() - 3);
    }

    public Brush getBrush(String name) {
        for (Brush b : brushes) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return brushes.get(0);
    }

    public List<Brush> getBrushes() {
        return brushes;
    }

    public void removePlayerBrush(Player p) {
        if (playerBrushes.containsKey(p.getName())) {
            playerBrushes.remove(playerBrushes.get(p.getName()));
        }
    }

    public Brush cycle(Brush b) {
        if (b == null) {
            return brushes.get(0);
        }
        int next = brushes.indexOf(b) + 1;
        if (next < brushes.size()) {
            return brushes.get(next);
        }
        return brushes.get(0);
    }

    public Brush cycleBack(Brush b) {
        if (b == null) {
            return brushes.get(0);
        }
        int back = brushes.indexOf(b) - 1;
        if (back >= 0) {
            return brushes.get(back);
        }
        return brushes.get(brushes.size() - 1);
    }

}
