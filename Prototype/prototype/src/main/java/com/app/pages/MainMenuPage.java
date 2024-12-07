package com.app.pages;

import com.app.models.User.Resident;

import java.util.Objects;
import java.util.Scanner;

import static com.app.controllers.NotificationsController.consulterNotificationsNonLues;

public class MainMenuPage {

    static Scanner scanner = new Scanner(System.in);

    public static String mainMenu() {
        while(true) {
            System.out.println("Entrez un numéro pour être redirigé vers ces " + "pages:");
            System.out.println("[1] Connection");
            System.out.println("[2] Inscription");
            String response = scanner.nextLine();
            if (Objects.equals(response, "1")) {
                return "Connection";
            } else if (Objects.equals(response, "2")) {
                return "Inscription";
            }
        }
    }

    public static String mainMenuLoggedResident(Resident resident) {
        while (true) {
            System.out.println("\nEntrez un numéro pour être redirigé vers ces " + "pages:");
            System.out.println("---------- Menu Principal ----------");
            System.out.println("[1] Consulter ou rechercher des travaux");
            System.out.println("[2] Consulter les entraves routières");
            System.out.println("[3] Soumettre une requête de travail");
            System.out.println("[4] Faire le suivi d'une requête de travail");
            System.out.println("[5] Notifications " + "(" + consulterNotificationsNonLues(resident).size() + ") non vue");
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

    public static String mainMenuLoggedIntervenant() {
        while(true) {
            System.out.println("\nEntrez un numéro pour être redirigé vers ces " + "pages:");
            System.out.println("---------- Menu Principal ----------");
            System.out.println("[1] Consulter la liste des requêtes de travail");
            System.out.println("[2] Soumettre un nouveau projet de travaux");
            System.out.println("[3] Mettre à jour les informations d'un chantier");
            System.out.println("[4] Consulter son profil");
            System.out.println("[5] Se Déconnecter");
            System.out.println("------------------------------------");
            String response = scanner.nextLine();
            switch (response) {
                case "1" -> {
                    return "Consulter la liste des requêtes de travail";
                }
                case "2" -> {
                    return "Soumettre un nouveau projet de travaux";
                }
                case "3" -> {
                    return "Mettre à jour les informations d'un chantier";
                }
                case "4" -> {
                    return "Consulter son profil";
                }
                case "5" -> {
                    return "Se Déconnecter";
                }
                case null, default -> {
                    System.out.println("Vous n'avez pas choisi de choix valide.");
                }
            }
        }
    }
}
