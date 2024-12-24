package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.Projet;
import com.app.models.User.User;
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
import java.util.stream.Collectors;

import static com.app.utils.GenerateurId.RandomIDGenerator;

/**
 * Contrôleur responsable de la gestion des projets dans l'application.
 * Permet la création, la modification, la consultation et la mise à jour des projets.
 */
public class ProjetController {

    private static final Logger logger =
        LoggerFactory.getLogger(ProjetController.class);
    private static final MongoCollection<Document> collectionProjets =
        MongoDBConnection.getDatabase().getCollection("projets");

    /**
     * Enregistre les routes du contrôleur pour l'application Javalin.
     *
     * @param app Instance de l'application Javalin
     */
    public static void registerRoutes(Javalin app) {

        app.get("/projets", ctx -> {
            try {
                List<Document> projets = collectionProjets.find().into(new ArrayList<>());
                ctx.json(projets);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des projets: ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });


        app.get("/projets/{idSoumissionnaireProjet}", ctx -> {
            try {
                String idSoumissionnaireProjet = ctx.pathParam("idSoumissionnaireProjet");

                ObjectMapper mapper = new ObjectMapper();

                List<Projet> projets = new ArrayList<>();
                collectionProjets.find(new Document("idSoumissionnaireProjet"
                    , idSoumissionnaireProjet)).forEach(document -> {
                    try {
                        Projet projet =
                            mapper.readValue(document.toJson(),
                                Projet.class);
                        projets.add(projet);
                    } catch (Exception e) {
                        logger.error("Erreur lors du mapping du document: " + e);
                    }
                });
                ctx.json(projets);
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération des projets : ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.post("/projets", ctx -> {
            try {
                Projet projet = ctx.bodyAsClass(Projet.class);
                projet.setId(RandomIDGenerator());

                // Validation des champs requis
                if ( projet.getId() == null || projet.getIdSoumissionnaireProjet() == null || projet.getTitre() == null || projet.getDescription() == null || projet.getTypeTravaux() == null || projet.getDateDebut() == null || projet.getDateFin() == null || projet.getQuartiersAffectes() == null || projet.getRuesAffectees() == null) {
                    ctx.status(400).result("Tous les champs sont requis.");
                    return;
                }

                // Insertion dans MongoDB
                Document doc = new Document("id", projet.getId()).append(
                    "idSoumissionnaireProjet", projet.getIdSoumissionnaireProjet()).append(
                    "titre", projet.getTitre()).append("description",
                    projet.getDescription()).append("typeTravaux",
                    projet.getTypeTravaux()).append("dateDebut", projet.getDateDebut()).append("dateFin", projet.getDateFin()).append("quartiersAffectes", projet.getQuartiersAffectes()).append("ruesAffectees", projet.getRuesAffectees()).append("statut", projet.getStatut());
                collectionProjets.insertOne(doc);

                ctx.status(201).result("Projet ajoutée avec " + "succès.");
            } catch (Exception e) {
                logger.error("Erreur lors de la création du projet: ", e);
                ctx.status(500).result("Erreur Serveur");
            }
        });

        app.patch("/projets/{id}", ctx -> {
            try {
                // Récupérer le paramètre de l'URL
                String id = ctx.pathParam("id");

                // Construire le filtre pour identifier le document
                Document filter = new Document("id", id); // Utiliser le
                // champ "id" (String)

                // Récupérer les mises à jour du corps du projet
                Document updates = Document.parse(ctx.body());

                // Valider les champs
                if (!updates.containsKey("statut")) {
                    ctx.status(400).result("Le champ 'statut' est requis.");
                    return;
                }

                // Construire les modifications
                Document update = new Document("$set", updates);

                // Mettre à jour dans MongoDB
                var result = collectionProjets.updateOne(filter, update);

                // Vérifier si un document a été mis à jour
                if (result.getModifiedCount() > 0) {
                    ctx.status(200).result("Statut du projet mise à jour avec succès.");
                } else {
                    System.out.println("Le statut du projet n’a pas été modifié, soit parce que le projet est introuvable, soit parce que le statut est déjà identique.");
                }
            } catch (Exception e) {
                ctx.status(500).result("Erreur serveur : " + e.getMessage());
            }
        });
    }

    /**
     * Soumet un projet via une requête HTTP POST.
     *
     * @param idSoumissionnaireProjet Identifiant du soumissionnaire
     * @param titre Titre du projet
     * @param description Description du projet
     * @param typeTravaux Type des travaux à réaliser
     * @param dateDebut Date de début des travaux
     * @param dateFin Date de fin des travaux
     * @param quartiersAffectes List des quartiers affectés par le projet
     * @param ruesAffectees List des rues affectées par le projet
     * @param statut Statut du projet
     * @return Message indiquant si la soumission a été réussie ou non
     */
    public static String soumettreProjet(String idSoumissionnaireProjet,
                                                 String titre,
                                                 String description,
                                                 String typeTravaux,
                                                 String dateDebut,
                                                 String dateFin,
                                                 List<String> quartiersAffectes,
                                                 List<String> ruesAffectees,
                                         String statut) {
        try {
            URL url = new URL("http://localhost:8000/projets");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String quartiersAffectesJson = quartiersAffectes.stream()
                .map(quartier -> "\"" + quartier + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

            String ruesAffecteesJson = ruesAffectees.stream()
                .map(rue -> "\"" + rue + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

            String jsonInputString = String.format(
                "{ \"id\": \"%s\", " +
                    "\"idSoumissionnaireProjet\": \"%s\", " +
                    "\"titre\": \"%s\", " +
                    "\"description\": \"%s\", " +
                    "\"typeTravaux\": \"%s\", " +
                    "\"dateDebut\": \"%s\", " +
                    "\"dateFin\": \"%s\", " +
                    "\"quartiersAffectes\": %s, " +
                    "\"ruesAffectees\": %s, " +
                    "\"statut\": \"%s\" }",
                RandomIDGenerator(), idSoumissionnaireProjet, titre,
                description, typeTravaux,
                dateDebut, dateFin, quartiersAffectesJson, ruesAffecteesJson,
                statut
            );


            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201) {
                return "Projets ajoutés avec succès.";
            } else {
                return "Erreur : " + conn.getResponseCode();
            }
        } catch (Exception e) {
            return "Erreur lors de la soumission du projet.";
        }
    }

    /**
     * Récupère la liste des projets pour tous les intervenants via l'API.
     *
     * @return Liste des projets récupérés depuis l'API
     */
    public static List<Projet> consulterProjets() {
        try {
            URL url = new URL("http://localhost:8000/projets");
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
                List<Projet> projets =
                    objectMapper.readValue(reponse.toString(),
                        new TypeReference<>() {
                        });
                return projets;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère la liste des projets pour un intervenant spécifique via l'API.
     * @param user Intervenant qui a créé le projet
     * @return Liste des projets récupérés depuis l'API
     */
    public static List<Projet> consulterProjet(User user) {
        try {
            URL url =
                new URL("http://localhost:8000/projets/" + user.getUserId());
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
                List<Projet> projets =
                    objectMapper.readValue(reponse.toString(),
                        new TypeReference<List<Projet>>() {
                        });
                return projets;

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * Modifie un projet existant via l'API.
     *
     * @param statut Statut du projet à mettre à jour
     * @param projet Projet existant à mettre à jour
     * @return True si le projet a été mis à jour avec succès, false sinon.
     */
    public static boolean mettreAJourStatutProjet(String statut,
                                                          Projet projet) {
        try {
            // Vérification de l'identifiant de la requête
            String idValue = projet.getId();
            if (idValue == null) {
                throw new IllegalArgumentException("L'identifiant 'id' est " +
                    "manquant ou invalide.");
            }

            // Construire l'URL de la requête
            String url = "http://localhost:8000/projets/" + idValue;

            // Construire le corps de la requête JSON
            String chargeJson = String.format("{\"statut\": \"%s\"}", statut);

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
                    System.out.println("Statut mise à jour");
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
}
