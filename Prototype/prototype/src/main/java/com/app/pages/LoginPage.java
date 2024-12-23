package com.app.pages;

import com.app.models.Notification;
import com.app.models.PreferenceHoraire;
import com.app.models.User.Resident;
import com.app.models.User.Intervenant;
import com.app.models.User.User;
import org.bson.Document;

import static com.app.controllers.UserController.findUserByEmail;

import java.util.*;

import static com.app.pages.InscriptionPage.passwordEncryption;

/**
 * Cette classe est responsable de la gestion de la connexion des utilisateurs.
 * Permet aux utilisateurs de se connecter en vérifiant leur adresse email
 * et leur mot de passe.
 */
public class LoginPage {

    static Scanner scanner = new Scanner(System.in);
    static boolean isLoggedIn = false;

    /**
     * Méthode principale pour gérer la page de connexion.
     * Permet à l'utilisateur de saisir son adresse courriel et son mot de passe.
     * Si les informations sont correctes, l'utilisateur est connecté.
     *
     * @return L'utilisateur connecté sous forme d'une instance de `Resident`
     * ou `Intervenant`, ou `null` si l'utilisateur choisit de revenir au menu
     * principal.
     */
    public static User loginPage() {
        while (!isLoggedIn) {
            System.out.println("[1] Retour");
            System.out.println("Entrez votre adresse courriel:");
            String email = scanner.nextLine();
            if (Objects.equals(email, "1")) {
                return null;
            } else {
                Document userInfo = findUserByEmail(email);

                if (userInfo != null) {
                    System.out.println("Entrez votre mot de passe:");
                    String password = scanner.nextLine();
                    int encryptedPassword =
                        passwordEncryption.encrypt(password);

                    Integer storedPassword = userInfo.getInteger("password");
                    if (Objects.equals(encryptedPassword, storedPassword)) {
                        String role = userInfo.getString("userRole");
                        System.out.println("Connexion réussie!");
                        List<Document> notificationsDoc = userInfo.getList("notifications", Document.class);
                        List<Notification> notifications = new ArrayList<>();
                        if (notificationsDoc != null) {
                            for (Document notificationDoc :
                                notificationsDoc) {
                                notifications.add(new Notification(
                                    notificationDoc.getString("msg"),
                                    notificationDoc.getString("id"),
                                    notificationDoc.getBoolean("vu")
                                ));
                            }
                        }

                        List<Document> preferencesHorairesDoc =
                            userInfo.getList("notifications", Document.class);
                        List<PreferenceHoraire> preferencesHoraires =
                            new ArrayList<>();

                        if (preferencesHorairesDoc != null) {
                            for (Document preferenceHoraireDoc : preferencesHorairesDoc) {
                                preferencesHoraires.add(new PreferenceHoraire(
                                    preferenceHoraireDoc.getString("jour"),
                                    preferenceHoraireDoc.getString("heureDebut"),
                                    preferenceHoraireDoc.getString("heureFin")
                                ));
                            }
                        }

                        if (Objects.equals(role, "RESIDENT")) {
                            return new Resident(userInfo.getString("firstName"
                            ), userInfo.getString("lastName"),
                                userInfo.getString("email"),
                                userInfo.getString("phoneNumber"),
                                userInfo.getString("dateOfBirth"),
                                userInfo.getString("homeAddress"),
                                userInfo.getInteger("password"),
                                userInfo.getString("userId"),
                                userInfo.getString("boroughId"),
                                notifications,
                                preferencesHoraires);
                        } else {
                            return new Intervenant(userInfo.getString(
                                "firstName"), userInfo.getString("lastName"),
                                userInfo.getString("email"),
                                userInfo.getString("entityType"),
                                userInfo.getString("cityId"),
                                userInfo.getInteger("password"),
                                userInfo.getString("userId"),
                                notifications);
                        }
                    } else {
                        System.out.println("Mauvais mot de passe ou mauvaise "
                            + "adresse" + " courriel");
                    }
                } else {
                    System.out.println("Cette adresse courriel n'est pas " +
                        "associée à un compte.");
                }
            }
        }
        return null;
    }

}
