package com.app.pages;


import java.util.Objects;
import java.util.Scanner;


import com.app.utils.GlobalUserInfo;
import com.app.utils.PasswordEncryption;
import com.app.models.User.Intervenant;
import com.app.models.User.Resident;
import com.app.models.User.User;

import static com.app.controllers.InscriptionController.addNewUser;
import static com.app.utils.InscriptionUtils.*;
import static com.app.utils.RegexChecker.*;

public class InscriptionPage {

    static Scanner scanner = new Scanner(System.in);
    static PasswordEncryption passwordEncryption = new PasswordEncryption();
    static User user = new User();
    static String role;

    // User Attributes
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static int encryptedPassword;

    // Resident Specific Attributes
    static String phoneNumber;
    static String dateOfBirth;
    static String homeAddress;

    // Intervenant Specific Attributes
    static String entityType;
    static String cityId;

    public static User inscriptionPage() {
        System.out.println("[1] Retour");
        System.out.println("[2] S'inscrire comme résident");
        System.out.println("[3] S'inscrire comme intervenant");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                return null;
            }
            case "2" -> {
                role = "Résident";
            }
            case "3" -> {
                role = "Intervenant";
            }
            case null, default -> {
                return inscriptionPage();
            }
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
            // Verify that the email is valid
            if (email.contains("@")) {
                // TODO: Ajouter la logique qui compare l'adresse courriel
                //  rentrées par
                //  le user et les données présentes dans une base de
                //  donnée.
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
                if (isValidDateFormat(dateOfBirth)) {
                    validDateOfBirth = true;
                } else {
                    System.out.println("La date de naissance entrée n'est " + "pas du format YYYY-MM-DD");
                }
            }

            boolean validPhoneNumber = false;
            while (!validPhoneNumber) {
                System.out.println("Entrez votre numéro de téléphone:");
                phoneNumber = scanner.nextLine();
                // Verify that the phoneNumber is valid format
                if (isValidPhoneNumberFormat(phoneNumber)) {
                    validPhoneNumber = true;
                } else {
                    System.out.println("Le numéro de téléphone entrée n'est " + "pas du format " + "0000-000-000");
                }
            }

            boolean validAddress = false;
            while (!validAddress) {
                System.out.println("Entrez votre adresse de résidence:");
                homeAddress = scanner.nextLine();
                // Verify that the homeAddress is valid format
                if (!Objects.equals(homeAddress, "")) {
                    validAddress = true;
                } else {
                    System.out.println("L'adresse de résidence n'est pas du " + "format accepté.");
                }
            }
        }

        // Specific required fields for Intervenants
        else {
            boolean validEntityType = false;
            while (!validEntityType) {
                System.out.println("Entrez votre type d'entité (" +
                    "Entreprise" + " " + "public, " + "Entrepreneur " + "priv"
                    + "é, Particulier):");
                entityType = scanner.nextLine();
                // Verify that the entityType is not null
                if (!Objects.equals(entityType, "")) {
                    validEntityType = true;
                }
            }

            boolean validCityId = false;
            while (!validCityId) {
                System.out.println("Entrez votre Identifiant de " + "ville:");
                cityId = scanner.nextLine();
                // Verify that the cityId is not null
                if (!Objects.equals(cityId, "")) {
                    validCityId = true;
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
                // TODO: Store the users info into a Hash table where
                //  the hash buckets are user classes
                Resident resident = new Resident(firstName, lastName, email,
                    phoneNumber, dateOfBirth, homeAddress, encryptedPassword);
                addNewUser(resident.getUserId(), firstName, lastName,
                    email, phoneNumber, dateOfBirth, homeAddress,
                    "", "", String.valueOf(encryptedPassword), resident.getUserRole());
                System.out.println("Inscription réussite !");
                GlobalUserInfo.setCurrentRole("RESIDENT");
                GlobalUserInfo.setCurrentUserId(resident.getUserId());
                user = resident;
                return resident;
            } else {
                inscriptionPage();
            }
        } else {
            // TODO: Store the users info into a Hash table where
            //  the hash buckets are user classes
            Intervenant intervenant = new Intervenant(firstName, lastName,
                email, entityType, cityId, encryptedPassword);
            addNewUser(intervenant.getUserId(), firstName, lastName, email, phoneNumber,
                dateOfBirth, homeAddress, entityType, cityId, String.valueOf(encryptedPassword),
                intervenant.getUserRole());
            System.out.println("Inscription réussite !");
            GlobalUserInfo.setCurrentRole("INTERVENANT");
            GlobalUserInfo.setCurrentUserId(intervenant.getUserId());
            user = intervenant;
            return intervenant;
        }
        return user;
    }
}
