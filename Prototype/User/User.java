package Prototype.User;

import java.lang.reflect.Constructor;

public class User {

    // Attributes
    private String firstname;
    private String lastname;
    private String email;
    private int password;
    private boolean connected;
    private String role;

    // Getters
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public String getRole() {
        return role;
    }

    // Setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

