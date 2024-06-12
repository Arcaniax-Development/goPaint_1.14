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

import net.kyori.adventure.text.TextComponent;
import net.onelitefeather.bettergopaint.objects.brush.Brush;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public interface BrushSettings {

    Axis axis();

    Brush brush();

    List<Material> blocks();

    Material mask();

    boolean enabled();

    boolean maskEnabled();

    boolean surfaceMode();

    double angleHeightDifference();

    int angleDistance();

    int chance();

    int falloffStrength();

    int fractureDistance();

    int mixingStrength();

    int size();

    int thickness();

    @NotNull
    Material randomBlock();

    @NotNull
    Random random();

    @Deprecated(forRemoval = true)
    static BrushSettings parse(@NotNull Optional<Brush> brush, @NotNull ItemMeta itemMeta) {
        return brush.map(ExportedPlayerBrush::builder).map(builder -> {
                    Optional.ofNullable(itemMeta.lore()).ifPresent(components -> components.stream()
                            .filter(component -> component instanceof TextComponent)
                            .map(component -> (TextComponent) component)
                            .map(TextComponent::content)
                            .forEach(string -> {
                                if (string.startsWith("Size: ")) {
                                    builder.size(Integer.parseInt(string.replace("Size: ", "")));
                                } else if (string.startsWith("Chance: ")) {
                                    builder.chance(Integer.parseInt(string.replace("Chance: ", "").replace("%", "")));
                                } else if (string.startsWith("Thickness: ")) {
                                    builder.thickness(Integer.parseInt(string.replace("Thickness: ", "")));
                                } else if (string.startsWith("Axis: ")) {
                                    builder.axis(Axis.valueOf(string.replace("Axis: ", "").toUpperCase()));
                                } else if (string.startsWith("FractureDistance: ")) {
                                    builder.fractureDistance(Integer.parseInt(string.replace("FractureDistance: ", "")));
                                } else if (string.startsWith("AngleDistance: ")) {
                                    builder.angleDistance(Integer.parseInt(string.replace("AngleDistance: ", "")));
                                } else if (string.startsWith("AngleHeightDifference: ")) {
                                    builder.angleHeightDifference(Double.parseDouble(
                                            string.replace("AngleHeightDifference: ", "")
                                    ));
                                } else if (string.startsWith("Mixing: ")) {
                                    builder.mixingStrength(Integer.parseInt(string.replace("Mixing: ", "")));
                                } else if (string.startsWith("Falloff: ")) {
                                    builder.falloffStrength(Integer.parseInt(string.replace("Falloff: ", "")));
                                } else if (string.startsWith("Blocks: ")) {
                                    builder.blocks(Arrays.stream(string.replace("Blocks: ", "").split(", "))
                                            .map(Material::matchMaterial)
                                            .filter(Objects::nonNull)
                                            .toList());
                                } else if (string.startsWith("Mask: ")) {
                                    builder.mask(Material.matchMaterial(string.replace("Mask: ", "")));
                                } else if (string.startsWith("Surface Mode")) {
                                    builder.surfaceMode(true);
                                }
                            }));
                    return builder;
                })
                .map(ExportedPlayerBrush.Builder::build)
                .orElse(null);
    }

}
