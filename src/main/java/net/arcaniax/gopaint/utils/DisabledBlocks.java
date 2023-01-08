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
package net.arcaniax.gopaint.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DisabledBlocks {

    private static final List<Material> disabledMaterials = new ArrayList();

    public static void addBlocks() {
        disabledMaterials.add(XMaterial.ENCHANTING_TABLE.parseMaterial());
        disabledMaterials.add(XMaterial.CHEST.parseMaterial());
        disabledMaterials.add(XMaterial.END_PORTAL_FRAME.parseMaterial());
        disabledMaterials.add(XMaterial.ANVIL.parseMaterial());
        disabledMaterials.add(XMaterial.DISPENSER.parseMaterial());
        disabledMaterials.add(XMaterial.COMMAND_BLOCK.parseMaterial());
        disabledMaterials.add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial());
        disabledMaterials.add(XMaterial.TNT.parseMaterial());
        disabledMaterials.add(XMaterial.TRAPPED_CHEST.parseMaterial());
        disabledMaterials.add(XMaterial.OAK_TRAPDOOR.parseMaterial());
        disabledMaterials.add(XMaterial.IRON_TRAPDOOR.parseMaterial());
        disabledMaterials.add(XMaterial.BEACON.parseMaterial());
        disabledMaterials.add(XMaterial.CACTUS.parseMaterial());
        disabledMaterials.add(XMaterial.FURNACE.parseMaterial());
        disabledMaterials.add(XMaterial.HOPPER.parseMaterial());
        for (XMaterial material : XMaterial.values()) {
            for (String s : material.m) {
                if (s.toLowerCase().contains("shulker_box")) {
                    disabledMaterials.add(material.parseMaterial());
                } else if (s.toLowerCase().contains("trapdoor")) {
                    disabledMaterials.add(material.parseMaterial());
                } else if (s.toLowerCase().contains("pressure_plate")) {
                    disabledMaterials.add(material.parseMaterial());
                } else if (s.toLowerCase().contains("banner")) {
                    disabledMaterials.add(material.parseMaterial());
                }
            }
        }
    }

    public static boolean isDisabled(Material m) {
        return disabledMaterials.contains(m);
    }

}
