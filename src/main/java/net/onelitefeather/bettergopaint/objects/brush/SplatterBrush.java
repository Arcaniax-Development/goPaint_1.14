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
package net.onelitefeather.bettergopaint.objects.brush;

import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.utils.Sphere;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class SplatterBrush extends Brush {

    private static final @NotNull String DESCRIPTION = "More chance when closer\n&8to the clicked point\n&8and configurable chance";
    private static final @NotNull String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzMzODI5MmUyZTY5ZjA5MDY5NGNlZjY3MmJiNzZmMWQ4Mzc1OGQxMjc0NGJiNmZmYzY4MzRmZGJjMWE5ODMifX19";
    private static final @NotNull String NAME = "Splatter Brush";

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull String getHead() {
        return HEAD;
    }

    @Override
    public @NotNull String getName() {
        return NAME;
    }

    @Override
    public void paint(
            @NotNull Location location,
            @NotNull Player player,
            @NotNull BrushSettings brushSettings
    ) {
        performEdit(player, session -> {
            Stream<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.size(), null, false);
            blocks.filter(block -> passesDefaultChecks(brushSettings, player, block))
                    .forEach(block -> {
                        double rate = (block.getLocation().distance(location) - ((double) brushSettings.size() / 2.0)
                                * ((100.0 - (double) brushSettings.falloffStrength()) / 100.0))
                                / (((double) brushSettings.size() / 2.0) - ((double) brushSettings.size() / 2.0)
                                * ((100.0 - (double) brushSettings.falloffStrength()) / 100.0));

                        if (brushSettings.random().nextDouble() <= rate) {
                            return;
                        }

                        setBlock(session, block, brushSettings.randomBlock());
                    });
        });
    }

}
