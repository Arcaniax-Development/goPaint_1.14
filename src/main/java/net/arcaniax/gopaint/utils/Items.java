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
        ItemStack is = new ItemStack(mat, amount);
        ItemMeta meta = is.getItemMeta();
        if (!lore.isEmpty()) {
            String[] loreListArray = lore.split("___");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.isEmpty()) {
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
        if (!lore.isEmpty()) {
            String[] loreListArray = lore.split("___");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.isEmpty()) {
            meta.setDisplayName(name.replace("&", "§"));
        }
        item.setItemMeta(meta);
        if (item.getItemMeta() instanceof SkullMeta) {
            SkullMeta headMeta = (SkullMeta) item.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), "goPaint");
            profile.getProperties().put("textures", new Property("textures", data));
            try {
                Field profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                if (profileField.getType() == GameProfile.class) {
                    profileField.set(headMeta, profile);
                } else {
                    Class<?> resolvableProfileClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");
                    profileField.set(headMeta, resolvableProfileClass.getConstructor(GameProfile.class).newInstance(profile));
                }
            } catch (Exception ignored) {
            }
            item.setItemMeta(headMeta);
        }
        return item;
    }

}
