/*
 *                             _____      _       _
 *                            |  __ \    (_)     | |
 *                  __ _  ___ | |__) |_ _ _ _ __ | |_
 *                 / _` |/ _ \|  ___/ _` | | '_ \| __|
 *                | (_| | (_) | |  | (_| | | | | | |_
 *                 \__, |\___/|_|   \__,_|_|_| |_|\__|
 *                  __/ |
 *                 |___/
 *
 *    goPaint is designed to simplify painting inside of Minecraft.
 *                     Copyright (C) 2021 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.objects.other;

import net.arcaniax.gopaint.GoPaintPlugin;

public class NmsManager {

    String version;

    public NmsManager() {
        String a = GoPaintPlugin.getGoPaintPlugin().getServer().getClass().getPackage().getName();
        version = a.substring(a.lastIndexOf('.') + 1);
    }

    public boolean isVersion(String v) {
        return version.equalsIgnoreCase(v);
    }

    public boolean isVersion(int gameV, int releaseV, int subReleaseV) {
        return version.equalsIgnoreCase("v" + gameV + "_" + releaseV + "_R" + subReleaseV);
    }

    public boolean isAtLeastVersion(int gameV, int releaseV, int subReleaseV) {
        String[] split = version.split("_");
        int game = Integer.parseInt(split[0].toLowerCase().replace("v", ""));
        int release = Integer.parseInt(split[1]);
        int subRelease = Integer.parseInt(split[2].toLowerCase().replace("r", ""));

        if (game > gameV) {
            return true;
        } else if (game < gameV) {
            return false;
        } else {
            if (release > releaseV) {
                return true;
            } else if (release < releaseV) {
                return false;
            } else {
                return subRelease >= subReleaseV;
            }
        }
    }

}
