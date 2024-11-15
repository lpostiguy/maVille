package com.app.utils;

public class RegexChecker {

    public static boolean isValidDateFormat(String date) {
        // Validate date format (YYYY-MM-DD)
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(regex);
    }

    public static boolean isValidPhoneNumberFormat(String phoneNumber) {
        //  Validate phone number, format 000-000-0000
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        return phoneNumber.matches(regex);
    }

}
