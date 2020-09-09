/*
 *                             _____      _       _
 *                            |  __ \    (_)     | |
 *                  __ _  ___ | |__) |_ _ _ _ __ | |_
 *                 / _` |/ _ \|  ___/ _` | | '_ \| __|
 *                | (_| | (_) | |  | (_| | | | | | |_
 *                 \__, |\___/|_|   \__,_|_|_| |_|\__|
 *                  __/ |
 *                 |___/
 *
 *    goPaint is designed to simplify painting inside of Minecraft.
 *                     Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.arcaniax.gopaint.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Items {

    public Items() {
    }

    public ItemStack create(Material mat, short data, int amount, String name, String lore) {
        ItemStack is = new ItemStack(mat);
        is.setAmount(amount);
        ItemMeta meta = is.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("___");
            List<String> loreList = new ArrayList<String>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.equals("")) {
            meta.setDisplayName(name.replace("&", "§"));
        }
        is.setItemMeta(meta);
        is.setDurability(data);
        return is;
    }

    public ItemStack createHead(String data, int amount, String name, String lore) {
        ItemStack item;
        if (XMaterial.isNewVersion()) {
            item = XMaterial.PLAYER_HEAD.parseItem();
        } else {
            item = new ItemStack(Material.getMaterial("SKULL_ITEM"));
            item.setDurability((short) 3);
        }
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        if (lore != "") {
            String[] loreListArray = lore.split("___");
            List<String> loreList = new ArrayList<String>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.equals("")) {
            meta.setDisplayName(name.replace("&", "§"));
        }
        item.setItemMeta(meta);
        if (item.getItemMeta() instanceof SkullMeta) {
            SkullMeta headMeta = (SkullMeta) item.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", data));
            Field profileField = null;
            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (Exception ignored) {
            }
            item.setItemMeta(headMeta);
        }
        return item;
    }
}