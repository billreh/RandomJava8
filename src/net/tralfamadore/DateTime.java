package net.tralfamadore;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class: DateTime
 * Created by billreh on 12/24/16.
 */
public class DateTime {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse("2016-12-24", formatter);
        System.out.println(date);
        formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        date = LocalDate.parse("12-24-2016", formatter);
        System.out.println(date);

        DecimalFormat format = new DecimalFormat("$#0.00");
        double d = 122.335;
        System.out.println(format.format(d));
        String refund = format.format(d);
        String cleansedAmount = refund.replaceAll("[^.\\d]", "");
        System.out.println(cleansedAmount);
    }
}
