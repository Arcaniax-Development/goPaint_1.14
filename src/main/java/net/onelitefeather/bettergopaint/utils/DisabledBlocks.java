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
package net.onelitefeather.bettergopaint.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class DisabledBlocks {

    private static final List<Material> disabledMaterials = new ArrayList<>();

    static {
        disabledMaterials.add(Material.ENCHANTING_TABLE);
        disabledMaterials.add(Material.CHEST);
        disabledMaterials.add(Material.END_PORTAL_FRAME);
        disabledMaterials.add(Material.ANVIL);
        disabledMaterials.add(Material.DISPENSER);
        disabledMaterials.add(Material.COMMAND_BLOCK);
        disabledMaterials.add(Material.DAYLIGHT_DETECTOR);
        disabledMaterials.add(Material.TNT);
        disabledMaterials.add(Material.TRAPPED_CHEST);
        disabledMaterials.add(Material.OAK_TRAPDOOR);
        disabledMaterials.add(Material.IRON_TRAPDOOR);
        disabledMaterials.add(Material.BEACON);
        disabledMaterials.add(Material.CACTUS);
        disabledMaterials.add(Material.FURNACE);
        disabledMaterials.add(Material.HOPPER);
    }

    public static boolean isDisabled(Material m) {
        return disabledMaterials.contains(m);
    }

}
