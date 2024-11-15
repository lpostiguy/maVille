package com.app.models.User;

public class User {

    // Attributes
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int password;
    private boolean connected;

    // Getters

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getPassword() {
        return password;
    }

    public boolean isConnected() {
        return connected;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

