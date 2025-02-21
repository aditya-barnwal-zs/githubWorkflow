package com.zs.assignments.assignment2;

import java.util.Scanner;

public class Controller {
    Scanner sc = new Scanner(System.in);
    Services services = new Services();

    public void startCalculator() {
        System.out.println("-- Welcome to ZOP Matrix Calculator --");
        int option;

        do {
            System.out.println("Choose the number of operation you want to do :");
            System.out.println("1. Add \n2. Subtract \n3. Multiply \n4. Multiply with scalar value \n5. Transpose \n6. Exit");

            option = sc.nextInt();
            handleOperation(option);
        }
        while (option != 6);
        System.out.println("Thanks for using ZOP Services");
    }

    private void handleOperation(int option) {
        switch (option) {

            case 1: {
                int[][] A = services.takeInput();
                int[][] B = services.takeInput();
                if (A.length != B.length || A[0].length != B[0].length) {
                    System.out.println("Addition of different dimension Matrices is not possible");
                    return;
                }
                services.printMatrix(services.operationAddSubtract(A, B, services::add));
                break;
            }
            case 2: {
                int[][] A = services.takeInput();
                int[][] B = services.takeInput();
                if (A.length != B.length || A[0].length != B[0].length) {
                    System.out.println("Subtraction of different dimension Matrices is not possible");
                    return;
                }
                services.printMatrix(services.operationAddSubtract(A, B, services::subtract));
                break;
            }
            case 3: {
                int[][] A = services.takeInput();
                int[][] B = services.takeInput();
                if (B.length != A[0].length) {
                    System.out.println("Multiplication of these Matrices is not possible");
                } else {
                    services.printMatrix(services.multiplicationOfMatrices(A, B));
                }
                break;
            }
            case 4: {
                int[][] A = services.takeInput();
                System.out.println("Enter the Scalar value: ");
                int scalarValue = sc.nextInt();
                services.printMatrix(services.scalarMultiplication(A, scalarValue));
                break;
            }
            case 5: {
                int[][] A = services.takeInput();
                services.printMatrix(services.transposOfMatrix(A));
                break;
            }
            case 6: {
                System.out.println("Exiting...");
                break;
            }
            default: {
                System.out.println("Choose a correct option");
            }
        }
    }
}