package com.app.pages;

import com.app.models.User.Intervenant;
import com.app.models.User.Resident;
import com.app.models.User.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

import static com.app.pages.InscriptionPage.passwordEncryption;

public class LoginPageTest {

    @Test
    public void loginIntervenantTest() {
        String simulatedInput = "james.williams@intervenant.com\nJames12345";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Intervenant result = (Intervenant) LoginPage.loginPage();

        assertNotNull(result);
        assertEquals("INTERVENANT", result.getUserRole());
        assertEquals("James", result.getFirstName());
        assertEquals("Williams", result.getLastName());
        assertEquals("273283", result.getCityId());
        assertEquals("james.williams@intervenant.com", result.getEmail());
        assertEquals("Entreprise", result.getEntityType());
        assertEquals(1661557291,
            passwordEncryption.encrypt(("" + result.getPassword())));
    }

    @Test
    public void unsuccessfulLoginTest() {
        String simulatedInput = "testtest@intervenant.com\nTest123456\n1";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        User result = LoginPage.loginPage();

        assertNull(result);
    }

    @Test
    public void loginResidentTest() {
        String simulatedInput = "jacob.wayne@resident.com\nWayne12345";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Resident result = (Resident) LoginPage.loginPage();

        assertNotNull(result);
        assertEquals("RESIDENT", result.getUserRole());
        assertEquals("Jacob", result.getFirstName());
        assertEquals("Wayne", result.getLastName());
        assertEquals("jacob.wayne@resident.com", result.getEmail());
        assertEquals(-570026804,
            passwordEncryption.encrypt("" + result.getPassword()));
    }

}
