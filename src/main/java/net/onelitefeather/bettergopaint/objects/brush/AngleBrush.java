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
import net.onelitefeather.bettergopaint.utils.Height;
import net.onelitefeather.bettergopaint.utils.Sphere;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AngleBrush extends Brush {

    private static final @NotNull String DESCRIPTION = "Only works on cliffs";
    private static final @NotNull String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNDQ4ZjBkYmU3NmJiOGE4MzJjOGYzYjJhMDNkMzViZDRlMjc4NWZhNWU4Mjk4YzI2MTU1MDNmNDdmZmEyIn19fQ==";
    private static final @NotNull String NAME = "Angle Brush";

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
    public void paint(final @NotNull Location location, final @NotNull Player player, final @NotNull BrushSettings brushSettings) {
        performEdit(player, session -> {
            List<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.size());
            for (Block block : blocks) {
                if (!passesDefaultChecks(brushSettings, player, block)) {
                    continue;
                }

                if (Height.getAverageHeightDiffAngle(block.getLocation(), 1) >= 0.1
                        && Height.getAverageHeightDiffAngle(block.getLocation(), brushSettings.angleDistance())
                        >= Math.tan(Math.toRadians(brushSettings.angleHeightDifference()))) {
                    continue;
                }

                setBlock(session, block, brushSettings.randomBlock());
            }
        });
    }

}
