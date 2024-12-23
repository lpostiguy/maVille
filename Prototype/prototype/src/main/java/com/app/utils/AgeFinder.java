package com.app.utils;

/**
 * Cette classe permet de calculer l'âge d'une personne en fonction de sa date
 * de naissance et de la date actuelle.
 */
public class AgeFinder {

    /**
     * Calcule l'âge d'une personne à partir de sa date de naissance et de
     * la date actuelle.
     *
     * @param birthDate La date de naissance de la personne au format
     *                 "YYYY-MM-DD".
     * @param currentDate La date actuelle au format "YYYY-MM-DD".
     * @return L'âge de la personne en années entières.
     * @throws IllegalArgumentException Si l'un des paramètres de date est
     * invalide.
     */
    public static int findAge(String birthDate, String currentDate) {

        // Split the currentDate, and the birthDate into parts (year, month,
        // day)
        String[] partsCurrentDate = currentDate.trim().split("-");
        String[] partsBirthDate = birthDate.trim().split("-");

        // Remove leading zeros from the month and day of the currentDate.
        for (int i = 1; i < partsCurrentDate.length; i++) {
            if (partsCurrentDate[i].charAt(0) == '0') {
                // Convert to single digit if necessary
                partsCurrentDate[i] =
                    String.valueOf(partsCurrentDate[i].charAt(1));
            }
        }

        // Remove leading zeros from the month and day of the birthDate.
        for (int i = 1; i < partsBirthDate.length; i++) {
            if (partsBirthDate[i].charAt(0) == '0') {
                if (partsBirthDate[i].charAt(1) != '0') {
                    // Convert to single digit if necessary
                    partsBirthDate[i] =
                        String.valueOf(partsBirthDate[i].charAt(1));
                } else {
                    partsBirthDate[i] = "0"; // Set to “0” if day is “00”
                }
            }
        }

        // Convert parts of the dates into integers
        int yearCurrentDate = Integer.parseInt(partsCurrentDate[0]);
        int monthCurrentDate = Integer.parseInt(partsCurrentDate[1]);
        int dayCurrentDate = Integer.parseInt(partsCurrentDate[2]);

        int yearBirthDate = Integer.parseInt(partsBirthDate[0]);
        int monthBirthDate = Integer.parseInt(partsBirthDate[1]);
        int dayBirthDate = Integer.parseInt(partsBirthDate[2]);

        int yearsDifference = yearCurrentDate - yearBirthDate;
        int monthsDifference = monthCurrentDate - monthBirthDate;
        int daysDifference = dayCurrentDate - dayBirthDate;

        if (monthsDifference > 0) {
            return yearsDifference;
        } else if (monthsDifference == 0 && daysDifference >= 0) {
            return yearsDifference;
        }
        return yearsDifference - 1;
    }
}

