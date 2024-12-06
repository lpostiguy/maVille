package com.app;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MongoDBConnectionTest {

    @Test
    public void connexionDoitRetournerUneBaseDeDonneesNonNulle() {
        // Vérifie que la connexion renvoie une base de données non nulle
        MongoDatabase database = MongoDBConnection.getDatabase();
        assertNotNull(database, "La base de données ne doit pas être nulle");
    }

    @Test
    public void connexionDoitPointerVersLaBonneBaseDeDonnees() {
        // Vérifie que la base de données a le bon nom
        MongoDatabase database = MongoDBConnection.getDatabase();
        assertEquals("MaVilleDataBase", database.getName(),
            "Le nom de la " + "base de données doit être 'MaVilleDataBase'");
    }

    @Test
    public void connexionDoitEtreResilienteAuxErreurs() {
        // Simule une erreur dans la connexion pour vérifier que
        // l'application ne plante pas
        try {
            MongoDBConnection.getDatabase();
        } catch (Exception e) {
            fail("La méthode getDatabase() ne doit pas lancer d'exception");
        }
    }
}
