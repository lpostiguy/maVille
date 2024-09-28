package Prototype.User;

import java.lang.reflect.Constructor;

public class User {

    // Attributes
    private String firstName;
    private String lastName;
    private String email;
    private int password;
    private boolean connected;

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPassword() {
        return password;
    }

    public boolean isConnected() {
        return connected;
    }

    // Setters
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

