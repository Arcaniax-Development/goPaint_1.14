package net.onelitefeather.bettergopaint.objects.other;

import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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
     * @see Surface#isOnSurface(Block, BrushSettings, Location)
     */
    DISABLED("Disabled"),
    /**
     * This enumeration represents the original surface mode check.
     *
     * @see Surface#isRelativelyOnSurface(Block, Location)
     */
    RELATIVE("Relative");

    private final @NotNull String name;

    SurfaceMode(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getName() {
        return name;
    }

    public static @NotNull Optional<SurfaceMode> byName(@NotNull String name) {
        return Arrays.stream(values())
                .filter(surfaceMode -> surfaceMode.getName().equals(name))
                .findAny();
    }
}
