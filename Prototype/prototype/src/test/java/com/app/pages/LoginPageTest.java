package com.app.pages;

import com.app.models.User.Intervenant;
import com.app.models.User.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

import static com.app.pages.InscriptionPage.passwordEncryption;

class LoginPageTest {

    @Test
    void loginIntervenantTest() {
        String simulatedInput = "test@intervenant.com\nTest12345";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Intervenant result = (Intervenant) LoginPage.loginPage();

        assertNotNull(result);
        assertEquals("INTERVENANT", result.getRole());
        assertEquals("Test First Name", result.getFirstName());
        assertEquals("Test Last Name", result.getLastName());
        assertEquals("273283", result.getCityId());
        assertEquals("test@intervenant.com", result.getEmail());
        assertEquals("Entreprise", result.getEntityType());
        assertEquals(1297444382, passwordEncryption.encrypt((""+ result.getPassword())));
    }

    @Test
    void unsuccessfulLoginTest() {
        String simulatedInput = "testtest@intervenant.com\nTest123456\n1";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        User result = LoginPage.loginPage();

        assertNull(result);
    }

    @Test
    void loginResidentTest() {
        String simulatedInput = "test@resident.com\nTest12345";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Resident result = (Resident) LoginPage.loginPage();

        assertNotNull(result);
        assertEquals("RESIDENT", result.getRole());
        assertEquals("Test First Name", result.getFirstName());
        assertEquals("Test Last Name", result.getLastName());
        assertEquals("test@resident.com", result.getEmail());
        assertEquals(1297444382, passwordEncryption.encrypt("" + result.getPassword()));
    }

}
