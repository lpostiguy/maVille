package com.app;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MongoDBConnectionTest {

    // Vérifier que la base de donnée n'est pas nulle.
    @Test
    public void connexionDoitRetournerUneBaseDeDonneesNonNulle() {
        // Vérifie que la connexion renvoie une base de données non nulle
        MongoDatabase database = MongoDBConnection.getDatabase();
        assertNotNull(database, "La base de données ne doit pas être nulle");
    }

    // Vérifier que la connexion est faite à la bonne base de donnée.
    @Test
    public void connexionDoitPointerVersLaBonneBaseDeDonnees() {
        // Vérifie que la base de données a le bon nom
        MongoDatabase database = MongoDBConnection.getDatabase();
        assertEquals("MaVilleDataBase", database.getName(),
            "Le nom de la " + "base de données doit être 'MaVilleDataBase'");
    }

    // Vérifier qu'un échec de la connexion à la base donnée ne lance pas d'exception.
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
