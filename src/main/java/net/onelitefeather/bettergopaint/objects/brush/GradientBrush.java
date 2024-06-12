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

import java.util.List;

public class GradientBrush extends Brush {

    private static final @NotNull String DESCRIPTION = "Creates gradients";
    private static final @NotNull String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA2MmRhM2QzYjhmMWZkMzUzNDNjYzI3OWZiMGZlNWNmNGE1N2I1YWJjNDMxZmJiNzhhNzNiZjJhZjY3NGYifX19";
    private static final @NotNull String NAME = "Gradient Brush";

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
            double y = location.getBlockY() - (brushSettings.size() / 2d);
            Stream<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.size(), null, false);
            blocks.filter(block -> passesDefaultChecks(brushSettings, player, block)).filter(block -> {
                double rate = (block.getLocation().distance(location) - (brushSettings.size() / 2d)
                        * ((100d - brushSettings.falloffStrength()) / 100d))
                        / ((brushSettings.size() / 2d) - (brushSettings.size() / 2d)
                        * ((100d - brushSettings.falloffStrength()) / 100d));

                return brushSettings.random().nextDouble() > rate;
            }).forEach(block -> {
                int random = (int) (((block.getLocation().getBlockY() - y) / brushSettings.size()
                        * brushSettings.blocks().size()) + (brushSettings.random().nextDouble() * 2 - 1)
                        * (brushSettings.mixingStrength() / 100d));

                int index = Math.clamp(random, 0, brushSettings.blocks().size() - 1);

                setBlock(session, block, brushSettings.blocks().get(index));
            });
        });
    }

}
