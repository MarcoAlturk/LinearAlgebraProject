package com.example.linearalgebrasolver;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class MatrixTest {

    private static final double EPSILON = 1e-10;

    public static void main(String[] args) {

        // Coefficient matrix A (3x3 matrix)
        double[][] arrayA = {
                {4, -4, 3},
                {-5, -6, 4},
                {6, 5, 6}
        };

        // Constants matrix B (3x1 matrix)
        double[] arrayB = {-28, -18, -26};

        // Convert arrays to SimpleMatrix
        //SimpleMatrix a = new SimpleMatrix(arrayA);
        //SimpleMatrix b = new SimpleMatrix(arrayB); // this take as argument double[][]

       // System.out.println(a.solve(b));



        // Solve the system of equations A * x = B
       SimpleMatrix result = EJMMatrixOperations.gaussianElimination(arrayA, arrayB); // this take double[]
        System.out.println("Result (Solution vector x): \n" + result); // works both cases
       // System.out.println(EJMMatrixOperations.det(a));

    }

    public static double[] lsolve(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;



    }}
