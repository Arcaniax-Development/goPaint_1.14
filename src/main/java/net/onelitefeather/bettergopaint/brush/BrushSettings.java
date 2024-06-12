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
import net.onelitefeather.bettergopaint.objects.other.SurfaceMode;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * The BrushSettings interface defines the configuration settings for a brush. It provides methods to retrieve information
 * about the brush's axis, brush type, list of blocks, mask material, enabled status, surface mode, angle-height
 * difference, angle distance, chance, falloff strength, fracture distance, mixing strength, size and thickness.
 */
public interface BrushSettings {

    /**
     * Returns the axis used by the brush settings.
     *
     * @return the axis used by the brush settings
     */
    @NotNull
    Axis axis();

    /**
     * Returns the brush used by the brush settings.
     *
     * @return The brush used by the brush settings.
     */
    @NotNull
    Brush brush();

    /**
     * Returns the list of blocks used by the brush settings.
     *
     * @return the list of blocks used by the brush settings
     */
    @NotNull
    List<Material> blocks();

    /**
     * Retrieves the mask material used by the brush settings.
     *
     * @return The mask material.
     * @deprecated the mask-material is going to be replaced with a WorldEdit Mask
     */
    @NotNull
    @Deprecated(since = "1.1.0-SNAPSHOT")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.2.0")
    Material mask();

    /**
     * Checks if the brush is enabled.
     *
     * @return true if the brush is enabled, false otherwise
     */
    boolean enabled();

    /**
     * Checks if the mask is enabled.
     *
     * @return true if the mask is enabled, false otherwise.
     */
    boolean maskEnabled();

    /**
     * Returns the surface mode used by the brush settings.
     *
     * @return The surface mode used by the brush settings.
     */
    SurfaceMode surfaceMode();

    /**
     * Returns the angle-height difference used by the brush settings.
     *
     * @return The angle-height difference used by the brush settings.
     */
    double angleHeightDifference();

    /**
     * Returns the angle distance used by the brush settings.
     *
     * @return The angle distance used by the brush settings.
     */
    int angleDistance();

    /**
     * Returns the chance of the brush being applied to a block.
     *
     * @return The chance of the brush being applied to a block.
     */
    int chance();

    /**
     * Returns the falloff strength used by the brush settings.
     *
     * @return The falloff strength used by the brush settings.
     */
    int falloffStrength();

    /**
     * Returns the fracture distance used by the brush settings.
     *
     * @return The fracture distance used by the brush settings.
     */
    int fractureDistance();

    /**
     * Returns the mixing strength used by the brush settings.
     *
     * @return The mixing strength used by the brush settings.
     */
    int mixingStrength();

    /**
     * Returns the size of the brush settings.
     *
     * @return The size of the brush settings.
     */
    int size();

    /**
     * Returns the thickness used by the brush settings.
     *
     * @return The thickness used by the brush settings.
     */
    int thickness();

    /**
     * Picks a random block material from {@link #blocks()}.
     *
     * @return The randomly picked block material.
     */
    @NotNull
    Material randomBlock();

    /**
     * The random number generator instance.
     *
     * @return a Random instance
     */
    @NotNull
    Random random();

}
