package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.Notification;
import com.app.models.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import io.javalin.Javalin;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotificationsController {

    private static final Logger logger =
        LoggerFactory.getLogger(NotificationsController.class);
    private static final MongoCollection<Document> collectionUsers =
        MongoDBConnection.getDatabase().getCollection("users");

    public static void registerRoutes(Javalin app) {

        // Pour chercher toutes les notifications
        app.get("/users/{userId}/notifications/", ctx -> {
            try {
                // Récupérer l'userId depuis l'URL
                String userId = ctx.pathParam("userId");

                // Rechercher l'utilisateur correspondant dans MongoDB
                Document userDoc = collectionUsers.find(new Document("userId"
                    , userId)).first();

                if (userDoc != null) {
                    // Extraire les notifications
                    List<Document> notifications = userDoc.getList(
                        "notifications", Document.class);

                    // Retourner les notifications en JSON
                    ctx.json(notifications);
                } else {
                    // Si l'utilisateur n'est pas trouvé
                    ctx.status(404).result("Utilisateur non trouvé.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des " +
                    "notifications : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.post("/users/{receveurId}/notifications", ctx -> {
            try {
                String receveurId = ctx.pathParam("receveurId");

                Notification notification = ctx.bodyAsClass(Notification.class);

                Document userDoc = collectionUsers.find(new Document("userId"
                    , receveurId)).first();

                if (userDoc == null) {
                    ctx.status(404).result("Utilisateur avec l'ID spécifié non trouvé.");
                    return;
                }

                // Construire la notification en tant que Document
                Document notificationDoc = new Document()
                    .append("id", notification.getId())
                    .append("msg", notification.getMsg())
                    .append("vu", notification.isVu());

                // Ajouter la notification à la liste des notifications de l'utilisateur
                var updateResult = collectionUsers.updateOne(
                    new Document("userId", receveurId),
                    new Document("$push", new Document("notifications", notificationDoc))
                );

                if (updateResult.getModifiedCount() > 0) {
                    ctx.status(201).result("Notification envoyée avec succès.");
                } else {
                    ctx.status(500).result("Erreur lors de l'ajout de la notification.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de l'envoi de la notification : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        // Pour chercher les notifications non vues
        app.get("/users/{userId}/notifications/nonVue", ctx -> {
            try {
                String userId = ctx.pathParam("userId");

                Document userDoc = collectionUsers.find(new Document("userId"
                    , userId)).first();
                if (userDoc != null) {
                    List<Document> notifications = userDoc.getList(
                        "notifications", Document.class);
                    // Filtrer uniquement les notifications non lues
                    List<Document> unreadNotifications =
                        notifications.stream().filter(notification -> !notification.getBoolean("vu")).toList();

                    ctx.json(unreadNotifications);
                } else {
                    ctx.status(404).result("Utilisateur non trouvé.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des " +
                    "notifications non lues : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.patch("/users/{userId}/notifications/update/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                String userId = ctx.pathParam("userId");
                Document updates = Document.parse(ctx.body());

                // Filtre : chercher l'utilisateur et la notification spécifique
                Document filter = new Document("userId", userId).append(
                    "notifications.id", id);

                // Mise à jour : cibler uniquement la notification spécifique
                Document update = new Document("$set", new Document(
                    "notifications.$[elem].vu", updates.getBoolean("vu")));

                // Utiliser un tableau de filtres pour identifier l'élément
                // spécifique
                var result = collectionUsers.updateOne(filter, update,
                    new UpdateOptions().arrayFilters(List.of(new Document(
                        "elem.id", id))));

                if (result.getModifiedCount() > 0) {
                    ctx.status(200).result("Notification mise à jour avec " +
                        "succès.");
                } else {
                    ctx.status(404).result("Aucune notification trouvée pour " +
                        "l'ID spécifié.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour de la " +
                    "notification : ", e);
                ctx.status(500).result("Erreur serveur : " + e.getMessage());
            }
        });
    }


    public static boolean mettreAJourStatutNotification(Boolean vu, User user
        , List<Notification> notifications) {
        String userId = user.getUserId();

        boolean allUpdatesSuccessful = false;

        for (Notification notification : notifications) {
            try {
                // Vérification de l'identifiant de la requête
                String idNotification = notification.getId();
                if (idNotification == null || idNotification.isEmpty()) {
                    System.err.println("L'identifiant 'id' est manquant ou " +
                        "invalide.");
                    allUpdatesSuccessful = false;
                    continue;
                }

                // Construire l'URL de la requête
                String url = "http://localhost:8000/users/" + userId +
                    "/notifications/update/" + idNotification;

                // Construire le corps de la requête JSON
                String chargeJson = String.format("{\"vu\": %b}", vu);

                // Créer un client HTTP
                HttpClient client = HttpClient.newHttpClient();

                // Construire la requête PATCH
                HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(url)).method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(chargeJson)).header("Content-Type", "application/json; utf-8").build();

                // Envoyer la requête
                HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

                // Vérifier la réponse
                if (response.statusCode() != 200) {
                    System.err.println("Erreur lors de la mise à jour : HTTP "
                        + response.statusCode());
                    System.err.println("Message d'erreur : " + response.body());
                    allUpdatesSuccessful = false;
                }

            } catch (Exception e) {
                System.err.println("Une exception s'est produite : " + e.getMessage());
                e.printStackTrace();
                allUpdatesSuccessful = false;
            }
        }

        return allUpdatesSuccessful;
    }

    public static List<Document> consulterNotificationsNonLues(User user) {
        try {
            // Construire l'URL pour l'endpoint des notifications non lues
            URL url =
                new URL("http://localhost:8000/users/" + user.getUserId() +
                    "/notifications/nonVue");
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

                // Désérialiser la réponse JSON en une liste de documents (ou
                // un autre type)
                ObjectMapper objectMapper = new ObjectMapper();
                List<Document> notifications =
                    objectMapper.readValue(reponse.toString(),
                        new TypeReference<List<Document>>() {
                });

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

    public static List<Document> consulterToutesLesNotifications(User user) {
        try {
            // Construire l'URL pour l'endpoint des notifications non lues
            URL url =
                new URL("http://localhost:8000/users/" + user.getUserId() +
                    "/notifications");
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

                // Désérialiser la réponse JSON en une liste de documents (ou
                // un autre type)
                ObjectMapper objectMapper = new ObjectMapper();
                List<Document> notifications =
                    objectMapper.readValue(reponse.toString(),
                        new TypeReference<List<Document>>() {
                });

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

    public static void envoyerNotification(String msg,
                                           String receveurId) {
        try {
            URL url = new URL("http://localhost:8000/users/" + receveurId + "/notifications");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"id\": \"%s\", " +
                    "\"msg\": \"%s\", \"vu\": %b}", receveurId, msg, false);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201) {
            } else {
                conn.getResponseCode();
            }
        } catch (Exception e) {
        }
    }
}
