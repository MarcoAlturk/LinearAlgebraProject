package com.example.linearalgebrasolver.OperationsMatrices;

public class LineToPointDistance {
    public static double calculate(double[] linePoint, double[] lineDirection, double[] point) {
        // Vector from linePoint to the given point
        double[] lineToPoint = {
                point[0] - linePoint[0],
                point[1] - linePoint[1],
                point[2] - linePoint[2]
        };

        // Cross product of direction vector and lineToPoint vector
        double[] crossProduct = {
                lineDirection[1] * lineToPoint[2] - lineDirection[2] * lineToPoint[1],
                lineDirection[2] * lineToPoint[0] - lineDirection[0] * lineToPoint[2],
                lineDirection[0] * lineToPoint[1] - lineDirection[1] * lineToPoint[0]
        };

        // Magnitude of the cross product
        double crossMagnitude = Math.sqrt(
                crossProduct[0] * crossProduct[0] +
                        crossProduct[1] * crossProduct[1] +
                        crossProduct[2] * crossProduct[2]
        );

        // Magnitude of the direction vector
        double directionMagnitude = Math.sqrt(
                lineDirection[0] * lineDirection[0] +
                        lineDirection[1] * lineDirection[1] +
                        lineDirection[2] * lineDirection[2]
        );

        // Distance is the magnitude of the cross product divided by the magnitude of the direction vector
        return crossMagnitude / directionMagnitude;
    }
}

