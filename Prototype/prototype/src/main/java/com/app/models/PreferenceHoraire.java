package com.app.models;

import java.util.Objects;

/**
 * Cette classe représente une préférence horaire d'un utilisateur,
 * avec un jour de la semaine et une plage horaire (heure de début et heure de
 * fin).
 */
public class PreferenceHoraire {
    private String jour;
    private String heureDebut;
    private String heureFin;

    // Constructeur par défaut (obligatoire pour la désérialisation)
    public PreferenceHoraire() {
    }

    // Constructeur avec paramètres
    public PreferenceHoraire(String jour, String heureDebut, String heureFin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // Getters
    public String getJour() {
        return jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    // Setters
    public void setJour(String jour) {
        this.jour = jour;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    // Méthode toString (pour affichage)
    @Override
    public String toString() {
        return jour + " : " + heureDebut + " - " + heureFin;
    }

    // Méthode equals (pour comparaison)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferenceHoraire that = (PreferenceHoraire) o;
        return Objects.equals(jour, that.jour) &&
            Objects.equals(heureDebut, that.heureDebut) &&
            Objects.equals(heureFin, that.heureFin);
    }
}
