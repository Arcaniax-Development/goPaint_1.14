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
package net.onelitefeather.bettergopaint.listeners;

import net.kyori.adventure.text.TextComponent;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import net.onelitefeather.bettergopaint.objects.brush.AngleBrush;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import net.onelitefeather.bettergopaint.objects.brush.DiscBrush;
import net.onelitefeather.bettergopaint.objects.brush.FractureBrush;
import net.onelitefeather.bettergopaint.objects.brush.GradientBrush;
import net.onelitefeather.bettergopaint.objects.brush.OverlayBrush;
import net.onelitefeather.bettergopaint.objects.brush.PaintBrush;
import net.onelitefeather.bettergopaint.objects.brush.SplatterBrush;
import net.onelitefeather.bettergopaint.objects.brush.SprayBrush;
import net.onelitefeather.bettergopaint.objects.brush.UnderlayBrush;
import net.onelitefeather.bettergopaint.utils.DisabledBlocks;
import net.onelitefeather.bettergopaint.utils.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public final class InventoryListener implements Listener {

    private final BetterGoPaint plugin;

    public InventoryListener(BetterGoPaint plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void menuClick(InventoryClickEvent e) {
        try {
            Player p = (Player) e.getWhoClicked();
            if (!(e.getView().title() instanceof TextComponent text) || !text.content().equals("goPaint Menu")) {
                return;
            }
            if (e.getView().getTopInventory() != e.getClickedInventory()) {
                if (e.getClick().isShiftClick() || e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    e.setCancelled(true);
                }
                return;
            }
            PlayerBrush pb = plugin.getBrushManager().getBrush(p);
            if (e.getRawSlot() == 10 || e.getRawSlot() == 1 || e.getRawSlot() == 19) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (!e.getCursor().getType().isBlock()) {
                        if (!e.getCursor().getType().equals(Material.FEATHER)) {
                            pb.export(e.getCursor());
                        }
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.toggle();
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 11 || e.getRawSlot() == 2 || e.getRawSlot() == 20) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    pb.cycleBrush();
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.cycleBrushBackwards();
                } else if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                    p.openInventory(GUI.generateBrushes());
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 12 || e.getRawSlot() == 3 || e.getRawSlot() == 21) {
                Brush brush = pb.brush();
                if (brush instanceof SprayBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseChance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseChance();
                    }
                } else if (brush instanceof OverlayBrush || brush instanceof UnderlayBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseThickness();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseThickness();
                    }
                } else if (brush instanceof FractureBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseFractureDistance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseFractureDistance();
                    }
                } else if (brush instanceof AngleBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseAngleDistance();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseAngleDistance();
                    }
                } else if (brush instanceof GradientBrush || brush instanceof PaintBrush
                        || brush instanceof SplatterBrush) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        pb.increaseFalloffStrength();
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        pb.decreaseFalloffStrength();
                    }
                } else if (brush instanceof DiscBrush) {
                    pb.cycleAxis();
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 13 || e.getRawSlot() == 4 || e.getRawSlot() == 22) {
                Brush b = pb.brush();
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
                    if (e.getCursor().getType().isBlock() && e.getCursor().getType().isSolid()
                            && !DisabledBlocks.isDisabled(e.getCursor().getType())) {
                        pb.addBlock(e.getCursor().getType(), slot);
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    pb.removeBlock(slot);
                }
                e.setCancelled(true);
            } else if (e.getRawSlot() == 43 || e.getRawSlot() == 52) {
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (e.getCursor().getType().isBlock() && e.getCursor().getType().isSolid()
                            && (!DisabledBlocks.isDisabled(e.getCursor().getType()))) {
                        pb.setMask(e.getCursor().getType());
                    }
                }
                e.setCancelled(true);
            } else {
                e.setCancelled(true);
            }
        } catch (NullPointerException e1) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void menuBrushClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (!(event.getView().title() instanceof TextComponent title) || !title.content().equals("goPaint Brushes")) {
            return;
        }

        if (event.getView().getTopInventory() != event.getClickedInventory()) {
            if (event.getClick().isShiftClick() || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                event.setCancelled(true);
            }
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null || !event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            return;
        }

        PlayerBrush playerBrush = plugin.getBrushManager().getBrush(player);

        ItemMeta itemMeta = event.getCurrentItem().getItemMeta();

        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return;
        }

        //noinspection deprecation
        plugin.getBrushManager().getBrushHandler(itemMeta.getDisplayName()).ifPresent(brush -> {
            playerBrush.setBrush(brush);
            playerBrush.updateInventory();
            player.openInventory(playerBrush.getInventory());
        });
    }

}
