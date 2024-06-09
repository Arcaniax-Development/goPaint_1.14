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

import java.util.List;

public class GradientBrush extends Brush {

    @Override
    public void paint(final Location location, final Player player, final BrushSettings brushSettings) {
        performEdit(player, session -> {
            double y = location.getBlockY() - ((double) brushSettings.getSize() / 2.0);
            List<Block> blocks = Sphere.getBlocksInRadius(location, brushSettings.getSize());
            for (Block block : blocks) {
                if (!passesDefaultChecks(brushSettings, player, block)) {
                    continue;
                }

                double rate = (block.getLocation().distance(location) - ((double) brushSettings.getSize() / 2.0)
                        * ((100.0 - (double) brushSettings.getFalloffStrength()) / 100.0))
                        / (((double) brushSettings.getSize() / 2.0) - ((double) brushSettings.getSize() / 2.0)
                        * ((100.0 - (double) brushSettings.getFalloffStrength()) / 100.0));

                if (brushSettings.getRandom().nextDouble() <= rate) {
                    continue;
                }

                double _y = (block.getLocation().getBlockY() - y) / (double) brushSettings.getSize() * blocks.size();
                int b = (int) (_y + (brushSettings.getRandom().nextDouble() * 2 - 1)
                        * ((double) brushSettings.getMixingStrength() / 100.0));

                setBlock(session, block, brushSettings.getBlocks().get(Math.clamp(b, 0, brushSettings.getBlocks().size() - 1)));
            }
        });
    }

    @Override
    public String getName() {
        return "Gradient Brush";
    }

}
