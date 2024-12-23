package com.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.Document;

/**
 * Cette classe représente une candidature faite par un résident et contient des
 * informations liées à la candidature.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Candidature {

    private String dateFin;
    private String dateDebut;
    private String status;
    private boolean confirmed;
    private String userId;
    private String residentMsg;
    private String intervenantMsg;
    private String id;

    // Constructeur par défaut (Obligatoire pour la désérialisation).
    public Candidature() {

    }

    // Méthode de conversion à partir d'un Document
    public static Candidature fromDocument(Document document) {
        String dateFin = document.getString("dateFin");
        String dateDebut = document.getString("dateDebut");
        boolean isConfirmed = document.getBoolean("isConfirmed");
        String status = document.getString("status");
        String userId = document.getString("userId");
        String residentMsg = document.getString("residentMsg");
        String intervenantMsg = document.getString("intervenantMsg");
        String id = document.getString("id");

        // Créer une instance de Notification
        return new Candidature(dateFin, dateDebut, userId, status, isConfirmed, residentMsg, intervenantMsg, id);
    }

    public Candidature(String dateFin, String dateDebut, String userId,
                       String status, boolean confirmed, String residentMsg, String intervenantMsg, String id) {
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.status = status;
        this.confirmed = confirmed;
        this.userId = userId;
        this.residentMsg = residentMsg;
        this.intervenantMsg = intervenantMsg;
        this.id = id;
    }

    // Getters

    public String getDateFin() {
        return this.dateFin;
    }

    public String getDateDebut() {
        return this.dateDebut;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getResidentMsg() { return this.residentMsg; }

    public String getIntervenantMsg() { return this.intervenantMsg; }

    public String getId() { return this.id; }

    // Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAccepted(String status) {
        this.status = status;
    }

    public void setResidentMsg(String residentMsg) {
        this.residentMsg = residentMsg;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.confirmed = isConfirmed;
    }

    public void setId(String id) {
        this.id = id;
    }
}
