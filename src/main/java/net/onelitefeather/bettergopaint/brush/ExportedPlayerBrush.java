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
import net.onelitefeather.bettergopaint.objects.other.Settings;
import net.onelitefeather.bettergopaint.objects.other.SurfaceMode;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public record ExportedPlayerBrush(
        @NotNull Brush brush,
        @Nullable Material mask,
        @NotNull List<Material> blocks,
        @NotNull Axis axis,
        SurfaceMode surfaceMode,
        int size,
        int chance,
        int thickness,
        int angleDistance,
        int fractureDistance,
        int falloffStrength,
        int mixingStrength,
        double angleHeightDifference
) implements BrushSettings {

    private static final @NotNull Random RANDOM = new Random();

    public ExportedPlayerBrush(@NotNull Builder builder) {
        this(
                builder.brush,
                builder.mask,
                builder.blocks,
                builder.axis,
                builder.surfaceMode,
                builder.size,
                builder.chance,
                builder.thickness,
                builder.angleDistance,
                builder.fractureDistance,
                builder.falloffStrength,
                builder.mixingStrength,
                builder.angleHeightDifference
        );
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public boolean maskEnabled() {
        return mask() != null;
    }

    @Override
    public @NotNull Material randomBlock() {
        return blocks().get(random().nextInt(blocks().size()));
    }

    @Override
    public @NotNull Random random() {
        return RANDOM;
    }

    public static Builder builder(Brush brush) {
        return new Builder(brush);
    }

    public static final class Builder {

        private final @NotNull Brush brush;

        private @NotNull List<Material> blocks = Collections.emptyList();
        private @NotNull Axis axis = Settings.settings().GENERIC.DEFAULT_AXIS;
        private @NotNull SurfaceMode surfaceMode = SurfaceMode.DISABLED;

        private @Nullable Material mask;

        private int size;
        private int chance;
        private int thickness;
        private int angleDistance;
        private int fractureDistance;
        private int falloffStrength;
        private int mixingStrength;
        private double angleHeightDifference;

        private Builder(@NotNull Brush brush) {
            this.brush = brush;
        }

        public @NotNull Builder surfaceMode(SurfaceMode surfaceMode) {
            this.surfaceMode = surfaceMode;
            return this;
        }

        public @NotNull Builder blocks(@NotNull List<Material> blocks) {
            this.blocks = blocks;
            return this;
        }

        public @NotNull Builder mask(@Nullable Material mask) {
            this.mask = mask;
            return this;
        }

        public @NotNull Builder size(int size) {
            this.size = size;
            return this;
        }

        public @NotNull Builder chance(int chance) {
            this.chance = chance;
            return this;
        }

        public @NotNull Builder thickness(int thickness) {
            this.thickness = thickness;
            return this;
        }

        public @NotNull Builder angleDistance(int angleDistance) {
            this.angleDistance = angleDistance;
            return this;
        }

        public @NotNull Builder fractureDistance(int fractureDistance) {
            this.fractureDistance = fractureDistance;
            return this;
        }

        public @NotNull Builder falloffStrength(int falloffStrength) {
            this.falloffStrength = falloffStrength;
            return this;
        }

        public @NotNull Builder mixingStrength(int mixingStrength) {
            this.mixingStrength = mixingStrength;
            return this;
        }

        public @NotNull Builder angleHeightDifference(double angleHeightDifference) {
            this.angleHeightDifference = angleHeightDifference;
            return this;
        }

        public @NotNull Builder axis(@NotNull Axis axis) {
            this.axis = axis;
            return this;
        }

        public @NotNull ExportedPlayerBrush build() {
            return new ExportedPlayerBrush(this);
        }

    }

}
