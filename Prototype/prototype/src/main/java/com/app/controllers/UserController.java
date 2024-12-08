package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.User.User;
import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

                List<Document> notificationsInitiales = new ArrayList<>();
                List<String> preferencesHorairesInitiales = new ArrayList<>();

                Document doc = new Document().append("userId",
                    user.getUserId()).append("firstName",
                    user.getFirstName()).append("lastName",
                    user.getLastName()).append("email", user.getEmail()).append("password", user.getPassword()).append("connected", user.isConnected()).append("userRole", user.getUserRole());

                // Ajouter des champs spécifiques aux sous-classes
                if (user instanceof com.app.models.User.Resident resident) {
                    doc.append("phoneNumber", resident.getPhoneNumber()).append("dateOfBirth", resident.getDateOfBirth()).append("homeAddress", resident.getHomeAddress()).append("notifications", notificationsInitiales).append("preferencesHoraires", preferencesHorairesInitiales).append("boroughId", resident.getBoroughId());

                } else if (user instanceof com.app.models.User.Intervenant intervenant) {
                    doc.append("entityType", intervenant.getEntityType()).append("cityId", intervenant.getCityId());
                }

                collectionUsers.insertOne(doc);

                ctx.status(201).result("Utilisateur ajouté avec " + "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création de l'utilisateur: ",
                    e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.get("/users", ctx -> {
            try {
                List<Document> users =
                    collectionUsers.find().into(new ArrayList<>());
                ctx.json(users);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des " +
                    "utilisateurs: ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });
    }

    public static Document findUserByEmail(String email) {
        return collectionUsers.find(new Document("email", email)).first();
    }

    public static String addNewUser(String userId, String firstName,
                                    String lastName, String email,
                                    String phoneNumber, String dateOfBirth,
                                    String homeAddress, String entityType,
                                    String cityId, String password,
                                    String userRole, String boroughId) {
        try {
            URL url = new URL("http://localhost:8000/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString =
                String.format("{ \"userId\": \"%s\", " + "\"firstName\": " +
                    "\"%s\", \"lastName\": \"%s\", " + "\"email\": \"%s\", " +
                    "\"phoneNumber\": \"%s\", " + "\"dateOfBirth\": \"%s\", " +
                    "\"homeAddress\": \"%s\", " + "\"entityType\": \"%s\", " +
                    "\"cityId\": \"%s\", " + "\"password\": \"%s\", " +
                    "\"userRole\": \"%s\", \"boroughId\": \"%s\"" + "}",
                    userId, firstName, lastName, email, phoneNumber,
                    dateOfBirth, homeAddress, entityType, cityId, password,
                    userRole, boroughId);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201) {
                return "Utilisateur ajoutée avec succès.";
            } else {
                return "Erreur : " + conn.getResponseCode();
            }
        } catch (Exception e) {
            return "Erreur lors de l'ajout de l'utilisateur.";
        }
    }
}
