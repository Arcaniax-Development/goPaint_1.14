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
package net.onelitefeather.bettergopaint.objects.other;

import net.onelitefeather.bettergopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public enum SurfaceMode {
    /**
     * This enumeration represents a more intuitive check.
     *
     * @see Surface#isDirectlyOnSurface(Block)
     */
    DIRECT("Direct"),
    /**
     * This enumeration represents that surface mode is disabled.
     *
     * @see Surface#isOnSurface(Block, SurfaceMode, Location)
     */
    DISABLED("Disabled"),
    /**
     * This enumeration represents the original surface mode check.
     *
     * @see Surface#isRelativelyOnSurface(Block, Location)
     */
    RELATIVE("Relative");

    private static final SurfaceMode[] VALUES = values();

    private final @NotNull String name;

    /**
     * Constructs a new surface mode entry with the given name.
     * @param name the name of the surface mode
     */
    SurfaceMode(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the name of the surface mode.
     * @return the name from the surface mode
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns the {@link SurfaceMode} enum entry by the given name.
     * @param name the name of the surface mode
     * @return the surface mode by the given name wrapped in an {@link Optional}
     */
    public static @NotNull Optional<SurfaceMode> byName(@NotNull String name) {
        SurfaceMode surfaceMode = null;
        for (int i = 0; i < VALUES.length && surfaceMode == null; i++) {
            surfaceMode = VALUES[i].getName().equals(name) ? VALUES[i] : null;
        }
        return Optional.ofNullable(surfaceMode);
    }
}
