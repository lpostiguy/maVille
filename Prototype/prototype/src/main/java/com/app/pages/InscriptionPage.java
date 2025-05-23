package com.app.pages;


import java.util.Objects;
import java.util.Scanner;


import com.app.utils.PasswordEncryption;
import com.app.models.User.Intervenant;
import com.app.models.User.Resident;
import com.app.models.User.User;
import com.app.utils.PostalCodeMapping;

import static com.app.controllers.UserController.addNewUser;
import static com.app.controllers.UserController.findUserByEmail;
import static com.app.controllers.UserController.findUserByCityId;
import static com.app.utils.InscriptionUtils.*;
import static com.app.utils.RegexChecker.*;

/**
 * Cette classe est responsable de la gestion de l'inscription des utilisateurs.
 * Elle permet l'inscription en tant que Résident ou Intervenant,
 * avec validation des données utilisateur et enregistrement dans le système.
 */

public class InscriptionPage {

    static Scanner scanner = new Scanner(System.in);
    static PasswordEncryption passwordEncryption = new PasswordEncryption();
    static User user = new User();
    static String role;

    // Attributs communs à tous les utilisateurs
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static int encryptedPassword;

    // Attributs uniques au résident
    static String phoneNumber;
    static String dateOfBirth;
    static String homeAddress;
    static String boroughId;

    // Attributs uniques à l'intervenant
    static String entityType;
    static String cityId;

    /**
     * Méthode principale pour afficher la page d'inscription.
     * Propose le choix de rôle (Résident ou Intervenant)
     * et gère le processus d'inscription complet.
     *
     * @return L'utilisateur inscrit (instance de "Resident" ou "Intervenant"),
     * ou `null` si l'utilisateur revient au menu.
     */
    public static User inscriptionPage() {
        System.out.println("\n----------- Inscription -----------");
        System.out.println("[1] Retour");
        System.out.println("[2] S'inscrire comme résident");
        System.out.println("[3] S'inscrire comme intervenant");
        System.out.println("-----------------------------------");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return null;
            case "2":
                role = "Résident";
                break;
            case "3":
                role = "Intervenant";
                break;
            case null, default:
                return inscriptionPage();
            }
        System.out.println("Suivre les instructions suivantes pour " + "s" +
            "'inscrire.");
        boolean validFirstName = false;
        while (!validFirstName) {
            System.out.println("Entrez votre prénom:");
            firstName = scanner.nextLine();
            // Verify that the firstName is not null
            if (!Objects.equals(firstName, "")) {
                validFirstName = true;
            }
        }
        boolean validLastName = false;
        while (!validLastName) {
            System.out.println("Entrez votre nom de famille:");
            lastName = scanner.nextLine();
            // Verify that the lastName is not null
            if (!Objects.equals(lastName, "")) {
                validLastName = true;
            }
        }

        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("Entrez votre adresse courriel:");
            email = scanner.nextLine();
            // Verify that the email is valid and not used
            if (findUserByEmail(email) != null) {
                System.out.println("Cette adresse courriel est déjà associée à un compte." +
                    " Veuillez entrer une autre adresse courriel.");
            }
            else if (email.contains("@")) {
                validEmail = true;
            }
        }

        // Specific required fields for Résidents
        if (Objects.equals(role, "Résident")) {

            boolean validDateOfBirth = false;
            while (!validDateOfBirth) {
                System.out.println("Entrez votre date de naissance afin de " + "valider votre age. (format YYYY-MM-DD):");
                dateOfBirth = scanner.nextLine();
                // Verify that the dateOfBirth is valid format
                if (estFormatDateValide(dateOfBirth)) {
                    validDateOfBirth = true;
                } else {
                    System.out.println("La date de naissance entrée n'est " + "pas du format YYYY-MM-DD");
                }
            }

            boolean validPhoneNumber = false;
            while (!validPhoneNumber) {
                System.out.println("Entrez votre numéro de téléphone:");
                System.out.println("(N'entrez rien pour sauter cette étape.)");
                phoneNumber = scanner.nextLine();
                // Verify that the phoneNumber is valid format
                if (estFormatNumeroTelephoneValide(phoneNumber) || phoneNumber.isEmpty()) {
                    validPhoneNumber = true;
                } else {
                    System.out.println("Le numéro de téléphone entrée n'est " + "pas du format " + "0000-000-000");
                }
            }

            boolean validAddress = false;
            while (!validAddress) {
                System.out.println("Entrez votre adresse de résidence: " +
                    "Numéro, rue, code postal");
                homeAddress = scanner.nextLine();
                PostalCodeMapping postalCodeMapping = new PostalCodeMapping(
                    "codesPostaux.csv");
                String borough =
                    postalCodeMapping.getDistrictByPostalCode(homeAddress);
                if (borough == null)
                    System.out.println("Veuillez respecter le format.");
                else if (borough.equals("Quartier inconnu")) {
                    System.out.println("Quartier inconnu. Veuillez réessayer.");
                } else {
                    validAddress = true;
                    boroughId = borough;
                }

            }
        }

        // Specific required fields for Intervenants
        else {
            boolean validEntityType = false;
            while (!validEntityType) {
                System.out.println("Entrez votre type d'entité (" +
                    "Entreprise" + " " + "publique, " + "Entrepreneur " + "priv"
                    + "é, Particulier):");
                entityType = scanner.nextLine();
                // Verify that the entityType is not null
                if (!Objects.equals(entityType, "")) {
                    validEntityType = true;
                }
            }

            boolean validCityId = false;
            while (!validCityId) {
                System.out.println("Entrez votre identifiant de la ville (8 " +
                    "chiffres):");
                cityId = scanner.nextLine();
                // Verify that the cityId is valid
                if (cityId.length() != 8) {
                    System.out.println("Cet identifiant de la ville n'est pas valide. Veuillez réessayer.");
                }
                else if (findUserByCityId(cityId) == null) {
                    validCityId = true;
                }
                else {
                    System.out.println("Cet identifiant est déjà associé à un compte. Veuillez réessayer.");
                }
            }
        }

        boolean validPassword = false;
        while (!validPassword) {
            System.out.println("Créer votre mot de passe:");
            password = scanner.nextLine();
            if (Objects.equals(isValidPassword(password), "")) {
                validPassword = true;
            } else if (Objects.equals(isValidPassword(password),
                "Minimum " + "of" + " 8 character required")) {
                System.out.println("Minimum de 8 caractères requis");
            } else if (Objects.equals(isValidPassword(password),
                "Minimum " + "of" + " 1 capital " + "letter and 1 " + "number"
                    + " " + "required")) {
                System.out.println("Minimum de 1 lettre majuscule et 1 " +
                    "chiffre requis");
            }
        }
        boolean validPasswordConfirmation = false;
        while (!validPasswordConfirmation) {
            System.out.println("Confirmez votre mot de passe:");
            if (isSamePassword(password, scanner.nextLine())) {
                validPasswordConfirmation = true;
                encryptedPassword = passwordEncryption.encrypt(password);
            } else {
                System.out.println("Le mot de passe ne correspond " + "pas " + "à" + " " + "celui rentré au par avant.");
            }
        }

        if (Objects.equals(role, "Résident")) {
            if (isAgeAbove16(dateOfBirth)) {
                Resident resident = new Resident(firstName, lastName, email,
                    phoneNumber, dateOfBirth, homeAddress, encryptedPassword,
                    boroughId);
                addNewUser(resident.getUserId(), firstName, lastName, email,
                    phoneNumber, dateOfBirth, homeAddress, "", "",
                    String.valueOf(encryptedPassword), resident.getUserRole()
                    , boroughId);
                System.out.println("Inscription réussite !");
                user = resident;
                return resident;
            } else {
                inscriptionPage();
            }
        } else {
            Intervenant intervenant = new Intervenant(firstName, lastName,
                email, entityType, cityId, encryptedPassword);
            addNewUser(intervenant.getUserId(), firstName, lastName, email,
                phoneNumber, dateOfBirth, homeAddress, entityType, cityId,
                String.valueOf(encryptedPassword), intervenant.getUserRole(),
                boroughId);
            System.out.println("Inscription réussite !");
            user = intervenant;
            return intervenant;
        }
        return user;
    }
}
