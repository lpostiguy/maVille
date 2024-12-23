package com.app.utils;

/**
 * Cette classe fournit des méthodes pour valider divers formats de données.
 */
public class RegexChecker {

    /**
     * Valide si la chaîne de caractères représente une date dans le format "YYYY-MM-DD".
     *
     * @param date La chaîne de caractères représentant une date.
     * @return {@code true} si la date est dans le format valide, sinon
     * {@code false}.
     */
    public static boolean estFormatDateValide(String date) {
        // Valider le format de la date (YYYY-MM-DD)
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(regex);
    }

    /**
     * Valide si la chaîne de caractères représente un numéro de téléphone dans
     * le format "000-000-0000".
     *
     * @param numeroTelephone La chaîne de caractères représentant un numéro de
     *                       téléphone.
     * @return {@code true} si le numéro de téléphone est dans le format valide,
     * sinon {@code false}.
     */
    public static boolean estFormatNumeroTelephoneValide(String numeroTelephone) {
        // Valider le numéro de téléphone, format 000-000-0000
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        return numeroTelephone.matches(regex);
    }

    /**
     * Valide si la chaîne de caractères représente un jour valide de la semaine.
     * La validation est insensible à la casse (par exemple "Lundi" et "lundi"
     * sont valides).
     *
     * @param jour La chaîne de caractères représentant un jour de la semaine.
     * @return {@code true} si le jour est valide, sinon {@code false}.
     */
    public static boolean estJourSemaineValide(String jour) {
        // Valider si l'entrée est un jour valide de la semaine (insensible à la casse)
        String regex = "(?i)Lundi|Mardi|Mercredi|Jeudi|Vendredi|Samedi|Dimanche";
        return jour.matches(regex);
    }

    /**
     * Valide si la chaîne de caractères représente une heure dans le format
     * "HH:mm" (horloge 24 heures).
     *
     * @param heure La chaîne de caractères représentant une heure.
     * @return {@code true} si l'heure est dans le format valide, sinon
     * {@code false}.
     */
    public static boolean estFormatHeureValide(String heure) {
        // Valider le format de l'heure HH:mm (horloge 24 heures)
        String regex = "([01]\\d|2[0-3]):[0-5]\\d";
        return heure.matches(regex);
    }
}
