package Prototype.Auth;

public class GlobalUserInfo {

    public static String currentRole = "";

    // Hard Coded Resident
    public static String firstNameRes = "Test First Name";
    public static String lastNameRes = "Test Last Name";
    public static String emailRes = "test@resident.com";
    public static String dateOfBirthRes = "20";
    public static String homeAddressRes = "564, test Avenue, Montréal";
    public static String passwordRes = "Test12345";


    // Hard Code Intervenant
    public static String firstNameInt = "Test First Name";
    public static String lastNameInt = "Test Last Name";
    public static String emailInt = "test@intervenant.com";
    public static String entityTypeInt = "Entreprise";
    public static String cityIdInt = "273283";
    public static String passwordInt = "Test12345";

    public static void setCurrentRole(String currentRole) {
        GlobalUserInfo.currentRole = currentRole;
    }
}
