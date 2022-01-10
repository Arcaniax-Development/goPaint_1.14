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
 *                     Copyright (C) 2022 Arcaniax
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

import java.util.ArrayList;
import java.util.List;

public class Settings {

    int maxSize;
    int defaultSize;
    int maxHistory;
    int defaultChance;
    int defaultThickness;
    int defaultAngleDistance;
    int maxAngleDistance;
    int defaultFractureDistance;
    int maxFractureDistance;
    double minAngleHeightDifference;
    double defaultAngleHeightDifference;
    double maxAngleHeightDifference;
    int maxThickness;
    String prefix;
    List<String> disabledWorldNames;
    boolean enabledByDefault;
    boolean maskEnabled;
    boolean boundingBoxEnabled;
    boolean surfaceModeEnabled;

    public Settings() {
        defaultSize = 10;
        maxSize = 100;
        defaultChance = 50;
        defaultThickness = 1;
        maxThickness = 5;
        defaultAngleDistance = 2;
        defaultFractureDistance = 2;
        maxFractureDistance = 5;
        maxAngleDistance = 5;
        minAngleHeightDifference = 10.0;
        defaultAngleHeightDifference = 40.0;
        maxAngleHeightDifference = 85.0;
        maxHistory = 100;
        prefix = "§bgoPaint > ";
        disabledWorldNames = new ArrayList<>();
        enabledByDefault = false;
        maskEnabled = true;
        surfaceModeEnabled = false;
    }

    public void loadConfig() {
        maxSize = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("size.max");
        defaultSize = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("size.default");
        int chance = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("chance.default");
        if (chance > 0 && chance < 100 && chance % 10 == 0) {
            defaultChance = chance;
        }
        defaultThickness = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("thickness.default");

        defaultAngleDistance = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("angleDistance.default");

        maxAngleDistance = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("angleDistance.max");

        defaultFractureDistance = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("fractureDistance.default");

        maxFractureDistance = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("fractureDistance.max");

        double minAngle = GoPaintPlugin.getGoPaintPlugin().getConfig().getDouble("angleHeightDifference.min");
        if (minAngle > 0 && minAngle < 90 && minAngle % 5 == 0) {
            minAngleHeightDifference = minAngle;
        }

        double defaultAngle = GoPaintPlugin.getGoPaintPlugin().getConfig().getDouble("angleHeightDifference.default");
        if (defaultAngle > 0 && defaultAngle < 90 && defaultAngle % 5 == 0) {
            defaultAngleHeightDifference = defaultAngle;
        }
        double maxAngle = GoPaintPlugin.getGoPaintPlugin().getConfig().getDouble("angleHeightDifference.max");
        if (maxAngle > 0 && maxAngle < 90 && maxAngle % 5 == 0) {
            maxAngleHeightDifference = maxAngle;
        }

        maxThickness = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("thickness.max");
        maxHistory = GoPaintPlugin.getGoPaintPlugin().getConfig().getInt("history.max");

        disabledWorldNames = GoPaintPlugin.getGoPaintPlugin().getConfig().getStringList("disabledWorlds");
        enabledByDefault = GoPaintPlugin.getGoPaintPlugin().getConfig().getBoolean("toggles.enabledByDefault");
        maskEnabled = GoPaintPlugin.getGoPaintPlugin().getConfig().getBoolean("toggles.maskEnabled");
        boundingBoxEnabled = GoPaintPlugin.getGoPaintPlugin().getConfig().getBoolean("toggles.boundingBoxEnabled");
        surfaceModeEnabled = GoPaintPlugin.getGoPaintPlugin().getConfig().getBoolean("toggles.surfaceModeEnabled");
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getDefaultChance() {
        return defaultChance;
    }

    public int getDefaultThickness() {
        return defaultThickness;
    }

    public double getDefaultAngleHeightDifference() {
        return defaultAngleHeightDifference;
    }

    public int getDefaultAngleDistance() {
        return defaultAngleDistance;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMaxThickness() {
        return maxThickness;
    }

    public double getMinAngleHeightDifference() {
        return minAngleHeightDifference;
    }

    public double getMaxAngleHeightDifference() {
        return maxAngleHeightDifference;
    }

    public int getMaxAngleDistance() {
        return maxAngleDistance;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getMaxHistory() {
        return maxHistory;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorldNames;
    }

    public boolean isEnabledDefault() {
        return enabledByDefault;
    }

    public boolean isMaskEnabledDefault() {
        return maskEnabled;
    }

    public boolean isSurfaceModeEnabledDefault() {
        return surfaceModeEnabled;
    }

    public int getDefaultFractureDistance() {
        return this.defaultFractureDistance;
    }

    public int getMaxFractureDistance() {
        return this.maxFractureDistance;
    }

}
