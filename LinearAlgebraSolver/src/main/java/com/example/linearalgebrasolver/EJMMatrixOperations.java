package com.example.linearalgebrasolver;

import org.ejml.simple.SimpleMatrix;
public class EJMMatrixOperations {
    // Gaussian Elimination (Solving Ax = B)
    public static SimpleMatrix gaussianEliminatin(SimpleMatrix A, SimpleMatrix B) {
        // Solve Ax = B
        return A.solve(B);
    }

    // Determinant of a matrix
    public static double det(SimpleMatrix A) {
        return A.determinant();
    }

    // Matrix Addition: A + B
    public static SimpleMatrix add(SimpleMatrix A, SimpleMatrix B) {
        return A.plus(B);
    }

    // Matrix Multiplication: A * B
    public static SimpleMatrix multiply(SimpleMatrix A, SimpleMatrix B) {
        return A.mult(B);
    }

    // Transpose of a matrix: A^T
    public static SimpleMatrix transpose(SimpleMatrix A) {
        return A.transpose();
    }

    // Conjugate of a matrix (complex conjugate)
    public static SimpleMatrix conjugate(SimpleMatrix A) {
        // EJML doesn't support complex numbers natively, so it's not directly applicable.
        // This function will return a "conjugate" by assuming real values (just a dummy method).
        return A;
    }

    // Adjoint (Adjugate) of a matrix: adj(A)
    public static SimpleMatrix adjoint(SimpleMatrix A) {
        // Calculate the adjoint: adj(A) = A^-1 * det(A)
        SimpleMatrix inverse = A.invert();
        double det = A.determinant();

        // Create a matrix where every element is multiplied by the determinant
        SimpleMatrix adjugate = inverse.scale(det);

        return adjugate;
    }

    // Inverse of a matrix: A^-1
    public static SimpleMatrix inverse(SimpleMatrix A) {
        return A.invert();
    }
    // Find the identity matrix
    public static SimpleMatrix identity(int n) {
        return SimpleMatrix.identity(n);
    }

    // Example of matrix scaling: A * scalar
    public static SimpleMatrix scale(SimpleMatrix A, double scalar) {
        return A.scale(scalar);
    }












    // Method to print a matrix
    public static void printMatrix(SimpleMatrix matrix) {
        matrix.print();  // SimpleMatrix has a built-in print() method
    }

    // Method to swap two rows in a matrix
    public static void swapRows(SimpleMatrix matrix, int row1, int row2) {
        for (int col = 0; col < matrix.numCols(); col++) {
            double temp = matrix.get(row1, col);
            matrix.set(row1, col, matrix.get(row2, col));
            matrix.set(row2, col, temp);
        }
    }

    // Method to perform Gaussian Elimination (A * x = B)
    public static SimpleMatrix gaussianElimination(SimpleMatrix A, SimpleMatrix B) {
        int rows = A.numRows();
        int cols = A.numCols();

        // Combine A and B into an augmented matrix (A | B)
        SimpleMatrix augmentedMatrix = new SimpleMatrix(rows, cols + 1);
        augmentedMatrix.insertIntoThis(0, 0, A); // Insert A into augmented matrix
        augmentedMatrix.insertIntoThis(0, cols, B); // Insert B into augmented matrix

        System.out.println("Initial augmented matrix:");
        printMatrix(augmentedMatrix);

        // Perform Gaussian elimination
        for (int i = 0; i < rows; i++) {
            // Pivoting: Find the maximum element in column i
            int maxRow = i;
            for (int j = i + 1; j < rows; j++) {
                if (Math.abs(augmentedMatrix.get(j, i)) > Math.abs(augmentedMatrix.get(maxRow, i))) {
                    maxRow = j;
                }
            }

            // Swap rows if necessary
            if (maxRow != i) {
                swapRows(augmentedMatrix, i, maxRow); // Swap the rows manually
                System.out.println("After row swap " + i + " with row " + maxRow + ":");
                printMatrix(augmentedMatrix); // Prints the updated augmented matrix after the swap
            }

            // Normalize the pivot row by dividing all elements by the pivot
            double pivot = augmentedMatrix.get(i, i);
            for (int j = i; j < cols + 1; j++) {
                augmentedMatrix.set(i, j, augmentedMatrix.get(i, j) / pivot);
            }
            System.out.println("After normalizing row " + i + ":");
            printMatrix(augmentedMatrix);

            // Eliminate below the pivot
            for (int j = i + 1; j < rows; j++) {
                double factor = augmentedMatrix.get(j, i);
                for (int k = i; k < cols + 1; k++) {
                    augmentedMatrix.set(j, k, augmentedMatrix.get(j, k) - factor * augmentedMatrix.get(i, k));
                }
            }
            System.out.println("After eliminating column " + i + ":");
            printMatrix(augmentedMatrix);
        }

        // Back substitution to get the solution
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                double factor = augmentedMatrix.get(j, i);
                augmentedMatrix.set(j, cols, augmentedMatrix.get(j, cols) - factor * augmentedMatrix.get(i, cols));
            }
        }
        System.out.println("After back substitution:");
        printMatrix(augmentedMatrix);

        // Extract the solution vector from the augmented matrix
        SimpleMatrix solution = new SimpleMatrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            solution.set(i, 0, augmentedMatrix.get(i, cols));
        }

        return solution;
    }
}
