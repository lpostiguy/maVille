package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexCheckerTest {

    @Test
    void testIsValidDateFormat() {
        // Cas valides
        assertTrue(RegexChecker.isValidDateFormat("2024-11-21"),
            "Une date au format YYYY-MM-DD devrait être valide.");
        assertTrue(RegexChecker.isValidDateFormat("2000-01-01"),
            "Une date au format YYYY-MM-DD devrait être valide.");

        // Cas invalides
        assertFalse(RegexChecker.isValidDateFormat("15-11-2024"),
            "Une date au format DD-MM-YYYY ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidDateFormat("2024/11/15"),
            "Une date au format YYYY/MM/DD ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidDateFormat("2024-1-1"),
            "Une date sans zéro-pading (YYYY-M-D) ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidDateFormat("Date"),
            "Une chaîne qui n'est pas une date ne devrait pas être valide.");
    }

    @Test
    void testIsValidPhoneNumberFormat() {
        // Cas valides
        assertTrue(RegexChecker.isValidPhoneNumberFormat("123-456-7890"),
            "Un numéro de téléphone au format 000-000-0000 devrait être valide.");
        assertTrue(RegexChecker.isValidPhoneNumberFormat("987-654-3210"),
            "Un numéro de téléphone au format 000-000-0000 devrait être valide.");

        // Cas invalides
        assertFalse(RegexChecker.isValidPhoneNumberFormat("1234567890"),
            "Un numéro de téléphone sans tirets ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidPhoneNumberFormat("123-45-67890"),
            "Un numéro de téléphone avec un format incorrect ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidPhoneNumberFormat("Phone Number"),
            "Une chaîne qui n'est pas un numéro ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidPhoneNumberFormat("123-4567-890"),
            "Un numéro de téléphone avec une mauvaise structure ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidPhoneNumberFormat("12-45-89-67-24"),
            "Un numéro de téléphone avec un format incorrect ne devrait pas être valide.");
        assertFalse(RegexChecker.isValidPhoneNumberFormat("+1 1234567890"),
            "Un numéro de téléphone avec une mauvaise structure ne devrait pas être valide.");
    }


}
