package com.example.linearalgebrasolver.OperationsMatrices;

public class PlaneToPointDistance {
    public static double calculate(double A, double B, double C, double D, double x, double y, double z) {
        double numerator = Math.abs(A * x + B * y + C * z + D);
        double denominator = Math.sqrt(A * A + B * B + C * C);
        return numerator / denominator;
    }
}

