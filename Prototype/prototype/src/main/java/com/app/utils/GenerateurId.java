package com.app.utils;

import java.util.UUID;

/**
 * Fournit une méthode utilitaire pour générer des identifiants uniques
 * aléatoires sous forme de chaînes de caractères.
 */
public class GenerateurId {

    /**
     * Génère un identifiant unique aléatoire sous forme de chaîne de caractères.
     * L'identifiant généré est basé sur l'UUID (Universal Unique Identifier).
     *
     * @return Un identifiant unique aléatoire sous forme de chaîne de
     * caractères.
     */
    public static String RandomIDGenerator() {
        return UUID.randomUUID().toString();
    }
}
