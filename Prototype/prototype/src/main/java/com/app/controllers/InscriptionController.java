package com.app.controllers;

import com.app.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InscriptionController {

    private static final Logger logger =
        LoggerFactory.getLogger(InscriptionController.class);
    private static final MongoCollection<Document> collectionUsers =
        MongoDBConnection.getDatabase().getCollection("users");

    public static String addNewUser(String userId,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    String phoneNumber,
                                    String dateOfBirth,
                                    String homeAddress,
                                    String entityType,
                                    String cityId,
                                    String password,
                                    String userRole) {
        try {
            URL url = new URL("http://localhost:8000/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"userId\": \"%s\", " +
                    "\"firstName\": \"%s\", \"lastName\": \"%s\", " +
                    "\"email\": \"%s\", \"phoneNumber\": \"%s\", " +
                    "\"dateOfBirth\": \"%s\", \"homeAddress\": \"%s\", " +
                    "\"entityType\": \"%s\", \"cityId\": \"%s\", " +
                    "\"password\": \"%s\", \"userRole\": \"%s\""
                    + "}", userId, firstName, lastName, email, phoneNumber,
                dateOfBirth, homeAddress, entityType, cityId, password, userRole);

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
