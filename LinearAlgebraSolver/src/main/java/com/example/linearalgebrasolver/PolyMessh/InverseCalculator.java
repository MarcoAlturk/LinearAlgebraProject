package com.example.linearalgebrasolver.PolyMessh;

import com.example.linearalgebrasolver.OperationsMatrices.DeterminantCalculator;

public class InverseCalculator {
    public static double[][] calculateInverse(double[][] matrix) {
        try {
            // Check if the matrix is square
            int n = matrix.length;
            if (matrix[0].length != n) {
                throw new IllegalArgumentException("Matrix must be square");
            }

            // Calculate the determinant
            double determinant = DeterminantCalculator.calculateDeterminant(matrix);
            if (determinant == 0) {
                throw new ArithmeticException("Matrix is singular and does not have an inverse");
            }

            // Calculate the adjugate matrix
            double[][] adjugate = calculateAdjugate(matrix);

            // Divide the adjugate matrix by the determinant to get the inverse
            double[][] inverse = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    inverse[i][j] = adjugate[i][j] / determinant;
                }
            }

            return inverse;

        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static double[][] calculateAdjugate(double[][] matrix) {
        int n = matrix.length;

        // Validate that the matrix is square
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("Matrix must be square to calculate the adjugate.");
        }

        double[][] adjugate = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] minor = DeterminantCalculator.getMinor(matrix, i, j);

                double determinant = DeterminantCalculator.calculateDeterminant(minor);

                double cofactor = Math.pow(-1, i + j) * determinant;

                adjugate[j][i] = cofactor;
            }
        }

        return adjugate;
    }

    // Example usage
    public static void main(String[] args) {
        double[][] matrix = {
                {4, 7},
                {2, 6}
        };

        try {
            double[][] inverse = calculateInverse(matrix);
            System.out.println("Inverse matrix:");
            for (double[] row : inverse) {
                for (double value : row) {
                    System.out.printf("%8.4f", value);
                }
                System.out.println();
            }
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
