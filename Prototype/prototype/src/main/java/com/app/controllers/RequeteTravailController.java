package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.RequeteTravail;
import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RequeteTravailController {

    private static final Logger logger =
        LoggerFactory.getLogger(RequeteTravailController.class);
    private static final MongoCollection<Document> collectionRequeteTravail =
        MongoDBConnection.getDatabase().getCollection("requete-travail");

    public static void registerRoutes(Javalin app) {

        app.post("/requete-travail", ctx -> {
            try {
                RequeteTravail requeteTravail =
                    ctx.bodyAsClass(RequeteTravail.class);

                // Validation des champs requis
                if (requeteTravail.getTitre() == null || requeteTravail.getDescription() == null || requeteTravail.getTypeTravaux() == null || requeteTravail.getDateDebutEspere() == null || requeteTravail.getDemandeurRequeteId() == null) {
                    ctx.status(400).result("Tous les champs sont requis.");
                    return;
                }

                // Insertion dans MongoDB
                Document doc = new Document("titre",
                    requeteTravail.getTitre()).append("description",
                    requeteTravail.getDescription()).append("typeTravaux",
                    requeteTravail.getTypeTravaux()).append("dateDebutEspere"
                    , requeteTravail.getDateDebutEspere()).append(
                        "demandeurRequete",
                    requeteTravail.getDemandeurRequeteId());
                collectionRequeteTravail.insertOne(doc);

                ctx.status(201).result("Requête de travail ajoutée avec " +
                    "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création de la requête de " + "travail : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.get("/requete-travail", ctx -> {
            try {
                List<Document> requetes =
                    collectionRequeteTravail.find().into(new ArrayList<>());
                ctx.json(requetes);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des requêtes de "
                    + "travail : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });
    }
}
