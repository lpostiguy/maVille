package com.app.pages;

import com.app.models.User.Resident;

import java.util.Objects;
import java.util.Scanner;

import static com.app.controllers.NotificationsController.consulterNotificationsNonLues;

/**
 * Classe responsable de la gestion des menus principaux.
 * Elle permet d'afficher différentes options de navigation en fonction de
 * l'état de connexion de l'utilisateur.
 * Il existe trois types de menus :
 * 1. Menu principal (avant connexion)
 * 2. Menu principal pour un résident connecté
 * 3. Menu principal pour un intervenant connecté
 */
public class MainMenuPage {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Affiche le menu principal pour un utilisateur non connecté et permet de
     * choisir entre la connexion ou l'inscription.
     *
     * @return Une chaîne de caractères représentant l'option choisie
     * ("Connexion" ou "Inscription").
     */
    public static String mainMenu() {
        while(true) {
            System.out.println("Entrez un numéro pour être redirigé vers ces "
                + "pages:");
            System.out.println("[1] Connexion");
            System.out.println("[2] Inscription");
            String response = scanner.nextLine();
            if (Objects.equals(response, "1")) {
                return "Connexion";
            } else if (Objects.equals(response, "2")) {
                return "Inscription";
            }
        }
    }
    /**
     * Affiche le menu principal pour un résident connecté et permet de choisir
     * parmi les différentes options disponibles. Ce menu propose des actions
     * spécifiques aux résidents comme la consultation de projets, la soumission
     * de requêtes, et la gestion des notifications.
     *
     * @param resident L'utilisateur résident connecté.
     * @return Une chaîne de caractères représentant l'option choisie
     * (par exemple "Consulter ou rechercher des travaux").
     */
    public static String mainMenuLoggedResident(Resident resident) {
        while (true) {
            System.out.println("\nEntrez un numéro pour être redirigé vers ces "
                + "pages:");
            System.out.println("---------- Menu Principal ----------");
            System.out.println("[1] Consulter ou rechercher des travaux et " +
                "projets");
            System.out.println("[2] Consulter les entraves routières");
            System.out.println("[3] Soumettre une requête de travail");
            System.out.println("[4] Faire le suivi d'une requête de travail");
            System.out.println("[5] Notifications " + "(" +
                consulterNotificationsNonLues(resident).size() + ") non vue");
            System.out.println("[6] Consulter son profil");
            System.out.println("[7] Se Déconnecter");
            System.out.println("------------------------------------");
            String response = scanner.nextLine();
            switch (response) {
                case "1" -> {
                    return "Consulter ou rechercher des travaux";
                }
                case "2" -> {
                    return "Consulter les entraves routières";
                }
                case "3" -> {
                    return "Soumettre une requête de travail";
                }
                case "4" -> {
                    return "Faire le suivi d'une requête de travail";
                }
                case "5" -> {
                    return "Notifications";
                }
                case "6" -> {
                    return "Consulter son profil";
                }
                case "7" -> {
                    return "Se Déconnecter";
                }
                case null, default -> {
                    System.out.println("Vous n'avez pas choisi de choix valide.");
                }
            }
        }
    }

    /**
     * Affiche le menu principal pour un intervenant connecté et permet de
     * choisir parmi les différentes options disponibles. Ce menu propose des
     * actions spécifiques aux intervenants comme la consultation de requêtes
     * et la gestion de projets.
     *
     * @return Une chaîne de caractères représentant l'option choisie (par
     * exemple "Consulter la liste des requêtes de travail").
     */
    public static String mainMenuLoggedIntervenant() {
        while(true) {
            System.out.println("\nEntrez un numéro pour être redirigé vers ces "
                + "pages:");
            System.out.println("---------- Menu Principal ----------");
            System.out.println("[1] Consulter la liste des requêtes de travail");
            System.out.println("[2] Soumettre ou consulter des projets");
            System.out.println("[3] Consulter son profil");
            System.out.println("[4] Se Déconnecter");
            System.out.println("------------------------------------");
            String response = scanner.nextLine();
            switch (response) {
                case "1" -> {
                    return "Consulter la liste des requêtes de travail";
                }
                case "2" -> {
                    return "Soumettre ou consulter des projets";
                }
                case "3" -> {
                    return "Consulter son profil";
                }
                case "4" -> {
                    return "Se Déconnecter";
                }
                case null, default -> {
                    System.out.println("Vous n'avez pas choisi de choix valide.");
                }
            }
        }
    }
}
