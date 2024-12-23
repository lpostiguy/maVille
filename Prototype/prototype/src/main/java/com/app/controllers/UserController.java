package com.app.controllers;

import com.app.MongoDBConnection;
import com.app.models.User.User;
import com.mongodb.client.FindIterable;
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

/**
 * Contrôleur gérant les opérations liées aux utilisateurs dans l'application.
 * Ce contrôleur permet de créer ou de récupérer des utilisateurs.
 */
public class UserController {

    private static final Logger logger =
        LoggerFactory.getLogger(UserController.class);
    private static MongoCollection<Document> collectionUsers = MongoDBConnection.getDatabase().getCollection("users");

    /**
     * Enregistre les routes du contrôleur pour l'application Javalin.
     *
     * @param app Instance de l'application Javalin
     */
    public static void registerRoutes(Javalin app) {

        app.post("/users", ctx -> {
            try {
                User user = ctx.bodyAsClass(User.class);

                List<Document> notificationsInitiales = new ArrayList<>();
                List<String> preferencesHorairesInitiales = new ArrayList<>();

                Document doc = new Document().append("userId",
                    user.getUserId()).append("firstName",
                    user.getFirstName()).append("lastName",
                    user.getLastName()).append("email", user.getEmail()).append("password", user.getPassword()).append("userRole", user.getUserRole());

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

    /**
     * Recherche un utilisateur dans la base de données par son courriel.
     *
     * @param email L'email de l'utilisateur à rechercher.
     * @return Un document représentant l'utilisateur trouvé, ou null si aucun
     * utilisateur n'a été trouvé.
     */
    public static Document findUserByEmail(String email) {
        return collectionUsers.find(new Document("email", email)).first();
    }

    /**
     * Recherche un utilisateur dans la base de données par son identifiant de
     * ville.
     *
     * @param cityId L'identifiant de la ville.
     * @return Un document représentant l'utilisateur trouvé, ou null si
     * aucun utilisateur n'a été trouvé.
     */
    public static Document findUserByCityId(String cityId) {
        return collectionUsers.find(new Document("cityId", cityId)).first();
    }

    /**
     * Recherche un utilisateur dans la base de données par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à rechercher.
     * @return Un document représentant l'utilisateur trouvé, ou null si aucun
     * utilisateur n'a été trouvé.
     */
    public static Document findUserById(String id) {
        return collectionUsers.find(new Document("userId", id)).first();
    }

    /**
     * Recherche tous les utilisateurs associés à un identifiant de quartier.
     *
     * @param boroughId L'identifiant du quartier.
     * @return Une liste d'identifiants d'utilisateurs associés à ce quartier.
     */
    public static List<String> findUsersByBoroughId(String boroughId) {
        // Rechercher les utilisateurs correspondant à boroughId
        FindIterable<Document> result = collectionUsers.find(new Document("boroughId", boroughId));

        // Convertir les résultats en une liste
        List<String> users = new ArrayList<>();
        for (Document doc : result) {
            String userId = doc.getString("userId");
            users.add(userId);
        }

        return users;
    }

    public static List<String> findUsersNameByUserId(String userId) {
        // Rechercher les informations utilisateurs correspondant à boroughId
        FindIterable<Document> result = collectionUsers.find(new Document("userId", userId));

        // Convertir les résultats en une liste
        List<String> users = new ArrayList<>();
        for (Document doc : result) {
            String prenom = doc.getString("firstName");
            String nom = doc.getString("lastName");
            users.add(prenom + " " + nom);
        }

        return users;
    }

    /**
     * Ajoute un nouvel utilisateur à l'application via une requête HTTP.
     *
     * @param userId L'identifiant de l'utilisateur.
     * @param firstName Le prénom de l'utilisateur.
     * @param lastName Le nom de famille de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param phoneNumber Le numéro de téléphone de l'utilisateur.
     * @param dateOfBirth La date de naissance de l'utilisateur.
     * @param homeAddress L'adresse du domicile de l'utilisateur.
     * @param entityType Le type d'entité pour un intervenant.
     * @param cityId L'identifiant de la ville pour un intervenant.
     * @param password Le mot de passe de l'utilisateur.
     * @param userRole Le rôle de l'utilisateur dans l'application.
     * @param boroughId L'identifiant du quartier de l'utilisateur.
     * @return Un message de statut indiquant si l'ajout de l'utilisateur
     * a réussi.
     */
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
