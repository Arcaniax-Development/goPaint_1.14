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
package net.onelitefeather.bettergopaint.brush;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.onelitefeather.bettergopaint.objects.brush.AngleBrush;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import net.onelitefeather.bettergopaint.objects.brush.DiscBrush;
import net.onelitefeather.bettergopaint.objects.brush.FractureBrush;
import net.onelitefeather.bettergopaint.objects.brush.GradientBrush;
import net.onelitefeather.bettergopaint.objects.brush.OverlayBrush;
import net.onelitefeather.bettergopaint.objects.brush.SplatterBrush;
import net.onelitefeather.bettergopaint.objects.brush.SprayBrush;
import net.onelitefeather.bettergopaint.objects.brush.UnderlayBrush;
import net.onelitefeather.bettergopaint.objects.other.Settings;
import net.onelitefeather.bettergopaint.objects.other.SurfaceMode;
import net.onelitefeather.bettergopaint.utils.GUI;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class PlayerBrush implements BrushSettings {

    private final @NotNull PlayerBrushManager brushManager;
    private final @NotNull Random random = new SecureRandom();

    private boolean maskEnabled;
    private boolean enabled;
    private int size;
    private int chance;
    private int thickness;
    private int fractureDistance;
    private int angleDistance;
    private int falloffStrength;
    private int mixingStrength;
    private double angleHeightDifference;
    private @NotNull Axis axis;
    private @NotNull SurfaceMode surfaceMode;

    private @NotNull Brush brush;
    private @NotNull Material mask;
    private final @NotNull List<Material> blocks = new ArrayList<>();

    private final Inventory gui;

    public PlayerBrush(PlayerBrushManager brushManager) {
        this.brushManager = brushManager;

        surfaceMode = Settings.settings().GENERIC.SURFACE_MODE;
        maskEnabled = Settings.settings().GENERIC.MASK_ENABLED;
        enabled = Settings.settings().GENERIC.ENABLED_BY_DEFAULT;
        chance = Settings.settings().GENERIC.DEFAULT_CHANCE;
        thickness = Settings.settings().THICKNESS.DEFAULT_THICKNESS;
        fractureDistance = Settings.settings().FRACTURE.DEFAULT_FRACTURE_DISTANCE;
        angleDistance = Settings.settings().ANGLE.DEFAULT_ANGLE_DISTANCE;
        angleHeightDifference = Settings.settings().ANGLE.DEFAULT_ANGLE_HEIGHT_DIFFERENCE;
        falloffStrength = Settings.settings().GENERIC.DEFAULT_FALLOFF_STRENGTH;
        mixingStrength = Settings.settings().GENERIC.DEFAULT_MIXING_STRENGTH;
        axis = Settings.settings().GENERIC.DEFAULT_AXIS;
        size = Settings.settings().GENERIC.DEFAULT_SIZE;
        mask = Settings.settings().GENERIC.DEFAULT_MASK;
        brush = brushManager.cycleForward(null);
        blocks.add(Material.STONE);
        gui = GUI.create(this);
    }

    public @NotNull Material randomBlock() {
        return blocks().get(random.nextInt(blocks().size()));
    }

    @Override
    public @NotNull Brush brush() {
        return brush;
    }

    public void setBrush(@NotNull Brush brush) {
        this.brush = brush;
    }

    @Override
    public @NotNull Random random() {
        return random;
    }

    @Override
    public int falloffStrength() {
        return falloffStrength;
    }

    @Override
    public int mixingStrength() {
        return mixingStrength;
    }

    @Override
    public double angleHeightDifference() {
        return this.angleHeightDifference;
    }

    @Override
    public int angleDistance() {
        return this.angleDistance;
    }

    @Override
    public int fractureDistance() {
        return this.fractureDistance;
    }

    @Override
    public @NotNull Material mask() {
        return mask;
    }

    @Override
    public @NotNull List<Material> blocks() {
        return blocks;
    }

    @Override
    public int size() {
        return size;
    }

    public boolean enabled() {
        return enabled;
    }

    @Override
    public boolean maskEnabled() {
        return maskEnabled;
    }

    @Override
    public int chance() {
        return chance;
    }

    @Override
    public SurfaceMode surfaceMode() {
        return surfaceMode;
    }

    @Override
    public int thickness() {
        return thickness;
    }

    @Override
    public @NotNull Axis axis() {
        return axis;
    }

    public void updateInventory() {
        GUI.update(gui, this);
    }

    public void increaseFalloffStrength() {
        if (falloffStrength <= 90) {
            falloffStrength += 10;
        }
        updateInventory();
    }

    public void decreaseFalloffStrength() {
        if (falloffStrength >= 10) {
            falloffStrength -= 10;
        }
        updateInventory();
    }

    public void increaseMixingStrength() {
        if (mixingStrength <= 90) {
            mixingStrength += 10;
        }
        updateInventory();
    }

    public void decreaseMixingStrength() {
        if (mixingStrength >= 10) {
            mixingStrength -= 10;
        }
        updateInventory();
    }

    public void setMask(Material bt) {
        mask = bt;
        updateInventory();
    }

    public void addBlock(Material bt, int slot) {
        if (blocks.size() >= slot) {
            blocks.set(slot - 1, bt);
        } else {
            blocks.add(bt);
        }
        updateInventory();
    }

    public void removeBlock(int slot) {
        if (blocks.size() >= slot) {
            blocks.remove(slot - 1);
            updateInventory();
        }
    }

    public void cycleBrushForward() {
        brush = brushManager.cycleForward(brush);
        updateInventory();
    }

    public void cycleBrushBackwards() {
        brush = brushManager.cycleBack(brush);
        updateInventory();
    }

    public void setSize(int size) {
        if (size <= Settings.settings().GENERIC.MAX_SIZE && size > 0) {
            this.size = size;
        } else if (size > Settings.settings().GENERIC.MAX_SIZE) {
            this.size = Settings.settings().GENERIC.MAX_SIZE;
        } else {
            this.size = 1;
        }
        updateInventory();
    }

    public Inventory getInventory() {
        return gui;
    }

    public void increaseBrushSize(boolean x10) {
        if (x10) {
            if (size + 10 <= Settings.settings().GENERIC.MAX_SIZE) {
                size += 10;
            } else {
                size = Settings.settings().GENERIC.MAX_SIZE;
            }
        } else {
            if (size < Settings.settings().GENERIC.MAX_SIZE) {
                size += 1;
            }
        }
        updateInventory();
    }

    public void decreaseBrushSize(boolean x10) {
        if (x10) {
            if (size - 10 >= 1) {
                size -= 10;
            } else {
                size = 1;
            }
        } else {
            if (size > 1) {
                size -= 1;
            }
        }
        updateInventory();
    }

    public void toggle() {
        enabled = !enabled;
        updateInventory();
    }

    public void increaseChance() {
        if (chance < 90) {
            chance += 10;
        }
        updateInventory();
    }

    public void decreaseChance() {
        if (chance > 10) {
            chance -= 10;
        }
        updateInventory();
    }

    public void increaseThickness() {
        if (thickness < Settings.settings().THICKNESS.MAX_THICKNESS) {
            thickness += 1;
        }
        updateInventory();
    }

    public void decreaseThickness() {
        if (thickness > 1) {
            thickness -= 1;
        }
        updateInventory();
    }

    public void increaseAngleDistance() {
        if (angleDistance < Settings.settings().ANGLE.MAX_ANGLE_DISTANCE) {
            angleDistance += 1;
        }
        updateInventory();
    }

    public void decreaseAngleDistance() {
        if (angleDistance > 1) {
            angleDistance -= 1;
        }
        updateInventory();
    }

    public void increaseFractureDistance() {
        if (this.fractureDistance < Settings.settings().FRACTURE.MAX_FRACTURE_DISTANCE) {
            this.fractureDistance += 1;
        }
        updateInventory();
    }

    public void decreaseFractureDistance() {
        if (this.fractureDistance > 1) {
            this.fractureDistance -= 1;
        }
        updateInventory();
    }

    public void increaseAngleHeightDifference(boolean d15) {
        if (d15) {
            angleHeightDifference += 15.0;
        } else {
            angleHeightDifference += 5.0;
        }
        if (angleHeightDifference > Settings.settings().ANGLE.MAX_ANGLE_HEIGHT_DIFFERENCE) {
            angleHeightDifference = Settings.settings().ANGLE.MAX_ANGLE_HEIGHT_DIFFERENCE;
        }
        updateInventory();
    }

    public void decreaseAngleHeightDifference(boolean d15) {
        if (d15) {
            angleHeightDifference -= 15.0;
        } else {
            angleHeightDifference -= 5.0;
        }
        if (angleHeightDifference < Settings.settings().ANGLE.MIN_ANGLE_HEIGHT_DIFFERENCE) {
            angleHeightDifference = Settings.settings().ANGLE.MIN_ANGLE_HEIGHT_DIFFERENCE;
        }
        updateInventory();
    }

    public void toggleMask() {
        maskEnabled = !maskEnabled;
        updateInventory();
    }

    public void cycleSurfaceMode() {
        surfaceMode = switch (surfaceMode) {
            case DIRECT -> SurfaceMode.RELATIVE;
            case RELATIVE -> SurfaceMode.DISABLED;
            case DISABLED -> SurfaceMode.DIRECT;
        };
        updateInventory();
    }

    public void cycleAxis() {
        axis = switch (axis) {
            case X -> Axis.Y;
            case Y -> Axis.Z;
            case Z -> Axis.X;
        };
        updateInventory();
    }

    public void export(ItemStack itemStack) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Size: " + size);
        if (brush() instanceof SprayBrush) {
            lore.add("Chance: " + chance() + "%");
        } else if (brush() instanceof OverlayBrush || brush() instanceof UnderlayBrush) {
            lore.add("Thickness: " + thickness());
        } else if (brush() instanceof DiscBrush) {
            lore.add("Axis: " + axis().name());
        } else if (brush() instanceof AngleBrush) {
            lore.add("AngleDistance: " + angleDistance());
            lore.add("AngleHeightDifference: " + angleHeightDifference());
        } else if (brush() instanceof SplatterBrush) {
            lore.add("Falloff: " + falloffStrength());
        } else if (brush() instanceof GradientBrush) {
            lore.add("Mixing: " + mixingStrength());
            lore.add("Falloff: " + falloffStrength());
        } else if (brush() instanceof FractureBrush) {
            lore.add("FractureDistance: " + fractureDistance());
        }
        lore.add("Blocks: " + (blocks().isEmpty() ? "none" : blocks().stream()
                .map(Material::getKey)
                .map(NamespacedKey::asMinimalString)
                .collect(Collectors.joining(", "))));

        if (maskEnabled()) {
            lore.add("Mask: " + mask().getKey().asMinimalString());
        }
        if (!surfaceMode().equals(SurfaceMode.DISABLED)) {
            lore.add("Surface Mode: " + surfaceMode.getName());
        }

        itemStack.editMeta(itemMeta -> {
            itemMeta.displayName(Component.text(" ♦ " + brush().getName() + " ♦ ", NamedTextColor.AQUA)
                    .style(Style.style(TextDecoration.ITALIC.withState(false))));
            itemMeta.lore(lore.stream().map(string -> Component.text(string).style(Style
                    .style(TextDecoration.ITALIC.withState(false))
                    .color(NamedTextColor.DARK_GRAY))).toList());
            itemMeta.addEnchant(Enchantment.INFINITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }

}
