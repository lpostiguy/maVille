package com.app.models;

import com.app.utils.InscriptionUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;
import org.bson.types.ObjectId;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RequeteTravail {

    private String titre;
    private String description;
    private String typeTravaux;
    private String dateDebutEspere;
    @JsonProperty("id")
    private String id;
    @JsonProperty("demandeurRequete")
    private String demandeurRequete;
    @JsonProperty("actif")
    private boolean actif;
    @JsonProperty("candidatures")
    private Candidature[] candidatures;

    @JsonCreator
    public RequeteTravail(@JsonProperty("titre") String titre, @JsonProperty(
        "description") String description,
                          @JsonProperty("typeTravaux") String typeTravaux,
                          @JsonProperty("dateDebutEspere") String dateDebutEspere, @JsonProperty("demandeurRequete") String demandeurRequete) {
        this.titre = titre;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.dateDebutEspere = dateDebutEspere;
        this.demandeurRequete = demandeurRequete;
        this.id = InscriptionUtils.RandomIDGenerator();
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

    public String getDemandeurRequete() {
        return demandeurRequete;
    }

    public boolean getActif() {
        return actif;
    }

    public String getId() {
        return id;
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

    public void setDemandeurRequete(String demandeurRequete) {
        this.demandeurRequete = demandeurRequete;
    }

    public void setActif(boolean open) {
        this.actif = open;
    }
}
