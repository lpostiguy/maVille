package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.User.User;
import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final Logger logger =
        LoggerFactory.getLogger(UserController.class);
    private static final MongoCollection<Document> collectionUsers =
        MongoDBConnection.getDatabase().getCollection("users");

    public static void registerRoutes(Javalin app) {

        app.post("/users", ctx -> {
            try {
                User user = ctx.bodyAsClass(User.class);

                Document initialNotifs = new Document();

                Document doc = new Document()
                    .append("userId", user.getUserId())
                    .append("firstName", user.getFirstName())
                    .append("lastName", user.getLastName())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("connected", user.isConnected())
                    .append("userRole", user.getUserRole());

                // Ajouter des champs spécifiques aux sous-classes
                if (user instanceof com.app.models.User.Resident resident) {
                    doc.append("phoneNumber", resident.getPhoneNumber())
                        .append("dateOfBirth", resident.getDateOfBirth())
                        .append("homeAddress", resident.getHomeAddress())
                        .append("notifications", initialNotifs)
                        .append("boroughId", resident.getBoroughId());

                } else if (user instanceof com.app.models.User.Intervenant intervenant) {
                    doc.append("entityType", intervenant.getEntityType())
                        .append("cityId", intervenant.getCityId());
                }

                collectionUsers.insertOne(doc);

                ctx.status(201).result("Utilisateur ajouté avec " +
                    "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création de l'utilisateur: ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.get("/users", ctx -> {
            try {
                List<Document> requetes =
                    collectionUsers.find().into(new ArrayList<>());
                ctx.json(requetes);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des utilisateurs: ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });
    }

    public static Document findUserByEmail(String email) {
        return collectionUsers.find(new Document("email", email)).first();
    }
}
