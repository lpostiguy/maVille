package com.app.utils;

public class RegexChecker {

    public static boolean estFormatDateValide(String date) {
        // Valider le format de la date (YYYY-MM-DD)
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(regex);
    }

    public static boolean estFormatNumeroTelephoneValide(String numeroTelephone) {
        // Valider le numéro de téléphone, format 000-000-0000
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        return numeroTelephone.matches(regex);
    }

    public static boolean estJourSemaineValide(String jour) {
        // Valider si l'entrée est un jour valide de la semaine (insensible à la casse)
        String regex = "(?i)Lundi|Mardi|Mercredi|Jeudi|Vendredi|Samedi|Dimanche";
        return jour.matches(regex);
    }

    public static boolean estFormatHeureValide(String heure) {
        // Valider le format de l'heure HH:mm (horloge 24 heures)
        String regex = "([01]\\d|2[0-3]):[0-5]\\d";
        return heure.matches(regex);
    }
}
