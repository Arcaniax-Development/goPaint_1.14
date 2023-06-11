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
package dev.themeinerlp.bettergopaint.listeners;

import com.cryptomorin.xseries.XMaterial;
import dev.themeinerlp.bettergopaint.BetterGoPaint;
import dev.themeinerlp.bettergopaint.objects.brush.*;
import dev.themeinerlp.bettergopaint.objects.other.BlockType;
import dev.themeinerlp.bettergopaint.objects.player.PlayerBrush;
import dev.themeinerlp.bettergopaint.utils.DisabledBlocks;
import dev.themeinerlp.bettergopaint.utils.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    public BetterGoPaint plugin;

    public InventoryListener(BetterGoPaint main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void menuClick(InventoryClickEvent e) {
        try {
            Player p = (Player) e.getWhoClicked();
            if (!e.getView().getTitle().contains("§1goPaint Menu")) {
                return;
            }
            if (e.getView().getTopInventory() != e.getClickedInventory()) {
                if (e.getClick().isShiftClick() || e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    e.setCancelled(true);
                }
                return;
            }
            PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            if (e.getRawSlot() == 10 || e.getRawSlot() == 1 || e.getRawSlot() == 19) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (e.getCursor() != null) {
                        if (!e.getCursor().getType().isBlock()) {
                            if (!e.getCursor().getType().equals(XMaterial.FEATHER.parseMaterial())) {
                                pb.export(e.getCursor());
                            }
                        }
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.toggleEnabled();
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 11 || e.getRawSlot() == 2 || e.getRawSlot() == 20) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    pb.cycleBrush();
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.cycleBrushBackwards();
                } else if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                    p.openInventory(GUI.GenerateBrushes());
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 12 || e.getRawSlot() == 3 || e.getRawSlot() == 21) {
                Brush b = pb.getBrush();
                if (b instanceof SprayBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseChance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseChance();
                    }
                } else if (b instanceof OverlayBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseThickness();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseThickness();
                    }
                } else if (b instanceof FractureBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseFractureDistance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseFractureDistance();
                    }
                } else if (b instanceof AngleBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseAngleDistance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseAngleDistance();
                    }
                } else if (b instanceof GradientBrush || b instanceof PaintBrush
                        || b instanceof SplatterBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseFalloffStrength();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseFalloffStrength();
                    }
                } else if (b instanceof DiscBrush) {
                    pb.cycleAxis();
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 13 || e.getRawSlot() == 4 || e.getRawSlot() == 22) {
                Brush b = pb.getBrush();
                if (b instanceof AngleBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseAngleHeightDifference(false);
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseAngleHeightDifference(false);
                    } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        pb.increaseAngleHeightDifference(true);
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        pb.decreaseAngleHeightDifference(true);
                    }
                } else if (b instanceof GradientBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseMixingStrength();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseMixingStrength();
                    }
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 14 || e.getRawSlot() == 5 || e.getRawSlot() == 23) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    pb.increaseBrushSize(false);
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.decreaseBrushSize(false);
                } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                    pb.increaseBrushSize(true);
                } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                    pb.decreaseBrushSize(true);
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 15 || e.getRawSlot() == 6 || e.getRawSlot() == 24) {
                pb.toggleMask();
                e.setCancelled(true);
            } else if (e.getRawSlot() == 16 || e.getRawSlot() == 7 || e.getRawSlot() == 25) {
                pb.toggleSurfaceMode();
                e.setCancelled(true);
            } else if ((e.getRawSlot() >= 37 && e.getRawSlot() <= 41) || (e.getRawSlot() >= 46 && e.getRawSlot() <= 50)) {
                int slot;
                if (e.getRawSlot() >= 37 && e.getRawSlot() <= 41) {
                    slot = e.getRawSlot() - 36;
                } else {
                    slot = e.getRawSlot() - 45;
                }
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (e.getCursor() != null && e.getCursor().getType().isBlock() && e
                            .getCursor()
                            .getType()
                            .isSolid() && (!DisabledBlocks
                            .isDisabled(e.getCursor().getType()))) {
                        pb.addBlock(new BlockType(e.getCursor().getType(), e.getCursor().getDurability()), slot);
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.removeBlock(slot);
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 43 || e.getRawSlot() == 52) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (e.getCursor() != null && e.getCursor().getType().isBlock() && e
                            .getCursor()
                            .getType()
                            .isSolid() && (!DisabledBlocks.isDisabled(e.getCursor().getType()))) {
                        pb.setMask(new BlockType(e.getCursor().getType(), e.getCursor().getDurability()));
                    }
                }
                e.setCancelled(true);
            } else if (e.getView().getTitle().contains("§1goPaint Menu")) {
                e.setCancelled(true);
            }
        } catch (NullPointerException e1) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void menuBrushClick(InventoryClickEvent e) {
        try {
            Player p = (Player) e.getWhoClicked();
            if (!e.getView().getTitle().contains("§1goPaint Brushes")) {
                return;
            }
            if (e.getView().getTopInventory() != e.getClickedInventory()) {
                if (e.getClick().isShiftClick() || e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    e.setCancelled(true);
                }
                return;
            }
            PlayerBrush pb = BetterGoPaint.getBrushManager().getPlayerBrush(p);
            boolean check = false;
            if (XMaterial.supports(13)) {
                if (e.getCurrentItem().getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())) {
                    check = true;
                }
            } else {
                if (e.getCurrentItem().getType().equals(Material.getMaterial("SKULL_ITEM"))) {
                    check = true;
                }
            }
            if (check) {
                pb.setBrush(BetterGoPaint
                        .getBrushManager()
                        .getBrush(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§6", "")));
                pb.updateInventory();
                p.openInventory(pb.getInventory());
                e.setCancelled(true);
            } else if (e.getView().getTitle().contains("§1goPaint Brushes")) {
                e.setCancelled(true);
            }
        } catch (NullPointerException e1) {
            e.getWhoClicked().closeInventory();
        }
    }

}
