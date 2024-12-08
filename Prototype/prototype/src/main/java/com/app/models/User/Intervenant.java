package com.app.models.User;

import com.app.models.Notification;
import com.app.utils.InscriptionUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intervenant extends User {

    // Constructeur par défaut
    public Intervenant() {
        setUserRole("INTERVENANT");
    }

    // Constructor pour création de compte
    public Intervenant(String firstName, String lastName, String email,
                       String entityType, String cityId, int password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setEntityType(entityType);
        setCityId(cityId);
        setPassword(password);
        setUserRole("INTERVENANT");
        setUserId(InscriptionUtils.RandomIDGenerator());
    }

    // Constructor pour se connecter
    public Intervenant(String firstName, String lastName, String email,
                       String entityType, String cityId, int password,
                       String userId, List<Notification> notifications) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setEntityType(entityType);
        setCityId(cityId);
        setPassword(password);
        setUserRole("INTERVENANT");
        setUserId(userId);
    }


    // Attributes
    private String cityId;
    private String entityType;

    // Getters
    public String getCityId() {
        return cityId;
    }

    public String getEntityType() {
        return entityType;
    }

    // Setters
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
