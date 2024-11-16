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


    private static final double EPSILON = 1e-10;

    // Gaussian elimination with partial pivoting
    public static SimpleMatrix gaussianElimination(double[][] A, double[] b) {
        int n = b.length;

        // Perform Gaussian elimination
        for (int p = 0; p < n; p++) {

            // Find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            // Swap rows in A
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            // Swap rows in b
            double t = b[p]; b[p] = b[max]; b[max] = t;

            // Check for singular or nearly singular matrix
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // Pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // Back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }

        // Return the solution as a SimpleMatrix
        SimpleMatrix result = new SimpleMatrix(n, 1);
        for (int i = 0; i < n; i++) {
            result.set(i, 0, x[i]);
        }

        return result;
    }
}
