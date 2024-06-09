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

import java.util.List;

public class FractureBrush extends Brush {

    @Override
    public void paint(final Location location, final Player player, final BrushSettings brushSettings) {
        performEdit(player, session -> {
            List<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.getSize());
            for (Block block : blocks) {
                if (!passesDefaultChecks(brushSettings, player, block)) {
                    continue;
                }

                if (Height.getAverageHeightDiffFracture(block.getLocation(), Height.getHeight(block.getLocation()), 1) < 0.1) {
                    return;
                }

                if (Height.getAverageHeightDiffFracture(
                        block.getLocation(),
                        Height.getHeight(block.getLocation()),
                        brushSettings.getFractureDistance()
                ) < 0.1) {
                    return;
                }

                setBlock(session, block, brushSettings.getRandomBlock());
            }
        });
    }

    @Override
    public String getName() {
        return "Fracture Brush";
    }

}
