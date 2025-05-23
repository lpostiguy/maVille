package com.app.utils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Cette classe contient des méthodes utilitaires pour la gestion de
 * l'inscription des utilisateurs.
 * Elle fournit des méthodes pour valider des mots de passe, vérifier si les
 * mots de passe sont identiques, et vérifier si l'utilisateur a l'âge requis
 * pour s'inscrire (16 ans).
 */
public class InscriptionUtils {

    public static boolean isSamePassword(String password1, String password2) {
        return Objects.equals(password1, password2);
    }

    public static String isValidPassword(String password) {
        String returnMessage = "";
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        if (password.length() < 8) {
            returnMessage = "Minimum of 8 character required";
        } else if (!password.matches(regex)) {
            returnMessage = "Minimum of 1 capital letter and 1" + " " +
                "number" + " " + "required";
        }
        return returnMessage;
    }

    public static boolean isAgeAbove16(String dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        int age = AgeFinder.findAge(dateOfBirth, currentDate.toString());
        return age >= 16;
    }
}
