package com.app.controllers;

import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.app.controllers.RequeteTravailController.soumettreRequeteTravail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoumettreRequeteTravailControllerTest {

    private Javalin app;
    private int port = 8000;

    @BeforeEach
    void setup() {
        app = Javalin.create().start(port);
        RequeteTravailController.registerRoutes(app);
    }

    @AfterEach
    void teardown() {
        app.stop();
    }

    @Test
    public void soumettreRequeteTravailTest() {
        assertEquals("Requête de travail ajoutée avec succès.",
            soumettreRequeteTravail("Travaux Maison", "Comptoir de la " +
                "cuisine" + " " + "à réparer.", "Réparation Mineur", "2024-12" +
                "-03", "263edbx2bi67zshn32", true, new ArrayList<>()));
    }
}
