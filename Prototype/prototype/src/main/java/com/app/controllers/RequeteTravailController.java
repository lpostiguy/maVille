package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.RequeteTravail;
import com.app.models.User.User;
import com.app.utils.JsonFormatting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
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

public class RequeteTravailController {

    private static final Logger logger =
        LoggerFactory.getLogger(RequeteTravailController.class);
    private static final MongoCollection<Document> collectionRequeteTravail =
        MongoDBConnection.getDatabase().getCollection("requete-travail");

    public static void registerRoutes(Javalin app) {

        app.get("/requete-travail", ctx -> {
            try {
                Document filter = new Document("actif", true);

                List<Document> requetes =
                    collectionRequeteTravail.find(filter).into(new ArrayList<>());
                ctx.json(requetes);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des requêtes de "
                    + "travail : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.get("/requete-travail/{demandeurRequete}", ctx -> {
            try {
                String demandeurRequete = ctx.pathParam("demandeurRequete");

                ObjectMapper mapper = new ObjectMapper();

                List<RequeteTravail> requetes = new ArrayList<>();
                collectionRequeteTravail.find(new Document("demandeurRequete"
                    , demandeurRequete)).forEach(document -> {
                    try {
                        RequeteTravail requete =
                            mapper.readValue(document.toJson(),
                                RequeteTravail.class);
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

        app.post("/requete-travail", ctx -> {
            try {
                RequeteTravail requeteTravail = ctx.bodyAsClass(RequeteTravail.class);

                // Validation des champs requis
                if (requeteTravail.getTitre() == null || requeteTravail.getDescription() == null || requeteTravail.getTypeTravaux() == null || requeteTravail.getDateDebutEspere() == null || requeteTravail.getDemandeurRequete() == null) {
                    ctx.status(400).result("Tous les champs sont requis.");
                    return;
                }

                // Insertion dans MongoDB
                Document doc = new Document("id", requeteTravail.getId()).append("titre", requeteTravail.getTitre()).append("description", requeteTravail.getDescription()).append("typeTravaux", requeteTravail.getTypeTravaux()).append("dateDebutEspere", requeteTravail.getDateDebutEspere()).append("demandeurRequete", requeteTravail.getDemandeurRequete()).append("actif", requeteTravail.getActif());
                collectionRequeteTravail.insertOne(doc);

                ctx.status(201).result("Requête de travail ajoutée avec " + "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création de la requête de " + "travail : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.patch("/requete-travail/{id}", ctx -> {
            try {
                // Récupérer le paramètre de l'URL
                String id = ctx.pathParam("id");

                // Construire le filtre pour identifier le document
                Document filter = new Document("id", id); // Utiliser le
                // champ "id" (String)

                // Récupérer les mises à jour du corps de la requête
                Document updates = Document.parse(ctx.body());

                // Valider les champs
                if (!updates.containsKey("actif")) {
                    ctx.status(400).result("Le champ 'actif' est requis.");
                    return;
                }

                // Construire les modifications
                Document update = new Document("$set", updates);

                // Mettre à jour dans MongoDB
                var result = collectionRequeteTravail.updateOne(filter, update);

                // Vérifier si un document a été mis à jour
                if (result.getModifiedCount() > 0) {
                    ctx.status(200).result("Requête mise à jour avec succès.");
                } else {
                    ctx.status(404).result("Aucune requête trouvée pour l'ID " + "spécifié.");
                }
            } catch (Exception e) {
                ctx.status(500).result("Erreur serveur : " + e.getMessage());
            }
        });

        app.delete("/requete-travail/{id}", ctx -> {
            try {
                // Récupérer le paramètre de l'URL
                String id = ctx.pathParam("id");

                // Construire le filtre pour identifier le document
                Document filter = new Document("id", id); // Utiliser le
                // champ "id" (String)

                // Mettre à jour dans MongoDB
                var result = collectionRequeteTravail.deleteOne(filter);

                // Vérifier si un document a été mis à jour
                if (result.getDeletedCount() > 0) {
                    ctx.status(200).result("Requête effacé avec succès.");
                } else {
                    ctx.status(404).result("Aucune requête trouvée pour l'ID " + "spécifié.");
                }
            } catch (Exception e) {
                ctx.status(500).result("Erreur serveur : " + e.getMessage());
            }
        });
    }

    // Méthode pour soumettre une nouvelle requête de travail
    public static String soumettreRequeteTravail(String titre,
                                                 String description,
                                                 String typeTravaux,
                                                 String dateDebutEspere,
                                                 String demandeurRequete,
                                                 boolean actif) {
        try {
            URL url = new URL("http://localhost:8000/requete-travail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"titre\": \"%s\", " +
                "\"description\": \"%s\", \"typeTravaux\": \"%s\", " +
                "\"dateDebutEspere\": \"%s\", \"demandeurRequete\": \"%s\", " +
                "\"actif\": \"%s\"" + "}", titre, description, typeTravaux,
                dateDebutEspere, demandeurRequete, actif);

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
            return "Erreur lors de la soumission de la requête " + "de" + " " + "travail.";
        }
    }

    // Méthode pour consulter les requêtes de travail de tous les utilisateurs
    public static String consulterRequetesTravail() {
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
                    JsonFormatting.jsonToTextRequeteTravail(reponse.toString());
                return formattedJson;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "Aucune requête de travail n'est disponible";
    }

    public static List<RequeteTravail> consulterRequetesTravail(User user) {
        try {
            URL url =
                new URL("http://localhost:8000/requete-travail/" + user.getUserId());
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
                List<RequeteTravail> requetes =
                    objectMapper.readValue(reponse.toString(),
                        new TypeReference<List<RequeteTravail>>() {
                });

                return requetes;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static boolean mettreAJourStatutRequeteTravail(Boolean actif,
                                                          RequeteTravail requeteTravail) {
        try {
            // Vérification de l'identifiant de la requête
            String idValue = requeteTravail.getId();
            if (idValue == null) {
                throw new IllegalArgumentException("L'identifiant 'id' est " +
                    "manquant ou invalide.");
            }

            // Construire l'URL de la requête
            String url = "http://localhost:8000/requete-travail/" + idValue;

            // Construire le corps de la requête JSON
            String chargeJson = String.format("{\"actif\": %b}", actif);

            // Créer un client HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Construire la requête PATCH
            HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).method("PATCH",
                    HttpRequest.BodyPublishers.ofString(chargeJson)).header(
                        "Content-Type", "application/json; utf-8").build();

            // Envoyer la requête
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

            // Vérifier la réponse
            if (response.statusCode() == 200) {
                if(actif) {
                    System.out.println("Requête désarchivé");
                }
                else {
                    System.out.println("Requête Archivé");
                }
                return true;
            } else {
                System.err.println("Erreur lors de la mise à jour : HTTP " + response.statusCode());
                System.err.println("Message d'erreur : " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteRequeteTravail(RequeteTravail requeteTravail) {
        try {
            // Vérification de l'identifiant de la requête
            String idValue = requeteTravail.getId();
            if (idValue == null) {
                throw new IllegalArgumentException("L'identifiant 'id' est " +
                    "manquant ou invalide.");
            }

            // Construire l'URL de la requête
            URL url =
                new URL("http://localhost:8000/requete-travail/" + idValue);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Vérifier la réponse
            if (conn.getResponseCode() == 200) {
                System.out.println("Requête de travail effacé");
                return true;
            } else {
                System.err.println("Erreur lors de la mise à jour : HTTP " + conn.getResponseCode());
                System.err.println("Message d'erreur : " + conn.getResponseMessage());
            }
        } catch (Exception e) {
            System.err.println("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
