package Prototype.Auth;

import Prototype.User.AgeFinder;

import java.time.LocalDate;
import java.util.Objects;

public class Inscription {

    public static boolean isSamePassword(String password1, String password2) {
        return Objects.equals(password1, password2);
    }

    public static String isValidPassword(String password) {
        String returnMessage = "";
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        if (password.length() < 8) {
            returnMessage = "Minimum of 8 character required";
        } else if (!password.matches(regex)) {
            returnMessage =
                    "Minimum of 1 capital letter and 1" + "number" + " " +
                            "required";
        }
        return returnMessage;
    }

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


    public static boolean isAgeAbove16(String dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        int age = AgeFinder.findAge(dateOfBirth, currentDate.toString());
        return age >= 16;
    }

}
