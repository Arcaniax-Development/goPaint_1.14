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
import net.onelitefeather.bettergopaint.utils.ConnectedBlocks;
import net.onelitefeather.bettergopaint.utils.Sphere;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class BucketBrush extends Brush {

    private static final @NotNull String DESCRIPTION = "Paints connected blocks\n&8with the same block type";
    private static final @NotNull String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAxOGI0NTc0OTM5Nzg4YTJhZDU1NTJiOTEyZDY3ODEwNjk4ODhjNTEyMzRhNGExM2VhZGI3ZDRjOTc5YzkzIn19fQ==";
    private static final @NotNull String NAME = "Bucket Brush";

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
            List<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.size(), null, false).toList();
            Stream<Block> connectedBlocks = ConnectedBlocks.getConnectedBlocks(location, blocks);
            connectedBlocks.filter(block -> passesDefaultChecks(brushSettings, player, block))
                    .forEach(block -> setBlock(session, block, brushSettings.randomBlock()));
        });
    }

}
