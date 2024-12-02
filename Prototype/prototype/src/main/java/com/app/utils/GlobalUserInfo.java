package com.app.utils;

public class GlobalUserInfo {

    public static String firstName;
    public static String lastName;
    public static String currentRole = "";
    public static String currentUserId = "";
    public static String currentBoroughId = "";

    // Hard Coded Resident 1
    public static String firstNameRes1 = "Jacob";
    public static String lastNameRes1 = "Wayne";
    public static String emailRes1 = "jacob.wayne@resident.com";
    public static String phoneNumberRes1 = "514-888-9090";
    public static String dateOfBirthRes1 = "02-07-2001";
    public static String homeAddressRes1 = "564, wayne Avenue, Montréal";
    public static String passwordRes1 = "Wayne12345";

    // Hard Coded Resident 2
    public static String firstNameRes2 = "Marc";
    public static String lastNameRes2 = "Jacobs";
    public static String emailRes2 = "marc.jacobs@resident.com";
    public static String phoneNumberRes2 = "516-886-9312";
    public static String dateOfBirthRes2 = "12-07-2006";
    public static String homeAddressRes2 = "464, Jacobs Avenue, Montréal";
    public static String passwordRes2 = "Marc12345";

    // Hard Coded Resident 3
    public static String firstNameRes3 = "Max";
    public static String lastNameRes3 = "Ronald";
    public static String emailRes3 = "max.ronald@resident.com";
    public static String phoneNumberRes3 = "214-348-1390";
    public static String dateOfBirthRes3 = "01-12-2011";
    public static String homeAddressRes3 = "894, Ronald Avenue, Montréal";
    public static String passwordRes3 = "Max12345";


    // Hard Code Intervenant 1
    public static String firstNameInt1 = "James";
    public static String lastNameInt1 = "Williams";
    public static String emailInt1 = "james.williams@intervenant.com";
    public static String entityTypeInt1 = "Entreprise";
    public static String cityIdInt1 = "273283";
    public static String passwordInt1 = "James12345";

    // Hard Code Intervenant 2
    public static String firstNameInt2 = "George";
    public static String lastNameInt2 = "Mackenzie";
    public static String emailInt2 = "george.mackenzie@intervenant.com";
    public static String entityTypeInt2 = "Entrepreneur";
    public static String cityIdInt2 = "742392";
    public static String passwordInt2 = "George12345";

    // Hard Code Intervenant 3
    public static String firstNameInt3 = "Bruce";
    public static String lastNameInt3 = "Cuban";
    public static String emailInt3 = "bruce.cuban@intervenant.com";
    public static String entityTypeInt3 = "Entreprise";
    public static String cityIdInt3 = "99384652";
    public static String passwordInt3 = "Bruce12345";

    public static void setCurrentRole(String currentRole) {
        GlobalUserInfo.currentRole = currentRole;
    }

    public static void setCurrentUserId(String currentUserId) {
        GlobalUserInfo.currentUserId = currentUserId;
    }

    public static void setCurrentBoroughId(String currentUserId) {
        GlobalUserInfo.currentUserId = currentUserId;
    }
}
