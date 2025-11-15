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
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.Material;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        try {
                PlayerProfile playerProfile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(), "goPaint");
                PlayerTextures texture = playerProfile.getTextures();
                String url = null;
                byte[] decoded = Base64.getDecoder().decode(data);
                try {
                    url = new String(decoded, StandardCharsets.UTF_8);
                } catch (Exception ignored) {}
                url = url.replace("{\"textures\":{\"SKIN\":{\"url\":\"", "").replace("\"}}}", "");
                texture.setSkin(new URL(url));
                headMeta.setOwnerProfile(playerProfile);
            } catch (Exception ignored) {
            }
        List<String> loreList = new ArrayList<>();
        if (!lore.isEmpty()) {
			String[] loreListArray = lore.split("___");
			for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
        }
        headMeta.setLore(loreList);
        if (!name.isEmpty()) {
            headMeta.setDisplayName(name.replace("&", "§"));
        }
        item.setItemMeta(headMeta);
        return item;
    }
}
