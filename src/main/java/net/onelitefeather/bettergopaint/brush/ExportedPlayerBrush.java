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
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    private static final Random RANDOM = new SecureRandom();

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

    public static Builder builder(@NotNull Brush brush) {
        return new Builder(brush);
    }

    public static final class Builder {

        private final Brush brush;

        private List<Material> blocks = Collections.emptyList();
        private Axis axis = Settings.settings().generic.DEFAULT_AXIS;
        private SurfaceMode surfaceMode = SurfaceMode.DISABLED;

        private Material mask;

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

    @Deprecated(forRemoval = true)
    public static ExportedPlayerBrush parse(@NotNull Brush brush, @NotNull ItemMeta itemMeta) {
        ExportedPlayerBrush.Builder builder = ExportedPlayerBrush.builder(brush);
        Optional.ofNullable(itemMeta.getLore()).ifPresent(lore -> lore.stream()
                .map(line -> line.replace("§8", ""))
                .forEach(line -> {
                    if (line.startsWith("Size: ")) {
                        builder.size(Integer.parseInt(line.substring(6)));
                    } else if (line.startsWith("Chance: ")) {
                        builder.chance(Integer.parseInt(line.substring(8, line.length() - 1)));
                    } else if (line.startsWith("Thickness: ")) {
                        builder.thickness(Integer.parseInt(line.substring(11)));
                    } else if (line.startsWith("Axis: ")) {
                        builder.axis(Axis.valueOf(line.substring(6).toUpperCase()));
                    } else if (line.startsWith("FractureDistance: ")) {
                        builder.fractureDistance(Integer.parseInt(line.substring(18)));
                    } else if (line.startsWith("AngleDistance: ")) {
                        builder.angleDistance(Integer.parseInt(line.substring(15)));
                    } else if (line.startsWith("AngleHeightDifference: ")) {
                        builder.angleHeightDifference(Double.parseDouble(line.substring(23)));
                    } else if (line.startsWith("Mixing: ")) {
                        builder.mixingStrength(Integer.parseInt(line.substring(8)));
                    } else if (line.startsWith("Falloff: ")) {
                        builder.falloffStrength(Integer.parseInt(line.substring(9)));
                    } else if (line.startsWith("Blocks: ")) {
                        builder.blocks(Arrays.stream(line.substring(8).split(", "))
                                .map(Material::matchMaterial)
                                .filter(Objects::nonNull)
                                .toList());
                    } else if (line.startsWith("Mask: ")) {
                        builder.mask(Material.matchMaterial(line.substring(6)));
                    } else if (line.startsWith("Surface Mode: ")) {
                        SurfaceMode.byName(line.substring(14)).ifPresent(builder::surfaceMode);
                    }
                }));
        return builder.build();
    }

}
