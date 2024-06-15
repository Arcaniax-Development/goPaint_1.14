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

import java.util.stream.Stream;

public class FractureBrush extends Brush {

    private static final String NAME = "Fracture Brush";
    private static final String DESCRIPTION = "Places blocks in cracks/fisures";
    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNkZjczZWVlNjIyNGM1YzVkOTQ4ZDJhMzQ1ZGUyNWYyMDhjYmQ5YWY3MTA4Y2UxZTFiNjFhNTg2ZGU5OGIyIn19fQ==";

    public FractureBrush() {
        super(NAME, DESCRIPTION, HEAD);
    }

    @Override
    public void paint(
            @NotNull Location location,
            @NotNull Player player,
            @NotNull BrushSettings brushSettings
    ) {
        performEdit(player, session -> {
            Stream<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.size(), null, false);
            blocks.filter(block -> passesMaskCheck(brushSettings, block))
                    .filter(block -> Height.getAverageHeightDiffFracture(
                            block.getLocation(),
                            Height.getNearestNonEmptyBlock(block.getLocation()),
                            1
                    ) >= 0.1)
                    .filter(block -> Height.getAverageHeightDiffFracture(
                            block.getLocation(),
                            Height.getNearestNonEmptyBlock(block.getLocation()),
                            brushSettings.fractureDistance()
                    ) >= 0.1)
                    .forEach(block -> setBlock(session, block, brushSettings.randomBlock()));
        });
    }

}
