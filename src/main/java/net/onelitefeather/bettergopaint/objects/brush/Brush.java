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
import com.sk89q.worldedit.math.BlockVector3;
import net.onelitefeather.bettergopaint.brush.BrushSettings;
import net.onelitefeather.bettergopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class Brush {

    /**
     * Retrieves the description of the brush.
     *
     * @return The description of the brush.
     */
    public abstract @NotNull String getDescription();

    /**
     * Retrieves the head of the brush.
     *
     * @return The head of the brush.
     */
    public abstract @NotNull String getHead();

    /**
     * Retrieves the name of the brush.
     *
     * @return The name of the brush.
     */
    public abstract @NotNull String getName();

    /**
     * Performs a painting action using the provided location, player, and brush settings.
     *
     * @param location      The location the painting action is performed.
     * @param player        The player who is performing the paint action.
     * @param brushSettings The brush settings to be applied while painting.
     */
    public abstract void paint(
            @NotNull Location location,
            @NotNull Player player,
            @NotNull BrushSettings brushSettings
    );

    /**
     * Sets the material of a block in an EditSession.
     *
     * @param session  The EditSession to perform the block update in.
     * @param block    The block to update.
     * @param material The material to set the block to.
     * @throws MaxChangedBlocksException If the maximum number of changed blocks is exceeded.
     */
    protected void setBlock(
            @NotNull EditSession session,
            @NotNull Block block,
            @NotNull Material material
    ) throws MaxChangedBlocksException {
        BlockVector3 vector = BlockVector3.at(block.getX(), block.getY(), block.getZ());
        if (session.getMask() == null || session.getMask().test(vector)) {
            session.setBlock(vector, BukkitAdapter.asBlockType(material));
        }
    }

    /**
     * Performs an edit using WorldEdit's EditSession.
     * This method wraps the edit session in a try-with-resources block to ensure proper cleanup of resources.
     *
     * @param player The player performing the edit.
     * @param edit   A Consumer functional interface that defines the actions to be taken within the edit session.
     */
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

    /**
     * Checks if a given block passes the default checks defined by the brush settings.
     *
     * @param brushSettings The brush settings to be checked against.
     * @param player        The player using the brush.
     * @param block         The block being checked.
     * @return true if the block passes all the default checks, false otherwise.
     */
    protected boolean passesDefaultChecks(@NotNull BrushSettings brushSettings, @NotNull Player player, @NotNull Block block) {
        return passesMaskCheck(brushSettings, block) && passesSurfaceCheck(brushSettings, player, block);
    }

    /**
     * Checks if a given block passes the surface check defined by the brush settings.
     *
     * @param brushSettings The brush settings to be checked against.
     * @param player        The player using the brush.
     * @param block         The block being checked.
     * @return true if the block passes the surface check, false otherwise.
     */
    protected boolean passesSurfaceCheck(@NotNull BrushSettings brushSettings, @NotNull Player player, @NotNull Block block) {
        return Surface.isOnSurface(block, brushSettings.surfaceMode(), player.getLocation());
    }

    /**
     * Checks if a given block passes the mask check defined by the brush settings.
     *
     * @param brushSettings The brush settings to be checked against.
     * @param block         The block being checked.
     * @return true if the block passes the mask check, false otherwise.
     */
    protected boolean passesMaskCheck(@NotNull BrushSettings brushSettings, @NotNull Block block) {
        return !brushSettings.maskEnabled() || block.getType().equals(brushSettings.mask());
    }

}
