package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.RequeteTravail;
import com.app.models.User.User;
import com.app.utils.JsonFormatting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import io.javalin.Javalin;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                if (requeteTravail.getTitre() == null || requeteTravail.getDescription() == null || requeteTravail.getTypeTravaux() == null || requeteTravail.getDateDebutEspere() == null || requeteTravail.getDemandeurRequete() == null) {
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
                    requeteTravail.getDemandeurRequete()).append("open", requeteTravail.getOpen());
                collectionRequeteTravail.insertOne(doc);

                ctx.status(201).result("Requête de travail ajoutée avec " +
                    "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création de la requête de " + "travail : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.get("/requete-travail/{demandeurRequete}", ctx -> {
            try {
                String demandeurRequete = ctx.pathParam("demandeurRequete");

                ObjectMapper mapper = new ObjectMapper();

                List<RequeteTravail> requetes = new ArrayList<>();
                collectionRequeteTravail.find(new Document("demandeurRequete", demandeurRequete))
                        .forEach(document -> {
                            try {
                                RequeteTravail requete = mapper.readValue(document.toJson(), RequeteTravail.class);
                                requetes.add(requete);
                            } catch (Exception e) {
                                logger.error("Erreur lors du mapping du document: " + e);
                            }
                        });
                ctx.json(requetes);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des requêtes de "
                    + "travail : ", e);
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

    // Méthode pour soumettre une nouvelle requête de travail
    public static String soumettreRequeteTravail(String titre,
                                                 String description,
                                                 String typeTravaux,
                                                 String dateDebutEspere,
                                                 String demandeurRequete,
                                                 boolean isOpen) {
        try {
            URL url = new URL("http://localhost:8000/requete-travail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"titre\": \"%s\", " +
                    "\"description\": \"%s\", \"typeTravaux\": \"%s\", " +
                    "\"dateDebutEspere\": \"%s\", \"demandeurRequete\": \"%s\", \"open\": \"%s\""
                    + "}", titre, description, typeTravaux, dateDebutEspere,
                demandeurRequete, isOpen);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201) {
                return "Requête de travail ajoutée avec succès.";
            } else {
                return "Erreur : " + conn.getResponseCode();
            }
        } catch (Exception e) {
            return "Erreur lors de la soumission de la requête " + "de" + " " +
                "travail.";
        }
    }

    // Méthode pour consulter les requêtes de travail de tous les utilisateurs
    public static void consulterRequetesTravail() {
        try {
            URL url = new URL("http://localhost:8000/requete-travail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                StringBuilder reponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        reponse.append(scanner.nextLine());
                    }
                }
                String formattedJson =
                    JsonFormatting.jsonToText(reponse.toString());
                System.out.println(formattedJson);

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static List<RequeteTravail> consulterRequetesTravail(User user) {
        try {
            URL url = new URL("http://localhost:8000/requete-travail/" + user.getUserId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                StringBuilder reponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        reponse.append(scanner.nextLine());
                    }
                }
                ObjectMapper objectMapper = new ObjectMapper();
                List<RequeteTravail> requetes = objectMapper.readValue(
                    reponse.toString(), new TypeReference<List<RequeteTravail>>() {}
                );

                return requetes;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

}
