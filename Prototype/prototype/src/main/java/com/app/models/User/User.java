package com.app.models.User;

import com.app.models.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userRole")
@JsonSubTypes({@JsonSubTypes.Type(value = Resident.class, name = "RESIDENT"),
    @JsonSubTypes.Type(value = Intervenant.class, name = "INTERVENANT")})

@JsonIgnoreProperties(ignoreUnknown = true)

public class User {

    // Attributes
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userRole;
    private int password;
    private boolean connected;


    // Getters
    public String getUserId() {
        return userId;
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

    public String getUserRole() {
        return userRole;
    }

    public int getPassword() {
        return password;
    }

    public boolean isConnected() {
        return connected;
    }

    // Setters
    public void setUserId(String id) {
        this.userId = id;
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

    public void setUserRole(String role) {
        this.userRole = role;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

