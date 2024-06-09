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

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.Vector3;
import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class Brush {

    public abstract void paint(Location location, Player player, BrushSettings brushSettings);

    public abstract String getName();

    protected void setBlock(
            @NotNull EditSession session,
            @NotNull Block block,
            @NotNull Material material
    ) throws MaxChangedBlocksException {

        Vector3 v = Vector3.at(block.getX(), block.getY(), block.getZ());
        if (session.getMask() != null && !session.getMask().test(v.toBlockPoint())) {
            return;
        }
        session.setBlock(
                block.getX(), block.getY(), block.getZ(),
                BukkitAdapter.asBlockType(material)
        );
    }

    protected void performEdit(Player player, Consumer<EditSession> edit) {
        BukkitPlayer wrapped = BukkitAdapter.adapt(player);
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(wrapped);
        try (EditSession editsession = localSession.createEditSession(wrapped)) {
            try {
                edit.accept(editsession);
            } finally {
                localSession.remember(editsession);
            }
        }
    }

    protected boolean passesDefaultChecks(BrushSettings brushSettings, Player player, Block block) {
        if (brushSettings.isSurfaceMode() && !Surface.isOnSurface(block, player.getLocation())) {
            return false;
        }
        return !brushSettings.isMask() || block.getType().equals(brushSettings.getMask());
    }

}
