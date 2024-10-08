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
        setRole("RESIDENT");

        // Phone number is optional
        if (!Objects.equals(phoneNumber, "")) {
            setPhoneNumber(phoneNumber);
        }
    }

    // Attributes
    private String phoneNumber;
    private String dateOfBirth;
    private String homeAddress;

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
}
