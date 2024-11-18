package com.example.linearalgebrasolver;

public class DeterminantCalculator {
    public static double calculateDeterminant(double[][] matrix) {
        // Check if the matrix is square
        int n = matrix.length;
        if (matrix[0].length != n) {
            throw new IllegalArgumentException("Matrix must be square");
        }


        // Base case for 2x2 matrix
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }


        // Recursively calculate the determinant
        double determinant = 0;
        for (int i = 0; i < n; i++) {
            // Get the minor matrix after excluding the current row (0) and column (i)
            double[][] minor = getMinor(matrix, 0, i);
            // Alternate the sign based on the column index and add the cofactor
            determinant += Math.pow(-1, i) * matrix[0][i] * calculateDeterminant(minor);
        }
        return determinant;
    }


    // Helper method to get the minor of a matrix by excluding a row and a column
    private static double[][] getMinor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];


        for (int i = 0, m = 0; i < n; i++) {
            for (int j = 0, n2 = 0; j < n; j++) {
                if (i != row && j != col) {
                    minor[m][n2] = matrix[i][j];
                    n2++;
                }
            }
            if (i != row) {
                m++;
            }
        }
        return minor;
    }


    // Example usage
    public static void main(String[] args) {
        double[][] matrix = {
                {4, 3, 2, 5},
                {3, 4, 1, 6},
                {2, 1, 3, 7},
                {0, 0, 4, 0}
        };


        double determinant = calculateDeterminant(matrix);
        System.out.println("Determinant: " + determinant);
    }

}
