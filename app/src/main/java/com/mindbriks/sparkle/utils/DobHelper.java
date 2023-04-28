package com.mindbriks.sparkle.utils;

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
}