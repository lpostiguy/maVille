package com.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequeteTravail {

    private String titre;
    private String description;
    private String typeTravaux;
    private String dateDebutEspere;
    private String demandeurRequeteId;

    @JsonCreator
    public RequeteTravail(@JsonProperty("titre") String titre, @JsonProperty(
        "description") String description,
                          @JsonProperty("typeTravaux") String typeTravaux,
                          @JsonProperty("dateDebutEspere") String dateDebutEspere, @JsonProperty("demandeurRequeteId") String demandeurRequeteId) {
        this.titre = titre;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.dateDebutEspere = dateDebutEspere;
        this.demandeurRequeteId = demandeurRequeteId;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeTravaux() {
        return typeTravaux;
    }

    public String getDateDebutEspere() {
        return dateDebutEspere;
    }

    public String getDemandeurRequeteId() {
        return demandeurRequeteId;
    }

    // Setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    public void setDemandeurRequeteId(String demandeurRequeteId) {
        this.demandeurRequeteId = demandeurRequeteId;
    }
}
