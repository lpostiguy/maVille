package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PasswordEncryptionTest {

    @Test
    public void encryptPasswordTest() {
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        // Mot de passe exemple
        String password = "Wayne12345";
        int expectedHash = password.hashCode();

        // on valide
        assertEquals(expectedHash, passwordEncryption.encrypt(password), "Le " +
            "mot de passe chiffré ne correspond pas au résultat attendu.");
    }

    @Test
    public void encryptDifferentPasswordsTest() {
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        String password1 = "Wayne12345";
        String password2 = "Marc12345";

        // on verifie que les hashs sont différents
        assertNotEquals(passwordEncryption.encrypt(password1),
            passwordEncryption.encrypt(password2), "Les mots de passe " +
                "différents ne devraient pas produire le même hash.");
    }
}
