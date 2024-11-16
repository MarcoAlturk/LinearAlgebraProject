package com.example.linearalgebrasolver;

import org.ejml.simple.SimpleMatrix;
public class EJMMatrixOperations {
    // Gaussian Elimination (Solving Ax = B)
    public static SimpleMatrix gaussianElimination(SimpleMatrix A, SimpleMatrix B) {
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
}
