package net.arcaniax.gopaint.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class DisabledBlocks
{
    private static List<Material> disabledMaterials = new ArrayList();

    public static void addBlocks()
    {
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
        for (XMaterial material : XMaterial.values()){
            for (String s : material.m){
                if (s.toLowerCase().contains("shulker_box")){
                    disabledMaterials.add(material.parseMaterial());
                }
                else if (s.toLowerCase().contains("trapdoor")){
                    disabledMaterials.add(material.parseMaterial());
                }
                else if (s.toLowerCase().contains("pressure_plate")){
                    disabledMaterials.add(material.parseMaterial());
                }
                else if (s.toLowerCase().contains("banner")){
                    disabledMaterials.add(material.parseMaterial());
                }
            }
        }
    }

    public static boolean isDisabled(Material m)
    {
        if (disabledMaterials.contains(m)) {
            return true;
        }
        return false;
    }
}
