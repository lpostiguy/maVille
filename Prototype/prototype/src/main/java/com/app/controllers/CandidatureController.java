package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.Candidature;
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
import java.util.List;

import static com.app.utils.GenerateurId.RandomIDGenerator;

/**
 * Contrôleur responsable de la gestion des candidatures dans l'application.
 * Cette classe permet de soumettre, modifier, supprimer et récupérer les
 * candidatures associées à des requêtes de travail dans la base de données
 * MongoDB.
 */
public class CandidatureController {

    private static final Logger logger =
        LoggerFactory.getLogger(CandidatureController.class);
    private static final MongoCollection<Document> collectionRequetesTravail =
        MongoDBConnection.getDatabase().getCollection("requete-travail");

    /**
     * Enregistre les routes HTTP pour la gestion des candidatures.
     * Ces routes incluent la création, la suppression, et la mise à jour
     * des candidatures.
     *
     * @param app Instance de l'application Javalin pour l'enregistrement des
     *           routes.
     */
    public static void registerRoutes(Javalin app) {

        // Pour chercher toutes les notifications
        app.get("/requete-travail/{id}/candidatures/", ctx -> {
            try {
                // Récupérer l'id depuis l'URL
                String id = ctx.pathParam("id");

                // Rechercher l'utilisateur correspondant dans MongoDB
                Document requestDoc = collectionRequetesTravail.find(new Document("id"
                    , id)).first();

                if (requestDoc != null) {
                    // Extraire les candidatures
                    List<Document> candidatures = requestDoc.getList(
                        "candidatures", Document.class);

                    // Retourner les candidatures en JSON
                    ctx.json(candidatures);
                } else {
                    // Si la requête n'est pas trouvée
                    ctx.status(404).result("Requête non trouvé.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des " +
                    "candidatures : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.post("/requete-travail/{id}/candidatures", ctx -> {
            try {
                String id = ctx.pathParam("id");

                Candidature candidature = ctx.bodyAsClass(Candidature.class);
                candidature.setId(RandomIDGenerator());

                Document requestDoc = collectionRequetesTravail.find(new Document("id"
                    , id)).first();

                if (requestDoc == null) {
                    ctx.status(404).result("Requête non trouvée.");
                    return;
                }

                // Construire la candidature en tant que Document
                Document candidatureDoc = new Document()
                    .append("id", candidature.getId())
                    .append("dateFin", candidature.getDateFin())
                    .append("dateDebut", candidature.getDateDebut())
                    .append("status", candidature.getStatus())
                    .append("confirmed", candidature.isConfirmed())
                    .append("userId", candidature.getUserId())
                    .append("residentMsg", candidature.getResidentMsg())
                    .append("intervenantMsg", candidature.getIntervenantMsg());

                // Ajouter la notification à la liste des candidatures de la requête
                var updateResult = collectionRequetesTravail.updateOne(
                    new Document("id", id),
                    new Document("$push", new Document("candidatures", candidatureDoc))
                );

                if (updateResult.getModifiedCount() > 0) {
                    ctx.status(201).result("Candidature envoyée avec succès.");
                } else {
                    ctx.status(500).result("Erreur lors de l'ajout de la candidature.");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de l'envoi de la candidature : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.patch("/requete-travail/{id}/candidatures/{candidatureId}/delete", ctx -> {
            String requeteId = ctx.pathParam("id");
            String candidatureId = ctx.pathParam("candidatureId");

            Document requete = collectionRequetesTravail.find(new Document("id", requeteId)).first();
            if (requete == null) {
                ctx.status(404).result("Requête non trouvée.");
                return;
            }

            List<Document> candidatures = requete.getList("candidatures", Document.class);

            // Supprimer la candidature
            candidatures.removeIf(c -> c.getString("id").equals(candidatureId));

            // Sauvegarder la mise à jour dans MongoDB
            collectionRequetesTravail.updateOne(new Document("id", requeteId),
                new Document("$set", new Document("candidatures", candidatures)));

            ctx.status(200).result("Candidature supprimée.");
        });

        app.patch("/requete-travail/{id}/candidatures/{candidatureId}/status", ctx -> {
            String requeteId = ctx.pathParam("id");
            String candidatureId = ctx.pathParam("candidatureId");

            Document requete = collectionRequetesTravail.find(new Document("id", requeteId)).first();
            if (requete == null) {
                ctx.status(404).result("Requête non trouvée.");
                return;
            }

            List<Document> candidatures = requete.getList("candidatures", Document.class);
            Document candidature = candidatures.stream()
                .filter(c -> c.getString("id").equals(candidatureId))
                .findFirst().orElse(null);

            if (candidature == null) {
                ctx.status(404).result("Candidature non trouvée.");
                return;
            }

            // Mettre à jour les champs modifiables
            Document updateFields = ctx.bodyAsClass(Document.class);
            if (updateFields.containsKey("status")) {
                candidature.put("status", updateFields.getString("status"));
                candidature.put("residentMsg", updateFields.getString("residentMsg"));
            }

            // Sauvegarder la mise à jour dans MongoDB
            collectionRequetesTravail.updateOne(new Document("id", requeteId),
                new Document("$set", new Document("candidatures", candidatures)));

            ctx.status(200).result("Candidature mise à jour.");
        });

        app.patch("/requete-travail/{id}/candidatures/{candidatureId}/isconfirmed", ctx -> {
            String requeteId = ctx.pathParam("id");
            String candidatureId = ctx.pathParam("candidatureId");

            Document requete = collectionRequetesTravail.find(new Document("id", requeteId)).first();
            if (requete == null) {
                ctx.status(404).result("Requête non trouvée.");
                return;
            }

            List<Document> candidatures = requete.getList("candidatures", Document.class);
            Document candidature = candidatures.stream()
                .filter(c -> c.getString("id").equals(candidatureId))
                .findFirst().orElse(null);

            if (candidature == null) {
                ctx.status(404).result("Candidature non trouvée.");
                return;
            }

            // Mettre à jour les champs modifiables
            Document updateFields = ctx.bodyAsClass(Document.class);
            if (updateFields.containsKey("confirmed")) {
                candidature.put("confirmed", updateFields.getBoolean("confirmed"));
            }

            // Sauvegarder la mise à jour dans MongoDB
            collectionRequetesTravail.updateOne(new Document("id", requeteId),
                new Document("$set", new Document("candidatures", candidatures)));

            ctx.status(200).result("Candidature mise à jour.");
        });
    }

    /**
     * Soumet une candidature pour une requête de travail en envoyant une
     * requête HTTP POST à l'API. Cette méthode envoie les détails de la
     * candidature au serveur pour les enregistrer dans la base de données.
     *
     * @param requeteId L'ID de la requête de travail pour laquelle la
     *                 candidature est soumise.
     * @param demandeurRequete L'ID de la personne qui a soumis la
     *                        requête.
     * @param dateFin La date de fin de la candidature.
     * @param dateDebut La date de début de la candidature.
     * @param status Le statut de la candidature
     * @param isConfirmed Indique si la candidature est confirmée ou non.
     * @param userId L'ID de l'utilisateur qui soumet la candidature.
     * @param intervenantMsg Le message entré par l'intervenant.
     * @param residentMsg Le message entré par le résident.
     * @return Une chaîne de caractères indiquant le résultat de la soumission
     * de la candidature (succès ou erreur).
     */
    public static String soumettreCandidature(String requeteId, String demandeurRequete,
                                            String dateFin, String dateDebut,
                                            String status, boolean isConfirmed,
                                            String userId,
                                            String intervenantMsg,
                                            String residentMsg) {
        try {
            URL url = new URL("http://localhost:8000/requete-travail/" + requeteId + "/candidatures");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"userId\": \"%s\"," +
                "\"dateDebut\": \"%s\", \"dateFin\": \"%s\"," +
                "\"status\": \"%s\", \"confirmed\": \"%b\", \"intervenantMsg\": \"%s\"," +
                "\"residentMsg\": \"%s\"" + "}", userId, dateDebut, dateFin, status, isConfirmed,
            intervenantMsg, residentMsg);

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

    /**
     * Modifie le statut d'une candidature en envoyant une requête HTTP PATCH à
     * l'API.
     * Cette méthode permet de mettre à jour le statut d'une candidature
     * ainsi que le message associé.
     *
     * @param requeteId L'ID de la requête de travail à laquelle appartient la
     *                 candidature.
     * @param candidatureId L'ID de la candidature dont le statut doit être
     *                     modifié.
     * @param status Le nouveau statut de la candidature.
     * @param residentMsg Le message entré par le résident, à mettre à jour dans
     *                   la candidature.
     * @return Une chaîne de caractères indiquant le résultat de la mise à jour
     * du statut de la candidature (succès ou erreur).
     */
    public static String modifierStatutCandidature(String requeteId, String candidatureId,
                                                   String status, String residentMsg) {
        try {
            // Construire l'URL pour la requête PATCH
            String url = "http://localhost:8000/requete-travail/" + requeteId + "/candidatures/" + candidatureId + "/status";

            // Construire les données JSON avec uniquement les champs à modifier
            StringBuilder jsonBuilder = new StringBuilder("{");
            if (status != null) {
                jsonBuilder.append("\"status\": \"").append(status).append("\",");
            }

            if (residentMsg != null) {
                jsonBuilder.append("\"residentMsg\": \"").append(residentMsg).append("\",");
            }

            // Retirer la dernière virgule et fermer l'objet JSON
            if (jsonBuilder.charAt(jsonBuilder.length() - 1) == ',') {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            }
            jsonBuilder.append("}");

            String jsonInputString = jsonBuilder.toString();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).method(
                    "PATCH",
                    HttpRequest.BodyPublishers.ofString(jsonInputString)).header("Content-Type", "application/json; utf-8").build();

            // Envoyer la requête
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            // Vérifier la réponse
            if (response.statusCode() != 200) {
                System.err.println("Erreur lors de la mise à jour : HTTP "
                    + response.statusCode());
                System.err.println("Message d'erreur : " + response.body());
            }


        } catch (Exception e) {
            logger.error("Erreur lors de la modification: ", e);
            return "Erreur";
        }
        return "Candidature mise à jour partiellement avec succès.";
    }

    /**
     * Confirme une candidature en envoyant une requête HTTP PATCH pour mettre à
     * jour le statut de la candidature.
     * La méthode marque la candidature comme confirmée dans la base de données.
     *
     * @param requeteId L'ID de la requête de travail pour laquelle la
     *                 candidature est soumise.
     * @param candidatureId L'ID de la candidature à confirmer.
     * @return Une chaîne de caractères indiquant le résultat de l'opération
     * (succès ou erreur).
     */
    public static String confirmerCandidature(String requeteId, String candidatureId) {
        try {
            // Construire l'URL pour la requête PATCH
            String url = "http://localhost:8000/requete-travail/" + requeteId + "/candidatures/" + candidatureId + "/isconfirmed";

            // Construire les données JSON avec uniquement les champs à modifier
            StringBuilder jsonBuilder = new StringBuilder("{");
            jsonBuilder.append("\"confirmed\":").append(true).append("}");

            String jsonInputString = jsonBuilder.toString();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).method(
                    "PATCH",
                    HttpRequest.BodyPublishers.ofString(jsonInputString)).header("Content-Type", "application/json; utf-8").build();

            // Envoyer la requête
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            // Vérifier la réponse
            if (response.statusCode() != 200) {
                System.err.println("Erreur lors de la mise à jour : HTTP "
                    + response.statusCode());
                System.err.println("Message d'erreur : " + response.body());
            }


        } catch (Exception e) {
            logger.error("Erreur lors de la modification: ", e);
            return "Erreur";
        }
        return "Candidature mise à jour partiellement avec succès.";
    }

    /**
     * Supprime une candidature en envoyant une requête HTTP PATCH pour la
     * marquer comme supprimée.
     * Cette méthode met à jour le statut de la candidature en la supprimant de
     * la base de données.
     *
     * @param requeteId L'ID de la requête de travail pour laquelle la
     *                 candidature est associée.
     * @param candidatureId L'ID de la candidature à supprimer.
     * @return Une chaîne de caractères indiquant le résultat de l'opération
     * (succès ou erreur).
     */
    public static String supprimerCandidature(String requeteId, String candidatureId) {
        try {
            // Construire l'URL pour la requête PATCH
            String url = "http://localhost:8000/requete-travail/" + requeteId + "/candidatures/" + candidatureId + "/delete";


            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).method(
                    "PATCH",
                    HttpRequest.BodyPublishers.ofString("")).header("Content-Type", "application/json; utf-8").build();

            // Envoyer la requête
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            // Vérifier la réponse
            if (response.statusCode() != 200) {
                System.err.println("Erreur lors de la mise à jour : HTTP "
                    + response.statusCode());
                System.err.println("Message d'erreur : " + response.body());
            }


        } catch (Exception e) {
            logger.error("Erreur lors de la modification: ", e);
            return "Erreur";
        }
        return "Candidature supprimée avec succès.";
    }
}
