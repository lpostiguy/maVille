package com.app.utils;

/**
 * Cette classe fournit une méthode pour effectuer une opération de hachage sur
 * un mot de passe donné.
 */
public class PasswordEncryption {

    /**
     * Hache le mot de passe en utilisant la méthode `hashCode()` de la classe "String".
     *
     * @param password Le mot de passe à hacher.
     * @return Un entier représentant le code de hachage du mot de passe.
     */
    public int encrypt(String password) {
        return password.hashCode();
    }
}
