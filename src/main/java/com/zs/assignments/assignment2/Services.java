package com.zs.assignments.assignment2;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Services {
    Scanner sc = new Scanner(System.in);

    public void printMatrix(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;

        System.out.println("The resultant Matrix is: ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] takeInput() {
        try {
            System.out.println("Enter the number of rows: ");
            int rows = sc.nextInt();
            System.out.println("Enter the number of columns: ");
            int columns = sc.nextInt();
            if (rows <= 0 || columns <= 0) {
                throw new IllegalArgumentException("Rows or Columns can't be zero or negative");
            }

            System.out.println("Enter the Matrix: ");
            int[][] matrix = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            return matrix;
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
            System.out.println("Enter the details again");
            return takeInput();
        }
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int[][] operationAddSubtract(int[][] A, int[][] B, BiFunction<Integer, Integer, Integer> operation) {
        int rows = A.length;
        int columns = A[0].length;

        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = operation.apply(A[i][j], B[i][j]);
            }
        }
        return result;
    }

    public int[][] multiplicationOfMatrices(int[][] A, int[][] B) {
        int rowA = A.length;
        int rowB = B.length;
        int columnB = B[0].length;

        int[][] result = new int[rowA][columnB];
        for (int i = 0; i < rowA; i++) {
            for (int j = 0; j < columnB; j++) {
                for (int k = 0; k < rowB; k++)
                    result[i][j] += A[i][k] * B[k][j];
            }
        }
        return result;
    }

    public int[][] scalarMultiplication(int[][] A, int K) {
        int rows = A.length;
        int columns = A[0].length;

        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = A[i][j] * K;
            }
        }
        return result;
    }

    public int[][] transposeOfMatrix(int[][] A) {
        int rows = A.length;
        int columns = A[0].length;

        int[][] result = new int[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = A[i][j];
            }
        }
        return result;
    }
}
