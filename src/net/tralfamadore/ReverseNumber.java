package net.tralfamadore;

import java.util.Scanner;

/**
 * Class: ReverseNumber
 * Created by billreh on 12/24/16.
 */
public class ReverseNumber {
    public static void main(String[] args) {
        int num = 1234;
        int rev = reverse(num);
        System.out.println(rev);
        System.out.println("Enter a number: ");
        Scanner scanner = new Scanner(System.in);
        num = scanner.nextInt();
        rev = reverse(num);
        if(num == rev)
            System.out.println("Palindrome!");
        else
            System.out.println("Non palindrome :(");
    }

    public static int reverse(int num) {
        String s = new String( ""+ num);
        StringBuilder sb = new StringBuilder();
        for(int i = s.length() -1; i >= 0; i--)
            sb.append(s.charAt(i));

        return new Integer(sb.toString());
    }
}
