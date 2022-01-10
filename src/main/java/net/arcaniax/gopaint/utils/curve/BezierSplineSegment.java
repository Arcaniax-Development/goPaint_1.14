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
package net.arcaniax.gopaint.utils.curve;

import org.bukkit.Location;

public class BezierSplineSegment {

    private final double[] lengths;
    private Location p0, p1, p2, p3;
    private float a, b, c;
    private Double xFlat, yFlat, zFlat;
    private Location r;
    private double curveLength;

    public BezierSplineSegment(Location p0, Location p3) {
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

    public void setX(Double xflat2) {
        p0.setX(xflat2);
        p1.setX(xflat2);
        p2.setX(xflat2);
        p3.setX(xflat2);
        xFlat = xflat2;
    }

    public void setY(Double yflat2) {
        p0.setY(yflat2);
        p1.setY(yflat2);
        p2.setY(yflat2);
        p3.setY(yflat2);
        yFlat = yflat2;
    }

    public void setZ(Double zflat2) {
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

    public double getdXdT(double t) {
        assert (t >= 0);
        assert (t <= 1);
        return 3 * (1 - t) * (1 - t) * (p1.getX() - p0.getX()) + 6 * (1 - t) * t
                * (p2.getX() - p1.getX()) + 3 * t * t * (p3.getX() - p2.getX());
    }

    public double getdYdT(double t) {
        assert (t <= 1);
        return 3 * (1 - t) * (1 - t) * (p1.getY() - p0.getY()) + 6 * (1 - t) * t
                * (p2.getY() - p1.getY()) + 3 * t * t * (p3.getY() - p2.getY());
    }

    public double getdZdT(double t) {
        assert (t >= 0);
        assert (t <= 1);
        return 3 * (1 - t) * (1 - t) * (p1.getZ() - p0.getZ()) + 6 * (1 - t) * t
                * (p2.getZ() - p1.getZ()) + 3 * t * t * (p3.getZ() - p2.getZ());
    }

    public double getdTdS(double t) {
        double dZdT = getdZdT(t);
        double dXdT = getdXdT(t);
        double dYdT = getdYdT(t);
        return 1 / Math.sqrt(dZdT * dZdT + dXdT * dXdT + dYdT * dYdT);
    }

    public double getHAngle(double t) {
        // Positive x is 0, positive z is pi/2, negative x is pi, negative z is
        // 3*pi/2
        double dZdT = getdZdT(t);
        double dXdT = getdXdT(t);
        if (dXdT == 0) {
            if (dZdT < 0) {
                return Math.PI / 2;
            } else {
                return -Math.PI / 2;
            }
        }

        if (dXdT < 0) {
            return Math.PI + Math.atan(dZdT / dXdT);
        }
        return Math.atan(dZdT / dXdT);
    }

    public double getT(double d) {
        assert (d >= 0);
        assert (d <= curveLength);
        if (d == 0) {
            return 0;
        }
        if (d == curveLength) {
            return 1;
        }
        int i = 0;
        for (i = 0; i < 20; i++) {
            if (d == lengths[i]) {
                return i / 19;
            }
            if (d < lengths[i]) {
                break;
            }
        }
        return (i + (d - lengths[i - 1]) / (lengths[i] - lengths[i - 1])) / 20;
    }

    public Location getPoint(double f) {
        Location result = new Location(p0.getWorld(), 0, 0, 0);
        if (xFlat == null) {
            result.setX((Math.pow(1 - f, 3) * p0.getX())
                    + (3 * Math.pow(1 - f, 2) * f * p1.getX())
                    + (3 * (1 - f) * f * f * p2.getX()) + (Math.pow(f, 3) * p3.getX()));
        } else {
            result.setX(xFlat);
        }
        if (yFlat == null) {
            result.setY((Math.pow(1 - f, 3) * p0.getY())
                    + (3 * Math.pow(1 - f, 2) * f * p1.getY())
                    + (3 * (1 - f) * f * f * p2.getY()) + (Math.pow(f, 3) * p3.getY()));
        } else {
            result.setY(yFlat);
        }
        if (zFlat == null) {
            result.setZ((Math.pow(1 - f, 3) * p0.getZ())
                    + (3 * Math.pow(1 - f, 2) * f * p1.getZ())
                    + (3 * (1 - f) * f * f * p2.getZ()) + (Math.pow(f, 3) * p3.getZ()));
        } else {
            result.setZ(zFlat);
        }
        return result;
    }

    public double getLinearLength() {
        return p0.distance(p3);
    }

    public Location getP0() {
        return p0;
    }

    public void setP0(Location p0) {
        this.p0 = p0;
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

    public void setP3(Location p3) {
        this.p3 = p3;
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

    public void setR(Location r) {
        this.r = r;
    }

}

