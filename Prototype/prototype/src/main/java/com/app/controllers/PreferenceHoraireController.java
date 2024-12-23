package com.app.controllers;

import com.app.MongoDBConnection;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur responsable de la gestion des préférences horaires pour les
 * résidents.
 * Publie, modifie, récupère et supprime les préférences horaires des résidents.
 */
public class PreferenceHoraireController {

    private static final Logger logger = LoggerFactory.getLogger(PreferenceHoraireController.class);
    private static final MongoCollection<Document> collectionUtilisateurs =
        MongoDBConnection.getDatabase().getCollection("users");

    private static final String BASE_URL = "http://localhost:8000/users";

    /**
     * Enregistre les routes du contrôleur pour l'application Javalin.
     *
     * @param app Instance de l'application Javalin
     */
    public static void registerRoutes(Javalin app) {

        // Route pour récupérer les préférences horaires d'un utilisateur
        app.get("/users/{userId}/preferencesHoraires", ctx -> {
            try {
                String userId = ctx.pathParam("userId");

                // Rechercher l'utilisateur dans MongoDB
                Document utilisateur = collectionUtilisateurs.find(new Document("userId", userId)).first();

                if (utilisateur == null) {
                    ctx.status(404).result("Utilisateur non trouvé.");
                    return;
                }

                // Extraire les préférences horaires
                List<Document> preferencesHoraires = utilisateur.getList("preferencesHoraires", Document.class);
                ctx.json(preferencesHoraires != null ? preferencesHoraires : new ArrayList<>());
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des préférences horaires : ", e);
                ctx.status(500).result("Erreur Serveur.");
            }
        });

        // Route pour créer des préférences horaires si aucune n'existe
        app.post("/users/{userId}/preferencesHoraires", ctx -> {
            try {
                String userId = ctx.pathParam("userId");
                Document preferenceRecu = Document.parse(ctx.body());

                // Validation des champs obligatoires
                String jour = preferenceRecu.getString("jour");
                String heureDebut = preferenceRecu.getString("heureDebut");
                String heureFin = preferenceRecu.getString("heureFin");

                if (jour == null || heureDebut == null || heureFin == null) {
                    ctx.status(400).result("Les champs 'jour', 'heureDebut', et 'heureFin' sont obligatoires.");
                    return;
                }

                // Rechercher l'utilisateur dans MongoDB
                Document utilisateur = collectionUtilisateurs.find(new Document("userId", userId)).first();

                if (utilisateur == null) {
                    ctx.status(404).result("Utilisateur non trouvé.");
                    return;
                }

                // Vérifier si l'utilisateur a déjà des préférences horaires
                List<Document> preferencesHoraires = utilisateur.getList("preferencesHoraires", Document.class);

                if (preferencesHoraires != null && !preferencesHoraires.isEmpty()) {
                    ctx.status(400).result("L'utilisateur a déjà des préférences horaires. Utilisez PATCH pour les modifier.");
                    return;
                }

                // Ajouter une nouvelle liste de préférences horaires
                List<Document> nouvellesPreferences = new ArrayList<>();
                nouvellesPreferences.add(new Document()
                    .append("jour", jour)
                    .append("heureDebut", heureDebut)
                    .append("heureFin", heureFin));

                // Mettre à jour dans MongoDB
                collectionUtilisateurs.updateOne(
                    new Document("userId", userId),
                    new Document("$set", new Document("preferencesHoraires", nouvellesPreferences)),
                    new UpdateOptions().upsert(true)
                );

                ctx.status(201).result("Préférences horaires créées avec succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création des préférences horaires : ", e);
                ctx.status(500).result("Erreur Serveur.");
            }
        });

        // Route pour ajouter ou modifier une préférence horaire
        app.patch("/users/{userId}/preferencesHoraires", ctx -> {
            try {
                String userId = ctx.pathParam("userId");
                Document preferenceRecu = Document.parse(ctx.body());

                // Validation des champs obligatoires
                String jour = preferenceRecu.getString("jour");
                String heureDebut = preferenceRecu.getString("heureDebut");
                String heureFin = preferenceRecu.getString("heureFin");

                if (jour == null || heureDebut == null || heureFin == null) {
                    ctx.status(400).result("Les champs 'jour', 'heureDebut', et 'heureFin' sont obligatoires.");
                    return;
                }

                // Rechercher l'utilisateur dans MongoDB
                Document utilisateur = collectionUtilisateurs.find(new Document("userId", userId)).first();

                if (utilisateur == null) {
                    ctx.status(404).result("Utilisateur non trouvé.");
                    return;
                }

                // Récupérer ou initialiser les préférences horaires existantes
                List<Document> preferencesHoraires = utilisateur.getList("preferencesHoraires", Document.class);
                if (preferencesHoraires == null) preferencesHoraires = new ArrayList<>();

                // Vérifier si le jour existe déjà dans les préférences
                boolean jourExistant = preferencesHoraires.stream()
                    .anyMatch(preference -> preference.getString("jour").equalsIgnoreCase(jour));

                if (jourExistant) {
                    // Modifier la préférence existante
                    preferencesHoraires.forEach(preference -> {
                        if (preference.getString("jour").equalsIgnoreCase(jour)) {
                            preference.put("heureDebut", heureDebut);
                            preference.put("heureFin", heureFin);
                        }
                    });
                } else {
                    // Ajouter une nouvelle préférence
                    preferencesHoraires.add(new Document()
                        .append("jour", jour)
                        .append("heureDebut", heureDebut)
                        .append("heureFin", heureFin));
                }

                // Mettre à jour les préférences horaires dans MongoDB
                collectionUtilisateurs.updateOne(
                    new Document("userId", userId),
                    new Document("$set", new Document("preferencesHoraires", preferencesHoraires))
                );

                ctx.status(200).result("Préférence horaire mise à jour avec succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la mise à jour des préférences horaires : ", e);
                ctx.status(500).result("Erreur Serveur.");
            }
        });

        // Route pour supprimer une préférence horaire
        app.delete("/users/{userId}/preferencesHoraires/{jour}", ctx -> {
            try {
                String userId = ctx.pathParam("userId");
                String jour = ctx.pathParam("jour");

                // Rechercher l'utilisateur dans MongoDB
                Document utilisateur = collectionUtilisateurs.find(new Document("userId", userId)).first();

                if (utilisateur == null) {
                    ctx.status(404).result("Utilisateur non trouvé.");
                    return;
                }

                // Récupérer les préférences horaires existantes
                List<Document> preferencesHoraires = utilisateur.getList("preferencesHoraires", Document.class);
                if (preferencesHoraires == null || preferencesHoraires.isEmpty()) {
                    ctx.status(404).result("Aucune préférence horaire trouvée.");
                    return;
                }

                // Supprimer la préférence correspondant au jour
                boolean supprimé = preferencesHoraires.removeIf(preference ->
                    preference.getString("jour").equalsIgnoreCase(jour));

                if (!supprimé) {
                    ctx.status(404).result("Préférence horaire pour le jour spécifié introuvable.");
                    return;
                }

                // Mettre à jour les préférences horaires dans MongoDB
                collectionUtilisateurs.updateOne(
                    new Document("userId", userId),
                    new Document("$set", new Document("preferencesHoraires", preferencesHoraires))
                );

                ctx.status(200).result("Préférence horaire supprimée avec succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la suppression de la préférence horaire : ", e);
                ctx.status(500).result("Erreur Serveur.");
            }
        });
    }

    /**
     * Récupère les préférences horaires d'un utilisateur via l'API.
     *
     * @param userId L'ID de l'utilisateur pour lequel les préférences
     *              horaires doivent être récupérées.
     * @return Une liste de documents représentant les préférences horaires
     * de l'utilisateur.
     */
    // Récupérer les préférences horaires d'un utilisateur via API
    public static List<Document> recupererPreferencesHoraires(String userId) {
        try {
            URL url = new URL(BASE_URL + "/" + userId + "/preferencesHoraires");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(conn.getInputStream(), new TypeReference<List<Document>>() {});
            } else {
                System.out.println("Erreur lors de la récupération des préférences horaires : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Ajoute ou modifie une préférence horaire d'un utilisateur via l'API.
     *
     * @param userId    L'ID de l'utilisateur pour lequel la préférence horaire
     *                  doit être ajoutée ou modifiée.
     * @param jour      Le jour de la semaine pour lequel la préférence horaire
     *                 est définie.
     * @param heureDebut L'heure de début de la préférence horaire.
     * @param heureFin  L'heure de fin de la préférence horaire.
     * @return true si la préférence horaire a été ajoutée ou modifiée avec
     * succès, false sinon.
     */
    public static boolean ajouterOuModifierPreferenceHoraire(String userId, String jour, String heureDebut, String heureFin) {
        try {
            URL url = new URL(BASE_URL + "/" + userId + "/preferencesHoraires");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Utiliser POST comme méthode de fallback pour simuler PATCH
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Indique que la requête est un PATCH
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire le JSON pour la préférence horaire
            String jsonInputString = String.format(
                "{\"jour\": \"%s\", \"heureDebut\": \"%s\", \"heureFin\": \"%s\"}",
                jour, heureDebut, heureFin
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Supprime une préférence horaire d'un utilisateur via l'API.
     *
     * @param userId L'ID de l'utilisateur pour lequel la préférence horaire
     *              doit être supprimée.
     * @param jour   Le jour de la semaine pour lequel la préférence horaire
     *               doit être supprimée.
     * @return true si la préférence horaire a été supprimée avec succès,
     * false sinon.
     */
    public static boolean supprimerPreferenceHoraire(String userId, String jour) {
        try {
            URL url = new URL(BASE_URL + "/" + userId + "/preferencesHoraires/" + jour);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
