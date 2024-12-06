package com.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Candidature {

    private String dateFin;
    private String dateDebut;
    private boolean isAccepted;
    private boolean isConfirmed;
    private String userId;

    public Candidature(String dateFin, String dateDebut, String userId,
                       boolean isAccepted, boolean isConfirmed) {
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.isAccepted = isAccepted;
        this.isConfirmed = isConfirmed;
        this.userId = userId;
    }

    // Getters

    public String getDateFin() {
        return this.dateFin;
    }

    public String getDateDebut() {
        return this.dateDebut;
    }

    public boolean isAccepted() {
        return this.isAccepted;
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    public String getUserId() {
        return this.userId;
    }

    // Setters

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
