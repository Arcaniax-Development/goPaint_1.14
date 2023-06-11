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
package dev.themeinerlp.bettergopaint.objects.brush;

import dev.themeinerlp.bettergopaint.objects.player.ExportedPlayerBrush;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Brush {

    public abstract void paint(Location loc, Player p);

    public abstract void paint(Location loc, Player p, ExportedPlayerBrush epb);

    public abstract String getName();

}
