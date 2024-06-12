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

import java.util.LinkedList;

public class BezierSpline {

    private final LinkedList<Location> knotsList;
    private Location[] knots;
    private BezierSplineSegment[] segments;
    private double length;

    public BezierSpline(LinkedList<Location> knotsList) {
        this.knotsList = knotsList;
        recalculate();
    }

    private void recalculate() {
        knots = knotsList.toArray(new Location[0]);
        segments = new BezierSplineSegment[knots.length - 1];
        for (int i = 0; i < knots.length - 1; i++) {
            segments[i] = new BezierSplineSegment(knots[i], knots[i + 1]);
        }
        calculateControlPoints();
        calculateLength();
    }

    public double getCurveLength() {
        return length;
    }

    public void calculateLength() {
        length = 0;
        for (BezierSplineSegment segment : segments) {
            segment.calculateCurveLength();
            length += segment.getCurveLength();
        }
    }

    public Location getPoint(double point) {
        if (point >= segments.length) {
            return getPoint(segments.length - 1, 1);
        } else {
            return getPoint((int) Math.floor(point), point - Math.floor(point));
        }
    }

    public Location getPoint(int n, double f) {
        assert (n < segments.length);
        assert (0 <= f && f <= 1);
        BezierSplineSegment segment = segments[n];
        return segment.getPoint(f);
    }

    public void calculateControlPoints() {
        /*
         * Do not touch.
         */
        if (segments == null) {
            return;
        }

        if (segments.length == 0) {
            return;
        }

        Double xflat, yflat, zflat;
        xflat = knots[0].getX();
        yflat = knots[0].getY();
        zflat = knots[0].getZ();
        for (Location l : knots) {
            if (l.getBlockX() != xflat) {
                xflat = null;
                break;
            }
        }
        for (Location l : knots) {
            if (l.getBlockY() != yflat) {
                yflat = null;
                break;
            }
        }
        for (Location l : knots) {
            if (l.getBlockZ() != zflat) {
                zflat = null;
                break;
            }
        }

        if (segments.length == 1) {
            Location midpoint = new Location(segments[0].getP0().getWorld(), 0, 0, 0);
            midpoint
                    .setX((segments[0].getP0().getX() + segments[0].getP3().getX()) / 2);
            midpoint
                    .setY((segments[0].getP0().getY() + segments[0].getP3().getY()) / 2);
            midpoint
                    .setZ((segments[0].getP0().getZ() + segments[0].getP3().getZ()) / 2);
            segments[0].setP1(midpoint);
            segments[0].setP2(midpoint.clone());
        } else {
            segments[0].setA(0);
            segments[0].setB(2);
            segments[0].setC(1);
            segments[0].getR().setX(knots[0].getX() + 2 * knots[1].getX());
            segments[0].getR().setY(knots[0].getY() + 2 * knots[1].getY());
            segments[0].getR().setZ(knots[0].getZ() + 2 * knots[1].getZ());
            int n = knots.length - 1;
            int i;
            float m;

            for (i = 1; i < n - 1; i++) {
                segments[i].setA(1);
                segments[i].setB(4);
                segments[i].setC(1);
                segments[i].getR().setX(4 * knots[i].getX() + 2 * knots[i + 1].getX());
                segments[i].getR().setY(4 * knots[i].getY() + 2 * knots[i + 1].getY());
                segments[i].getR().setZ(4 * knots[i].getZ() + 2 * knots[i + 1].getZ());
            }

            segments[n - 1].setA(2);
            segments[n - 1].setB(7);
            segments[n - 1].setC(0);
            segments[n - 1].getR().setX(8 * knots[n - 1].getX() + knots[n].getX());
            segments[n - 1].getR().setY(8 * knots[n - 1].getY() + knots[n].getY());
            segments[n - 1].getR().setZ(8 * knots[n - 1].getZ() + knots[n].getZ());

            for (i = 1; i < n; i++) {
                m = segments[i].getA() / segments[i - 1].getB();
                segments[i].setB(segments[i].getB() - m * segments[i - 1].getC());
                segments[i].getR().setX(
                        segments[i].getR().getX() - m * segments[i - 1].getR().getX());
                segments[i].getR().setY(
                        segments[i].getR().getY() - m * segments[i - 1].getR().getY());
                segments[i].getR().setZ(
                        segments[i].getR().getZ() - m * segments[i - 1].getR().getZ());
            }
            segments[n - 1].getP1().setX(
                    segments[n - 1].getR().getX() / segments[n - 1].getB());
            segments[n - 1].getP1().setY(
                    segments[n - 1].getR().getY() / segments[n - 1].getB());
            segments[n - 1].getP1().setZ(
                    segments[n - 1].getR().getZ() / segments[n - 1].getB());

            for (i = n - 2; i >= 0; i--) {
                segments[i].getP1().setX(
                        (segments[i].getR().getX() - segments[i].getC()
                                * segments[i + 1].getP1().getX())
                                / segments[i].getB());
                segments[i].getP1().setY(
                        (segments[i].getR().getY() - segments[i].getC()
                                * segments[i + 1].getP1().getY())
                                / segments[i].getB());
                segments[i].getP1().setZ(
                        (segments[i].getR().getZ() - segments[i].getC()
                                * segments[i + 1].getP1().getZ())
                                / segments[i].getB());
            }

            for (i = 0; i < n - 1; i++) {
                segments[i].getP2().setX(
                        2 * knots[i + 1].getX() - segments[i + 1].getP1().getX());
                segments[i].getP2().setY(
                        2 * knots[i + 1].getY() - segments[i + 1].getP1().getY());
                segments[i].getP2().setZ(
                        2 * knots[i + 1].getZ() - segments[i + 1].getP1().getZ());
            }
            segments[n - 1].getP2().setX(
                    0.5 * (knots[n].getX() + segments[n - 1].getP1().getX()));
            segments[n - 1].getP2().setY(
                    0.5 * (knots[n].getY() + segments[n - 1].getP1().getY()));
            segments[n - 1].getP2().setZ(
                    0.5 * (knots[n].getZ() + segments[n - 1].getP1().getZ()));
        }

        if (xflat != null) {
            for (BezierSplineSegment cs : segments) {
                cs.setX(xflat);
            }
        }
        if (yflat != null) {
            for (BezierSplineSegment cs : segments) {
                cs.setY(yflat);
            }
        }
        if (zflat != null) {
            for (BezierSplineSegment cs : segments) {
                cs.setZ(zflat);
            }
        }
    }

    @Override
    public String toString() {
        if (knots == null) {
            return "0 points.";
        }
        return knots.length + " points.";
    }

}
