package com.example.linearalgebrasolver;

public class ClosestPointOnPlane {


    public static double[] closestPointOnPlane(double A, double B, double C, double D, double x, double y, double z) {
        // Parametric equations of the line:
        // x(t) = x0 + A * t
        // y(t) = y0 + B * t
        // z(t) = z0 + C * t
        double x0 = x;
        double y0 = y;
        double z0 = z;

        // Substitute into the plane equation to solve for t
        // A * (x0 + A * t) + B * (y0 + B * t) + C * (z0 + C * t) + D = 0
        // Solve for t
        double t = -(A * x0 + B * y0 + C * z0 + D) / (A * A + B * B + C * C);

        // Find the closest point by plugging t into the parametric equations
        double xClosest = x0 + A * t;
        double yClosest = y0 + B * t;
        double zClosest = z0 + C * t;
        double[] points = {xClosest, yClosest, zClosest};
        return points;
    }
}

