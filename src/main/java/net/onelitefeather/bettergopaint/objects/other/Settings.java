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
package net.onelitefeather.bettergopaint.objects.other;

import com.fastasyncworldedit.core.configuration.Config;
import net.onelitefeather.bettergopaint.BetterGoPaint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Settings extends Config {

    @Ignore
    private static final Settings settings = new Settings();

    public void reload(BetterGoPaint plugin, File file) {
        if (!load(file)) {
            try {
                if (!file.createNewFile()) {
                    plugin.getComponentLogger().error("Failed to create file {}", file.getName());
                }
            } catch (IOException e) {
                plugin.getComponentLogger().error("Failed to create file {}", file.getName(), e);
            }
        }
        save(file);
    }

    @Create
    public GENERIC GENERIC;

    @Create
    public THICKNESS THICKNESS;

    @Create
    public ANGLE ANGLE;

    @Create
    public FRACTURE FRACTURE;

    @Comment("This is related to generic settings")
    public static class GENERIC {

        @Comment("Max size of the brush")
        public int MAX_SIZE = 100;
        @Comment("Default size for each player of the brush")
        public int DEFAULT_SIZE = 10;
        @Comment("Default chance for some brushes")
        public int DEFAULT_CHANCE = 50;
        @Comment("Prefix of the plugin")
        public String PREFIX = "<aqua>BetterGoPaint > </aqua>";
        @Comment("World there are disabled to used brushes")
        public List<String> DISABLED_WORLDS = new ArrayList<>();

        @Comment("Enables BetterGoPaint usage by default")
        public boolean ENABLED_BY_DEFAULT = true;

        @Comment("Enables mask usage by default")
        public boolean MASK_ENABLED = true;

        @Comment({"direct surface mode usage by default", "Possible values: DIRECT, DISABLED, RELATIVE"})
        public SurfaceMode SURFACE_MODE = SurfaceMode.DIRECT;

    }

    @Comment("This is related to thickness settings")
    public static class THICKNESS {

        @Comment("Default thickness for some brushes")
        public int DEFAULT_THICKNESS = 1;

        @Comment("Maximum thickness for some brushes")
        public int MAX_THICKNESS = 5;

    }

    @Comment("This is related to angle settings")
    public static class ANGLE {

        @Comment("Default angle distance for some brushes")
        public int DEFAULT_ANGLE_DISTANCE = 2;
        @Comment("Maximum angle distance for some brushes")
        public int MAX_ANGLE_DISTANCE = 5;

        @Comment("Minimum angle height difference for some brushes")
        public double MIN_ANGLE_HEIGHT_DIFFERENCE = 10.0;

        @Comment("Default angle height difference for some brushes")
        public double DEFAULT_ANGLE_HEIGHT_DIFFERENCE = 40.0;

        public double MAX_ANGLE_HEIGHT_DIFFERENCE = 85.0;

    }

    @Comment("This is related to fracture settings")
    public static class FRACTURE {

        @Comment("Default fracture for some brushes")
        public int DEFAULT_FRACTURE_DISTANCE = 2;
        @Comment("Maximum fracture for some brushes")
        public int MAX_FRACTURE_DISTANCE = 5;

    }

    public static Settings settings() {
        return settings;
    }

}
