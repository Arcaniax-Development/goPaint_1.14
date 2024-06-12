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
package net.onelitefeather.bettergopaint.utils.curve;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BezierSplineSegment {

    private final double[] lengths;
    private final Location p0, p3;
    private Location p1, p2;
    private float a, b, c;
    private Double xFlat, yFlat, zFlat;
    private final Location r;
    private double curveLength;

    public BezierSplineSegment(@NotNull Location p0, @NotNull Location p3) {
        this.p0 = p0;
        this.p3 = p3;
        lengths = new double[20];
        p1 = new Location(p0.getWorld(), 0, 0, 0);
        p2 = new Location(p0.getWorld(), 0, 0, 0);
        r = new Location(p0.getWorld(), 0, 0, 0);
    }

    public double getCurveLength() {
        return curveLength;
    }

    public void setX(double xflat2) {
        p0.setX(xflat2);
        p1.setX(xflat2);
        p2.setX(xflat2);
        p3.setX(xflat2);
        xFlat = xflat2;
    }

    public void setY(double yflat2) {
        p0.setY(yflat2);
        p1.setY(yflat2);
        p2.setY(yflat2);
        p3.setY(yflat2);
        yFlat = yflat2;
    }

    public void setZ(double zflat2) {
        p0.setZ(zflat2);
        p1.setZ(zflat2);
        p2.setZ(zflat2);
        p3.setZ(zflat2);
        zFlat = zflat2;
    }

    public void calculateCurveLength() {
        Location current = p0.clone();
        double step = 0.05;
        lengths[0] = 0;
        Location temp;
        for (int i = 1; i < 20; i++) {
            temp = getPoint(i * step);
            lengths[i] = lengths[i - 1] + temp.distance(current);
            current = temp;
        }
        curveLength = lengths[19];
    }

    public Location getPoint(double f) {
        Location result = new Location(p0.getWorld(), 0, 0, 0);
        result.setX(Objects.requireNonNullElseGet(xFlat, () -> (Math.pow(1 - f, 3) * p0.getX())
                + (3 * Math.pow(1 - f, 2) * f * p1.getX())
                + (3 * (1 - f) * f * f * p2.getX()) + (Math.pow(f, 3) * p3.getX())));
        result.setY(Objects.requireNonNullElseGet(yFlat, () -> (Math.pow(1 - f, 3) * p0.getY())
                + (3 * Math.pow(1 - f, 2) * f * p1.getY())
                + (3 * (1 - f) * f * f * p2.getY()) + (Math.pow(f, 3) * p3.getY())));
        result.setZ(Objects.requireNonNullElseGet(zFlat, () -> (Math.pow(1 - f, 3) * p0.getZ())
                + (3 * Math.pow(1 - f, 2) * f * p1.getZ())
                + (3 * (1 - f) * f * f * p2.getZ()) + (Math.pow(f, 3) * p3.getZ())));
        return result;
    }

    public Location getP0() {
        return p0;
    }

    public Location getP1() {
        return p1;
    }

    public void setP1(Location p1) {
        this.p1 = p1;
    }

    public Location getP2() {
        return p2;
    }

    public void setP2(Location p2) {
        this.p2 = p2;
    }

    public Location getP3() {
        return p3;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public Location getR() {
        return r;
    }

}

