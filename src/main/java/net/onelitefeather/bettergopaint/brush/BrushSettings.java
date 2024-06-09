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

import net.onelitefeather.bettergopaint.objects.brush.Brush;
import org.bukkit.Axis;
import org.bukkit.Material;

import java.util.List;
import java.util.Random;

public interface BrushSettings {

    Axis getAxis();

    Brush getBrush();

    List<Material> getBlocks();

    Material getMask();

    boolean isEnabled();

    boolean isMask();

    boolean isSurfaceMode();

    double getAngleHeightDifference();

    int getAngleDistance();

    int getChance();

    int getFalloffStrength();

    int getFractureDistance();

    int getMixingStrength();

    int getSize();

    int getThickness();

    Material getRandomBlock();

    Random getRandom();

}
