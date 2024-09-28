package Prototype.User;

import java.time.LocalDate;
import java.util.Objects;

public class Resident extends User {

    // Constructor
    public Resident(String firstName, String lastName, String email,
                    String phoneNumber, String dateOfBirth,
                    String homeAddress, int password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setDateOfBirth(dateOfBirth);
        setHomeAddress(homeAddress);
        setPassword(password);
        setAbove16(isAgeAbove16(dateOfBirth));

        // Phone number is optional
        if (!Objects.equals(phoneNumber, "")) {
            setPhoneNumber(phoneNumber);
        }
    }

    // Attributes
    private String phoneNumber;
    private String dateOfBirth;
    private String homeAddress;
    private boolean isAbove16;

    // Getters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public boolean isAbove16() {
        return isAbove16;
    }

    // Setters
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setAbove16(boolean above16) {
        isAbove16 = above16;
    }

    // Methods
    public boolean isAgeAbove16(String dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        int age = ageFinder.findAge(dateOfBirth, currentDate.toString());
        System.out.println("Age : " + age);
        return age >= 16;
    }
}
