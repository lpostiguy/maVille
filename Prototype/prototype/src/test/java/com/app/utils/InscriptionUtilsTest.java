package com.app.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InscriptionUtilsTest {

    @Test
    public void isAbove16Test() {
        // Cas 1 : Âge supérieur à 16 ans
        String validDateOfBirth = "2000-01-01";
        assertTrue(InscriptionUtils.isAgeAbove16(validDateOfBirth), "Une " +
            "personne née le 2000-01-01 devrait être validée comme ayant " +
            "plus" + " de 16 ans.");

        // Cas 2 : Âge exactement 16 ans
        String exact16YearsOld = LocalDate.now().minusYears(16).toString();
        assertTrue(InscriptionUtils.isAgeAbove16(exact16YearsOld), "Une " +
            "personne née il y a exactement 16 ans aujourd'hui devrait être " + "validée.");

        // Cas 3 : Âge inférieur à 16 ans
        String below16YearsOld = "2010-01-01";
        assertFalse(InscriptionUtils.isAgeAbove16(below16YearsOld), "Une " +
            "personne née le 2010-01-01 ne devrait pas être validée comme " + "ayant plus de 16 ans.");

        // Cas 4 : Date future
        String futureDateOfBirth = "2025-01-01";
        assertFalse(InscriptionUtils.isAgeAbove16(futureDateOfBirth),
            "Une " + "date de naissance dans le futur ne devrait pas être " +
                "validée.");
    }

    @Test
    public void isValidPasswordTest() {
        // Cas où le mot de passe est valide
        assertEquals("", InscriptionUtils.isValidPassword("Password1"),
            "Un " + "mot de passe valide devrait retourner une chaîne vide.");

        // Cas où le mot de passe est trop court
        assertEquals("Minimum of 8 character required",
            InscriptionUtils.isValidPassword("Pass1"), "Un mot de passe trop "
                + "court devrait retourner un message d'erreur.");

        // Cas où le mot de passe manque une majuscule et un chiffre
        assertEquals("Minimum of 1 capital letter and 1 number required",
            InscriptionUtils.isValidPassword("password"),
            "Un mot de passe " + "sans majuscule et chiffre devrait retourner" +
                " un message " + "d'erreur.");

        // Cas où le mot de passe manque un chiffre
        assertEquals("Minimum of 1 capital letter and 1 number required",
            InscriptionUtils.isValidPassword("PASSWORD"),
            "Un mot de passe " + "sans chiffre devrait retourner un message " +
                "d'erreur.");
    }

    @Test
    public void isSamePasswordTest() {
        // Cas où les mots de passe sont identiques
        assertTrue(InscriptionUtils.isSamePassword("Password1", "Password1"),
            "Les mots de passe identiques, même avec des espaces aux " +
                "extrémités, devraient être considérés comme identiques.");

        // Cas où les mots de passes ne sont pas identiques
        assertFalse(InscriptionUtils.isSamePassword(" Password1 ", "Password1"
        ), "Les mots de passe avec des espaces ne devraient pas être " +
            "identiques.");

        assertFalse(InscriptionUtils.isSamePassword("Password1", "password1")
            , "Les mots de passe devraient être sensibles à la casse.");
    }

}

