package net.tralfamadore;

import java.util.Scanner;

/**
 * Class: FindGcd
 * Created by billreh on 12/24/16.
 */
public class FindGcd {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();
        System.out.println();
        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();
        System.out.println();
        System.out.println("Greatest common divisor: " + gcd(num1, num2));
    }

    private static int gcd(int num1, int num2) {
        if(num2 == 0)
            return num1;
        return gcd(num2, num1 % num2);
    }
}
