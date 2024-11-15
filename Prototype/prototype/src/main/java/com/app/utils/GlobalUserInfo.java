package com.app.utils;

public class GlobalUserInfo {

    public static String currentRole = "";

    // Hard Coded Resident
    public static String firstNameRes = "Test First Name";
    public static String lastNameRes = "Test Last Name";
    public static String emailRes = "test@resident.com";
    public static String phoneNumberRes = "514-888-9090";
    public static String dateOfBirthRes = "02-07-2001";
    public static String homeAddressRes = "564, test Avenue, Montréal";
    public static String passwordRes = "Test12345";
    public static String idRes = "34ed6d7wdw9ee3451";


    // Hard Code Intervenant
    public static String firstNameInt = "Test First Name";
    public static String lastNameInt = "Test Last Name";
    public static String emailInt = "test@intervenant.com";
    public static String entityTypeInt = "Entreprise";
    public static String cityIdInt = "273283";
    public static String passwordInt = "Test12345";
    public static String idInt = "de56723vd82h3ve3";

    public static void setCurrentRole(String currentRole) {
        GlobalUserInfo.currentRole = currentRole;
    }
}
