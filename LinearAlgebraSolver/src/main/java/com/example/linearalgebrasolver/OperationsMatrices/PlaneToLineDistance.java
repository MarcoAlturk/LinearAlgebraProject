package com.example.linearalgebrasolver.OperationsMatrices;

public class PlaneToLineDistance {
    public static Double calculate(double A, double B, double C, double D,
                                   double[] pointOnLine, double[] directionVector) {
        // Calculate the dot product of plane normal and direction vector
        double dotProduct = A * directionVector[0] + B * directionVector[1] + C * directionVector[2];

        // If dot product is not zero, the line is not parallel to the plane (distance = 0)
        if (dotProduct != 0) {
            return 0.0; // Line intersects the plane
        }

        // Otherwise, calculate the distance using the point-to-plane formula
        double x0 = pointOnLine[0];
        double y0 = pointOnLine[1];
        double z0 = pointOnLine[2];
        double numerator = Math.abs(A * x0 + B * y0 + C * z0 + D);
        double denominator = Math.sqrt(A * A + B * B + C * C);
        return numerator / denominator;
    }
}

