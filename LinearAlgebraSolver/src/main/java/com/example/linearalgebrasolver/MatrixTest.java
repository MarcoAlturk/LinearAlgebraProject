package com.example.linearalgebrasolver;

import org.ejml.simple.SimpleMatrix;

public class MatrixTest {
    public static void main(String[] args) {

        // Coefficient matrix A (3x3 matrix)
        double[][] arrayA = {
                {2, 1, 3},
                {1, 2, 1},
                {3, 3, 2}
        };

        // Constants matrix B (3x1 matrix)
        double[] arrayB = {9, 8, 7};

        // Convert arrays to SimpleMatrix


        // Solve the system of equations A * x = B
        SimpleMatrix result = EJMMatrixOperations.gaussianElimination(arrayA, arrayB);
        System.out.println("Result (Solution vector x): \n" + result);
    }


}
