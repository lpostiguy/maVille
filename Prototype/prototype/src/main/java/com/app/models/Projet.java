package com.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.app.utils.GenerateurId.RandomIDGenerator;

/**
 * Cette classe représente un projet avec ses caractéristiques, incluant les
 * dates, la description, les zones affectées, et son statut.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Projet {

    // Attributs
    private String id;
    private String idSoumissionnaireProjet;
    private String dateDebut;
    private String dateFin;
    private String description;
    private String titre;
    private String typeTravaux;
    private List<String> quartiersAffectes;
    private List<String> ruesAffectees;
    private String statut;

    // Constructeur
    @JsonCreator
    public Projet(
        @JsonProperty("id") String id,
        @JsonProperty("idSoumissionnaireProjet") String idSoumissionnaireProjet,
        @JsonProperty("dateDebut") String dateDebut,
        @JsonProperty("dateFin") String dateFin,
        @JsonProperty("description") String description,
        @JsonProperty("titre") String titre,
        @JsonProperty("typeTravaux") String typeTravaux,
        @JsonProperty("quartiersAffectes") List<String> quartiersAffectes,
        @JsonProperty("ruesAffectees") List<String> ruesAffectees,
        @JsonProperty("statut") String statut) {
        this.id = id;
        this.idSoumissionnaireProjet = idSoumissionnaireProjet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.titre = titre;
        this.typeTravaux = typeTravaux;
        this.quartiersAffectes = quartiersAffectes;
        this.ruesAffectees = ruesAffectees;
        this.statut = statut != null ? statut : "Prévu";
    }

    // Constructeur par défaut
    public Projet() {
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSoumissionnaireProjet() {
        return idSoumissionnaireProjet;
    }

    public void setIdSoumissionnaireProjet(String idSoumissionnaireProjet) {
        this.idSoumissionnaireProjet = idSoumissionnaireProjet;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTypeTravaux() {
        return typeTravaux;
    }

    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    public List<String> getQuartiersAffectes() {
        return quartiersAffectes;
    }

    public void setQuartiersAffectes(List<String> quartierAffectes) {
        this.quartiersAffectes = quartierAffectes;
    }

    public List<String> getRuesAffectees() {
        return ruesAffectees;
    }

    public void setRuesAffectees(List<String> ruesAffectees) {
        this.ruesAffectees = ruesAffectees;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // Méthode toString (optionnel)
    @Override
    public String toString() {
        return
            "\n------------------------------------" +
            "\nTitre : '" + titre + '\'' +
                "\nDescription : '" + description + '\'' +
            "\nDate de Debut : '" + dateDebut + '\'' +
            "\nDate de Fin : '" + dateFin + '\'' +
            "\nType de Travaux : '" + typeTravaux + '\'' +
            "\nQuartiers Affectés : " + quartiersAffectes +
            "\nRues Affectées : " + ruesAffectees +
            "\nStatut : '" + statut + '\'';
    }
}
