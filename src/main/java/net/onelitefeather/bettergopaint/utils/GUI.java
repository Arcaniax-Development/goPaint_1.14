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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.onelitefeather.bettergopaint.BetterGoPaint;
import net.onelitefeather.bettergopaint.objects.brush.AngleBrush;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import net.onelitefeather.bettergopaint.objects.brush.BucketBrush;
import net.onelitefeather.bettergopaint.objects.brush.DiscBrush;
import net.onelitefeather.bettergopaint.objects.brush.FractureBrush;
import net.onelitefeather.bettergopaint.objects.brush.GradientBrush;
import net.onelitefeather.bettergopaint.objects.brush.OverlayBrush;
import net.onelitefeather.bettergopaint.objects.brush.PaintBrush;
import net.onelitefeather.bettergopaint.objects.brush.SphereBrush;
import net.onelitefeather.bettergopaint.objects.brush.SplatterBrush;
import net.onelitefeather.bettergopaint.objects.brush.SprayBrush;
import net.onelitefeather.bettergopaint.brush.PlayerBrush;
import net.onelitefeather.bettergopaint.objects.brush.UnderlayBrush;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class GUI {

    private static final BetterGoPaint plugin = JavaPlugin.getPlugin(BetterGoPaint.class);

    private static final String headSphere = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU5OGY0ODU2MDE0N2MwYTJkNGVkYzE3ZjZkOTg1ZThlYjVkOTRiZDcyZmM2MDc0NGE1YThmMmQ5MDVhMTgifX19";
    private static final String headSpray = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg4MGY3NjVlYTgwZGVlMzcwODJkY2RmZDk4MTJlZTM2ZmRhODg0ODY5MmE4NDFiZWMxYmJkOWVkNTFiYTIyIn19fQ==";
    private static final String headSplatter = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzMzODI5MmUyZTY5ZjA5MDY5NGNlZjY3MmJiNzZmMWQ4Mzc1OGQxMjc0NGJiNmZmYzY4MzRmZGJjMWE5ODMifX19";
    private static final String headDisc = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFmMjgyNTBkMWU0MjBhNjUxMWIwMzk2NDg2OGZjYTJmNTYzN2UzYWJhNzlmNGExNjNmNGE4ZDYxM2JlIn19fQ==";
    private static final String headBucket = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAxOGI0NTc0OTM5Nzg4YTJhZDU1NTJiOTEyZDY3ODEwNjk4ODhjNTEyMzRhNGExM2VhZGI3ZDRjOTc5YzkzIn19fQ==";
    private static final String headAngle = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNDQ4ZjBkYmU3NmJiOGE4MzJjOGYzYjJhMDNkMzViZDRlMjc4NWZhNWU4Mjk4YzI2MTU1MDNmNDdmZmEyIn19fQ==";
    private static final String headOverlay = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzMWQ2Zjk2NTRmODc0ZWE5MDk3YWRlZWEwYzk2OTk2ZTc4ZTNmZDM3NTRmYmY5ZWJlOTYzYWRhZDliZTRjIn19fQ==";
    private static final String headUnderlay = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzNDQ2OTkwZjU4YjY1M2FiNWYwZTdhZjNmZGM3NTYwOTEyNzVmNGMzYzJkZDQxYzdkODYyZGQzZjkyZTg0YSJ9fX0=";
    private static final String headFracture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNkZjczZWVlNjIyNGM1YzVkOTQ4ZDJhMzQ1ZGUyNWYyMDhjYmQ5YWY3MTA4Y2UxZTFiNjFhNTg2ZGU5OGIyIn19fQ==";
    private static final String headGradient = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA2MmRhM2QzYjhmMWZkMzUzNDNjYzI3OWZiMGZlNWNmNGE1N2I1YWJjNDMxZmJiNzhhNzNiZjJhZjY3NGYifX19";
    private static final String headPaint = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBiM2E5ZGZhYmVmYmRkOTQ5YjIxN2JiZDRmYTlhNDg2YmQwYzNmMGNhYjBkMGI5ZGZhMjRjMzMyZGQzZTM0MiJ9fX0=";
    private static final String headBlend = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU5MTg0YWYxZGU3ZTViN2M0YWQ0MTFjNTZhZjRmOTMzNjY1MzYxNTkyOWJjOTVkNzEzYjdhNDJjZmYzZmJhZCJ9fX0=";

    public static Inventory create(PlayerBrush pb) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("goPaint Menu", NamedTextColor.DARK_BLUE));
        update(inv, pb);
        return inv;
    }

    public static Inventory GenerateBrushes() {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("goPaint Brushes", NamedTextColor.DARK_BLUE));
        // FILLER
        for (int x = 0; x < 27; x++) {
            inv.setItem(
                    x,
                    Items.create(
                            Material.GRAY_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }
        int x = 0;
        for (Brush b : plugin.getBrushManager().getBrushes()) {
            if (b instanceof SphereBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headSphere,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Regular sphere brush"
                        )
                );
            } else if (b instanceof SprayBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headSpray,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Configurable random chance brush"
                        )
                );
            } else if (b instanceof SplatterBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headSplatter,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8More chance when closer\n&8to the clicked point\n&8and configurable chance"
                        )
                );
            } else if (b instanceof DiscBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headDisc,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Paints blocks in the\n&8same selected axis\n&8from the block you clicked"
                        )
                );
            } else if (b instanceof BucketBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headBucket,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Paints connected blocks\n&8with the same block type"
                        )
                );
            } else if (b instanceof AngleBrush) {
                inv.setItem(
                        x,
                        Items.createHead(headAngle, 1, "&6" + b.getName(), "\n&7Click to select\n\n" + "&8Only works on cliffs")
                );
            } else if (b instanceof OverlayBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headOverlay,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Only paints blocks\n&8that have air above it"
                        )
                );
            } else if (b instanceof UnderlayBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headUnderlay,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Only paints blocks\n&8that have no air above it"
                        )
                );
            } else if (b instanceof FractureBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headFracture,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Places blocks in cracks/fisures"
                        )
                );
            } else if (b instanceof GradientBrush) {
                inv.setItem(
                        x,
                        Items.createHead(headGradient, 1, "&6" + b.getName(), "\n&7Click to select\n\n" + "&8Creates gradients")
                );
            } else if (b instanceof PaintBrush) {
                inv.setItem(
                        x,
                        Items.createHead(
                                headPaint,
                                1,
                                "&6" + b.getName(),
                                "\n&7Click to select\n\n" + "&8Paints strokes\n&8hold shift to end"
                        )
                );
            }
            x++;
        }
        return inv;
    }

    public static void update(Inventory inv, PlayerBrush pb) {
        Brush b = pb.getBrush();

        // FILLER
        for (int x = 0; x < 54; x++) {
            inv.setItem(
                    x,
                    Items.create(
                            Material.GRAY_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // goPaint toggle
        if (pb.isEnabled()) {
            inv.setItem(
                    1,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    10,
                    Items.create(
                            Material.FEATHER,
                            1,
                            "&6goPaint Brush",
                            "&a&lEnabled\n\n&7Left click with item to export\n&7Right click to toggle"
                    )
            );
            inv.setItem(
                    19,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        } else {
            inv.setItem(
                    1,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    10,
                    Items.create(
                            Material.FEATHER,
                            1,
                            "&6goPaint Brush",
                            "&c&lDisabled\n\n&7Left click with item to export\n&7Right click to toggle"
                    )
            );
            inv.setItem(
                    19,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // Brushes + Chance
        inv.setItem(
                2,
                Items.create(
                        Material.ORANGE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );


        String clicks = "\n&7Shift click to select\n&7Click to cycle brush\n\n";
        if (b instanceof SphereBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headSphere,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof SprayBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headSpray,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof SplatterBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headSplatter,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof DiscBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headDisc,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof BucketBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headBucket,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof AngleBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headAngle,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof OverlayBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headOverlay,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof UnderlayBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headUnderlay,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof FractureBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headFracture,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof GradientBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headGradient,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        } else if (b instanceof PaintBrush) {
            inv.setItem(
                    11,
                    Items.createHead(
                            headPaint,
                            1,
                            "&6Selected Brush type",
                            clicks + plugin.getBrushManager().getBrushLore(b.getName())
                    )
            );
        }
        inv.setItem(
                20,
                Items.create(
                        Material.ORANGE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );

        // chance
        if (b instanceof SprayBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.GOLD_NUGGET,
                            1,
                            "&6Place chance: &e" + pb.getChance() + "%",
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // axis
        if (b instanceof DiscBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.COMPASS,
                            1,
                            "&6Axis: &e" + pb.getAxis(),
                            "\n&7Click to change"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }


        // thickness
        if (b instanceof OverlayBrush || b instanceof UnderlayBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.BOOK,
                            1,
                            "&6Layer Thickness: &e" + pb.getThickness(),
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // angle settings
        if (b instanceof AngleBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.DAYLIGHT_DETECTOR,
                            1,
                            "&6Angle Check Distance: &e" + pb.getAngleDistance(),
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );

            inv.setItem(
                    4,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    13,
                    Items.create(
                            Material.BLAZE_ROD,
                            1,
                            "&6Maximum Angle: &e" + pb.getAngleHeightDifference() + "°",
                            "\n&7Left click to increase\n&7Right click to decrease\n&7Shift click to change by 15"
                    )
            );
            inv.setItem(
                    22,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // fracture settings
        if (b instanceof FractureBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.DAYLIGHT_DETECTOR,
                            1,
                            "&6Fracture Check Distance: &e" + pb.getFractureDistance(),
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // angle settings
        if (b instanceof GradientBrush) {
            inv.setItem(
                    4,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    13,
                    Items.create(
                            Material.MAGMA_CREAM,
                            1,
                            "&6Mixing Strength: &e" + pb.getMixingStrength() + "%",
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    22,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        if (b instanceof SplatterBrush || b instanceof PaintBrush || b instanceof GradientBrush) {
            inv.setItem(
                    3,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    12,
                    Items.create(
                            Material.BLAZE_POWDER,
                            1,
                            "&6Falloff Strength: &e" + pb.getFalloffStrength() + "%",
                            "\n&7Left click to increase\n&7Right click to decrease"
                    )
            );
            inv.setItem(
                    21,
                    Items.create(
                            Material.WHITE_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }


        // Size
        inv.setItem(
                5,
                Items.create(
                        Material.WHITE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );
        inv.setItem(
                14,
                Items.create(
                        Material.BROWN_MUSHROOM,
                        1,
                        "&6Brush Size: &e" + pb.getSize(),
                        "\n&7Left click to increase\n&7Right click to decrease\n&7Shift click to change by 10"
                )
        );
        inv.setItem(
                23,
                Items.create(
                        Material.WHITE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );

        // Mask toggle
        if (pb.isMask()) {
            inv.setItem(
                    6,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    15,
                    Items.create(
                            Material.JACK_O_LANTERN,
                            1,
                            "&6Mask",
                            "&a&lEnabled\n\n&7Click to toggle"
                    )
            );
            inv.setItem(
                    24,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        } else {
            inv.setItem(
                    6,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    15,
                    Items.create(Material.PUMPKIN, 1, "&6Mask", "&c&lDisabled\n\n&7Click to toggle")
            );
            inv.setItem(
                    24,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // Surface Mode toggle
        if (pb.isSurfaceMode()) {
            inv.setItem(
                    7,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    16,
                    Items.create(
                            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
                            1,
                            "&6Surface Mode",
                            "&a&lEnabled\n\n&7Click to toggle"
                    )
            );
            inv.setItem(
                    25,
                    Items.create(
                            Material.LIME_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        } else {
            inv.setItem(
                    7,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
            inv.setItem(
                    16,
                    Items.create(
                            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
                            1,
                            "&6Surface Mode",
                            "&c&lDisabled\n\n&7Click to toggle"
                    )
            );
            inv.setItem(
                    25,
                    Items.create(
                            Material.RED_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }

        // Place Block
        for (int x = 37; x <= 41; x++) {
            inv.setItem(
                    x,
                    Items.create(
                            Material.YELLOW_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );
        }
        for (int x = 46; x <= 50; x++) {
            inv.setItem(
                    x,
                    Items.create(Material.BARRIER, 1, "&cEmpty Slot", "\n&7Click with a block to set")
            );
        }
        int x = 46;
        int size = pb.getBlocks().size();
        for (Material bt : pb.getBlocks()) {
            int chance = (int) (double) (100 / size);
            if (chance > 64) {
                inv.setItem(
                        x,
                        Items.create(
                                bt,
                                1,
                                "&aSlot " + (x - 45) + " &7" + (int) (double) (100 / size) + "%",
                                "\n&7Left click with a block to change\n&7Right click to clear"
                        )
                );
            } else {
                inv.setItem(
                        x,
                        Items.create(
                                bt,
                                (int) (double) (100 / size),
                                "&aSlot " + (x - 45) + " &7" + (int) (double) (100 / size) + "%",
                                "\n&7Left click with a block to change\n&7Right click to clear"
                        )
                );
            }
            x++;
        }

        // Mask Block
        inv.setItem(
                43,
                Items.create(
                        Material.YELLOW_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );
        inv.setItem(
                52,
                Items.create(pb.getMask(), 1, "&6Current Mask", "\n&7Left click with a block to change")
        );
    }

}
