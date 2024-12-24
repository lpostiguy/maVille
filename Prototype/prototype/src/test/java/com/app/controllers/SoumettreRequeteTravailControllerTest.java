package com.app.controllers;

import com.app.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.app.controllers.RequeteTravailController.soumettreRequeteTravail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoumettreRequeteTravailControllerTest {

    @Mock
    private MongoCollection mockCollection;

    private Javalin app;
    private int port = 8000;

    // Vérifier que la requête est soumise avec succès lorsque la connexion est
    // valide et que tous les champs sont remplis.
    @Test
    public void soumettreRequeteTravailTest() {
        app = Javalin.create().start(port);
        mockCollection = Mockito.mock(MongoCollection.class);
        RequeteTravailController.setCollectionRequeteTravail(MongoDBConnection.getDatabase().getCollection("requete-travail"));
        RequeteTravailController.registerRoutes(app);
        assertEquals("Requête de travail ajoutée avec succès.",
            soumettreRequeteTravail("Travaux Maison", "Comptoir de la " +
                "cuisine" + " " + "à réparer.", "Réparation Mineur", "2024-12" +
                "-03", "263edbx2bi67zshn32", true));
        app.stop();
        app.close();
    }

    // Vérifier que la requête n'est pas soumise lorsqu'un ou plusieurs champs
    // sont null.
    @Test
    public void soumettreRequeteTravailMissingFieldTest() {
        app = Javalin.create().start(port);
        mockCollection = Mockito.mock(MongoCollection.class);
        RequeteTravailController.setCollectionRequeteTravail(MongoDBConnection.getDatabase().getCollection("requete-travail"));
        RequeteTravailController.registerRoutes(app);
        assertEquals("Erreur : 400",
            soumettreRequeteTravail(null, null, "Réparation Mineur", "2024-12" +
                "-03", "263edbx2bi67zshn32", true));
        app.stop();
        app.close();
    }

    // Vérifier qu'un message d'erreur est retourné lorsqu'il y a un problème
    // avec la connexion à l'API.
    @Test
    public void soumettreRequeteTravailServerError() {
        app = Javalin.create().start(port);
        mockCollection = Mockito.mock(MongoCollection.class);
        app.stop();
        app.close();
        assertEquals("Erreur lors de la soumission de la requête " + "de" + " " + "travail.",
            soumettreRequeteTravail("Travaux Maison", "Comptoir de la " +
                "cuisine" + " " + "à réparer.", "Réparation Mineur", "2024-12" +
                "-03", "263edbx2bi67zshn32", true));
    }
}
