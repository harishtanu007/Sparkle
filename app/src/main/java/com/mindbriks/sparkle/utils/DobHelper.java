package com.mindbriks.sparkle.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DobHelper {
    public static int calculateAge(long dobUnix) {
        // Get the current time in milliseconds since January 1, 1970
        long currentTimeMillis = System.currentTimeMillis();
        // Convert the Unix birthdate timestamp to milliseconds
        long birthdateMillis = dobUnix * 1000;
        // Calculate the difference between the current time and the birthdate
        long ageMillis = currentTimeMillis - birthdateMillis;

        // Calculate the age in years
        int ageYears = (int) (ageMillis / (1000 * 60 * 60 * 24 * 365.25));

        return ageYears;
    }

    public static String getDateFromUnixTime(long unixTime) {
        // Convert Unix time to a Date object
        Date date = new Date(unixTime * 1000L);

        // Format the date string
        SimpleDateFormat sdf = new SimpleDateFormat("d'" + getDayOfMonthSuffix(date) + "' MMMM yyyy");
        return sdf.format(date);
    }

    // Method to get the day of month suffix (e.g. "st" for "1st", "nd" for "2nd", etc.)
    private static String getDayOfMonthSuffix(Date date) {
        int day = Integer.parseInt(new SimpleDateFormat("d").format(date));
        switch (day) {
            case 1:
            case 21:
            case 31:
                return "st";
            case 2:
            case 22:
                return "nd";
            case 3:
            case 23:
                return "rd";
            default:
                return "th";
        }
    }

}