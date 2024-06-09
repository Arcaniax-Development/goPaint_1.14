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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import org.bukkit.Axis;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public ExportedPlayerBrush(BetterGoPaint plugin, TextComponent name, List<Component> lore) {
        brush = plugin.getBrushManager().getBrushHandler(name.content().replace("♦", "").strip());
        lore.stream()
                .filter(component -> component instanceof TextComponent)
                .map(component -> (TextComponent) component)
                .map(TextComponent::content)
                .forEach(string -> {
                    if (string.startsWith("Size: ")) {
                        size = Integer.parseInt(string.replace("Size: ", ""));
                    } else if (string.startsWith("Chance: ")) {
                        chance = Integer.parseInt(string.replace("Chance: ", "").replace("%", ""));
                    } else if (string.startsWith("Thickness: ")) {
                        thickness = Integer.parseInt(string.replace("Thickness: ", ""));
                    } else if (string.startsWith("Axis: ")) {
                        axis = Axis.valueOf(string.replace("Axis: ", "").toUpperCase());
                    } else if (string.startsWith("FractureDistance: ")) {
                        fractureDistance = Integer.parseInt(string.replace("FractureDistance: ", ""));
                    } else if (string.startsWith("AngleDistance: ")) {
                        angleDistance = Integer.parseInt(string.replace("AngleDistance: ", ""));
                    } else if (string.startsWith("AngleHeightDifference: ")) {
                        angleHeightDifference = Double.parseDouble(string.replace("AngleHeightDifference: ", ""));
                    } else if (string.startsWith("Mixing: ")) {
                        mixingStrength = Integer.parseInt(string.replace("Mixing: ", ""));
                    } else if (string.startsWith("Falloff: ")) {
                        falloffStrength = Integer.parseInt(string.replace("Falloff: ", ""));
                    } else if (string.startsWith("Blocks: ")) {
                        Arrays.stream(string.replace("Blocks: ", "").split(", "))
                                .map(Material::matchMaterial)
                                .filter(Objects::nonNull)
                                .forEach(this.blocks::add);
                    } else if (string.startsWith("Mask: ")) {
                        this.mask = Material.matchMaterial(string.replace("Mask: ", ""));
                        maskEnabled = true;
                    } else if (string.startsWith("Surface Mode")) {
                        surfaceMode = true;
                    }
                });
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
