package com.example.linearalgebrasolver;

import org.ejml.simple.SimpleMatrix;

public class MatrixTest {
    public static void main(String[] args) {

        // Coefficient matrix A (3x3 matrix)
        double[][] arrayA = {
                {1, 2},
                {4, 5}
        };

        // Constants matrix B (3x1 matrix)
        double[][] arrayB = {
                {6},
                {15},
        };

        // Convert arrays to SimpleMatrix
        SimpleMatrix A = new SimpleMatrix(arrayA);
        SimpleMatrix B = new SimpleMatrix(arrayB);

        // Solve the system of equations A * x = B
        SimpleMatrix result = EJMMatrixOperations.gaussianElimination(A, B);
        System.out.println("Result (Solution vector x): \n" + result);
    }


}
