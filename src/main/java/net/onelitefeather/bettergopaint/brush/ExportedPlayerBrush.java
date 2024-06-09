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

import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import org.bukkit.Axis;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExportedPlayerBrush implements BrushSettings {

    private final Random random = new Random();

    private boolean surfaceMode;
    private boolean maskEnabled;
    private int size = 10;
    private int chance = 50;
    private int thickness = 1;
    private int angleDistance = 2;
    private int fractureDistance = 2;
    private int falloffStrength = 50;
    private int mixingStrength = 50;
    private double angleHeightDifference = 2.5;
    private Axis axis = Axis.Y;

    private final Brush brush;
    private Material mask;
    private final List<Material> blocks = new ArrayList<>();

    public ExportedPlayerBrush(BetterGoPaint plugin, String name, List<String> lore) {
        brush = plugin.getBrushManager().getBrushHandler(name.replaceAll(" §b♦ ", ""));
        for (String s : lore) {
            if (s.startsWith("§8Size: ")) {
                size = Integer.parseInt(s.replaceAll("§8Size: ", ""));
            } else if (s.startsWith("§8Chance: ")) {
                chance = Integer.parseInt(s.replaceAll("§8Chance: ", "").replaceAll("%", ""));
            }
            if (s.startsWith("§8Thickness: ")) {
                thickness = Integer.parseInt(s.replaceAll("§8Thickness: ", ""));
            }
            if (s.startsWith("§8Axis: ")) {
                axis = Axis.valueOf(s.replaceAll("§8Axis: ", "").toUpperCase());
            }
            if (s.startsWith("§8FractureDistance: ")) {
                fractureDistance = Integer.parseInt(s.replaceAll("§8FractureDistance: ", ""));
            }
            if (s.startsWith("§8AngleDistance: ")) {
                angleDistance = Integer.parseInt(s.replaceAll("§8AngleDistance: ", ""));
            }
            if (s.startsWith("§8AngleHeightDifference: ")) {
                angleHeightDifference = Double.parseDouble(s.replaceAll("§8AngleHeightDifference: ", ""));
            }
            if (s.startsWith("§8Mixing: ")) {
                mixingStrength = Integer.parseInt(s.replaceAll("§8Mixing: ", ""));
            }
            if (s.startsWith("§8Falloff: ")) {
                falloffStrength = Integer.parseInt(s.replaceAll("§8Falloff: ", ""));
            }
            if (s.startsWith("§8Blocks: ")) {
                s = s.replaceAll("§8Blocks: ", "");
                if (!s.equals("none")) {
                    for (String type : s.split(" ")) {
                        blocks.add(Material.matchMaterial(type));
                    }
                }
            }
            if (s.startsWith("§8Mask: ")) {
                s = s.replaceAll("§8Mask: ", "");
                String[] type = s.split(":");
                mask = Material.matchMaterial(type[0].toUpperCase());
                maskEnabled = true;
            }
            if (s.startsWith("§8Surface Mode")) {
                surfaceMode = true;
            }
        }
    }

    @Override
    public int getThickness() {
        return thickness;
    }

    @Override
    public Material getRandomBlock() {
        return getBlocks().get(random.nextInt(getBlocks().size()));
    }

    @Override
    public Random getRandom() {
        return random;
    }

    @Override
    public int getFalloffStrength() {
        return falloffStrength;
    }

    @Override
    public int getFractureDistance() {
        return fractureDistance;
    }

    @Override
    public int getMixingStrength() {
        return mixingStrength;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public Brush getBrush() {
        return brush;
    }

    @Override
    public List<Material> getBlocks() {
        return blocks;
    }

    @Override
    public Material getMask() {
        return mask;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isMask() {
        return maskEnabled;
    }

    @Override
    public boolean isSurfaceMode() {
        return surfaceMode;
    }

    @Override
    public double getAngleHeightDifference() {
        return angleHeightDifference;
    }

    @Override
    public int getAngleDistance() {
        return angleDistance;
    }

    @Override
    public int getChance() {
        return chance;
    }

}
