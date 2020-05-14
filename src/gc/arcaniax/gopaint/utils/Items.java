package gc.arcaniax.gopaint.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Items {
	public Items(){}

	public ItemStack create(Material mat, short data, int amount,String name, String lore){
		ItemStack is = new ItemStack(mat);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		if (lore != ""){
			String[] loreListArray = lore.split("___");
			List<String> loreList = new ArrayList<String>();
			for (String s : loreListArray) {loreList.add(s.replace("&", "§"));}
			meta.setLore(loreList);
		}
		if (name != ""){meta.setDisplayName(name.replace("&", "§"));}
		is.setItemMeta(meta);
		is.setDurability(data);
		return is;
	}

	public ItemStack createHead(String data, int amount, String name, String lore){
        ItemStack item;
	    if (XMaterial.isNewVersion()){
            item = XMaterial.PLAYER_HEAD.parseItem();
        }
        else{
            item = new ItemStack(Material.getMaterial("SKULL_ITEM"));
            item.setDurability((short)3);
        }
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		if (lore != ""){
			String[] loreListArray = lore.split("___");
			List<String> loreList = new ArrayList<String>();
			for (String s : loreListArray) {loreList.add(s.replace("&", "§"));}
			meta.setLore(loreList);
		}
		if (name != ""){meta.setDisplayName(name.replace("&", "§"));}
		item.setItemMeta(meta);
		if (item.getItemMeta() instanceof SkullMeta){
			SkullMeta headMeta = (SkullMeta)item.getItemMeta();
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			profile.getProperties().put("textures", new Property("textures", new String(data)));
			Field profileField = null;
			try
			{
				profileField = headMeta.getClass().getDeclaredField("profile");
				profileField.setAccessible(true);
				profileField.set(headMeta, profile);
			}
			catch (Exception e){}
			item.setItemMeta(headMeta);
		}
		return item;
	}
}