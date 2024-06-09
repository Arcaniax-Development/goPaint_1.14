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

    private static final List<Material> disabledMaterials = new ArrayList<>() {{
        add(Material.ENCHANTING_TABLE);
        add(Material.CHEST);
        add(Material.END_PORTAL_FRAME);
        add(Material.ANVIL);
        add(Material.DISPENSER);
        add(Material.COMMAND_BLOCK);
        add(Material.DAYLIGHT_DETECTOR);
        add(Material.TNT);
        add(Material.TRAPPED_CHEST);
        add(Material.OAK_TRAPDOOR);
        add(Material.IRON_TRAPDOOR);
        add(Material.BEACON);
        add(Material.CACTUS);
        add(Material.FURNACE);
        add(Material.HOPPER);
    }};

    public static boolean isDisabled(Material m) {
        return disabledMaterials.contains(m);
    }

}
