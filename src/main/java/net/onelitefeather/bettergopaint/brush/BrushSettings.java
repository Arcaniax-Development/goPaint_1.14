package net.onelitefeather.bettergopaint.brush;

import net.onelitefeather.bettergopaint.objects.brush.Brush;
import org.bukkit.Axis;
import org.bukkit.Material;

import java.util.List;
import java.util.Random;

public interface BrushSettings {

    Axis getAxis();

    Brush getBrush();

    List<Material> getBlocks();

    Material getMask();

    boolean isMask();

    boolean isSurfaceMode();

    double getAngleHeightDifference();

    int getAngleDistance();

    int getChance();

    int getFalloffStrength();

    int getFractureDistance();

    int getMixingStrength();

    int getSize();

    int getThickness();

    Material getRandomBlock();

    Random getRandom();

}
