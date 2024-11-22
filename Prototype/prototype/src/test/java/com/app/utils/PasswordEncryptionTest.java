package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordEncryptionTest {

    @Test
    void encryptPasswordTest() {
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        // Mot de passe exemple
        String password = "12345";
        int expectedHash = password.hashCode();

        // on valide
        assertEquals(expectedHash, passwordEncryption.encrypt(password), 
                     "Le mot de passe chiffré ne correspond pas au résultat attendu.");
    }

    @Test
    void encryptDifferentPasswordsTest() {
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        String password1 = "12345";
        String password2 = "67890";

        // on verifie que les hashs sont différents
        assertNotEquals(passwordEncryption.encrypt(password1), 
                        passwordEncryption.encrypt(password2), 
                        "Les mots de passe différents ne devraient pas produire le même hash.");
    }
}