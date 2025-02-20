package com.zs.assignments.assignment2;

import java.util.Scanner;

public class Controller {
    public void startCalculator() {
        Scanner sc = new Scanner(System.in);
        Services services = new Services();

        System.out.println("-- Welcome to ZOP Matrix Calculator --");
        int option;

        do {
            System.out.println("Choose the number of operation you want to do :");
            System.out.println("1. Add 2. Subtract 3. Multiply 4. Multiply with scalar value 5. Transpose 6. Exit");

            option = sc.nextInt();
            if (option < 1 || option > 6) {
                System.out.println("Choose a correct option");
                continue;
            } else if (option == 6) {
                System.out.println("Exiting..");
                continue;
            }

            int[][] A = services.takeInput();
            int[][] B = new int[1][2];
            if (option == 1 || (option == 2 || option == 3)) {
                System.out.println("Enter the details of second Matrix:");
                B = services.takeInput();
            }

            switch (option) {

                case 1:
                    if (A.length == B.length && A[0].length == B[0].length)
                        services.printMatrix(services.operationAddSubtract(A, B, services::add));
                    else {
                        System.out.println("Addition of different dimension Matrices is not possible");
                    }
                    break;

                case 2:
                    if (A.length == B.length && A[0].length == B[0].length)
                        services.printMatrix(services.operationAddSubtract(A, B, services::subtract));
                    else
                        System.out.println("Subtraction of different dimension Matrices is not possible");
                    break;

                case 3:
                    if (B.length != A[0].length) {
                        System.out.println("Multiplication of these Matrices is not possible");
                    } else {
                        services.printMatrix(services.multiplicationOfMatrices(A, B));
                    }
                    break;

                case 4:
                    System.out.println("Enter the Scalar value: ");
                    int scalarValue = sc.nextInt();
                    services.printMatrix(services.scalarMultiplication(A, scalarValue));
                    break;

                case 5:
                    services.printMatrix(services.transposOfMatrix(A));
                    break;
            }
        }
        while (option != 6);

        System.out.println("Thanks for using ZOP Services");
    }
}
