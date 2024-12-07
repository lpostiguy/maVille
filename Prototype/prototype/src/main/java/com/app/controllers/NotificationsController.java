package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotificationsController {

    private static final Logger logger =
        LoggerFactory.getLogger(RequeteTravailController.class);
    private static final MongoCollection<Document> collectionUsers =
        MongoDBConnection.getDatabase().getCollection("users");

    public static void registerRoutes(Javalin app) {

        // Pour chercher toutes les notifications
        app.get("/users/{userId}/notifications/", ctx -> {
            try {
                // Récupérer l'userId depuis l'URL
                String userId = ctx.pathParam("userId");

                // Rechercher l'utilisateur correspondant dans MongoDB
                Document userDoc = collectionUsers.find(new Document("userId", userId)).first();

                if (userDoc != null) {
                    // Extraire les notifications
                    List<Document> notifications = userDoc.getList("notifications", Document.class);

                    // Retourner les notifications en JSON
                    ctx.json(notifications);
                } else {
                    // Si l'utilisateur n'est pas trouvé
                    ctx.status(404).result("Utilisateur non trouvé.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des notifications : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        // Pour chercher les notifications non vues
        app.get("/users/{userId}/notifications/nonVue", ctx -> {
            try {
                String userId = ctx.pathParam("userId");

                Document userDoc = collectionUsers.find(new Document("userId", userId)).first();
                if (userDoc != null) {
                    List<Document> notifications = userDoc.getList("notifications", Document.class);
                    // Filtrer uniquement les notifications non lues
                    List<Document> unreadNotifications = notifications.stream()
                        .filter(notification -> !notification.getBoolean("vu"))
                        .toList();

                    ctx.json(unreadNotifications);
                } else {
                    ctx.status(404).result("Utilisateur non trouvé.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des notifications non lues : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });
    }

    public static List<Document> consulterNotificationsNonLues(User user) {
        try {
            // Construire l'URL pour l'endpoint des notifications non lues
            URL url = new URL("http://localhost:8000/users/" + user.getUserId() + "/notifications/nonVue");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Vérifier le code de réponse HTTP
            if (conn.getResponseCode() == 200) {
                StringBuilder reponse = new StringBuilder();

                // Lire la réponse JSON
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        reponse.append(scanner.nextLine());
                    }
                }

                // Désérialiser la réponse JSON en une liste de documents (ou un autre type)
                ObjectMapper objectMapper = new ObjectMapper();
                List<Document> notifications = objectMapper.readValue(
                    reponse.toString(),
                    new TypeReference<List<Document>>() {}
                );

                return notifications;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retourner une liste vide en cas d'erreur
        return new ArrayList<>();
    }
}
