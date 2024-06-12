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

import com.google.common.collect.ImmutableList;
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
import net.onelitefeather.bettergopaint.objects.brush.UnderlayBrush;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerBrushManager {

    private final @NotNull HashMap<UUID, PlayerBrush> playerBrushes = new HashMap<>();
    private final @NotNull List<Brush> brushes = ImmutableList.of(
            new SphereBrush(),
            new SprayBrush(),
            new SplatterBrush(),
            new DiscBrush(),
            new BucketBrush(),
            new AngleBrush(),
            new OverlayBrush(),
            new UnderlayBrush(),
            new FractureBrush(),
            new GradientBrush(),
            new PaintBrush()
    );

    public PlayerBrush getBrush(@NotNull Player player) {
        return playerBrushes.computeIfAbsent(player.getUniqueId(), ignored -> new PlayerBrush(this));
    }

    public String getBrushLore(@NotNull Brush brush) {
        return brushes.stream().map(current -> {
            if (current.equals(brush)) {
                return "&e" + current.getName() + "\n";
            } else {
                return "&8" + current.getName() + "\n";
            }
        }).collect(Collectors.joining());
    }

    public @NotNull Optional<Brush> getBrushHandler(String name) {
        return brushes.stream()
                .filter(brush -> brush.getName().contains(name))
                .findAny();
    }

    public @NotNull List<Brush> getBrushes() {
        return brushes;
    }

    public void removeBrush(@NotNull Player player) {
        playerBrushes.remove(player.getUniqueId());
    }

    public Brush cycleForward(@Nullable Brush brush) {
        if (brush == null) {
            return brushes.getFirst();
        }
        int next = brushes.indexOf(brush) + 1;
        if (next < brushes.size()) {
            return brushes.get(next);
        }
        return brushes.getFirst();
    }

    public Brush cycleBack(@Nullable Brush brush) {
        if (brush == null) {
            return brushes.getFirst();
        }
        int back = brushes.indexOf(brush) - 1;
        if (back >= 0) {
            return brushes.get(back);
        }
        return brushes.getLast();
    }

}
