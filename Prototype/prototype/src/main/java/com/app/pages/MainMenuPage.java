package com.app.pages;

import java.util.Objects;
import java.util.Scanner;

public class MainMenuPage {

    static Scanner scanner = new Scanner(System.in);

    public static String wrongPageChoice(String menuName) {
        System.out.println("Vous n'avez pas choisi de choix valide.");
        System.out.println("[1] Revoir les choix de pages");
        String response2 = scanner.nextLine();
        if (Objects.equals(response2, "1")) {
            if (Objects.equals(menuName, "mainMenu")) {
                return mainMenu();
            } else if (Objects.equals(menuName, "menuLoggedResident")) {
                return mainMenuLoggedResident();
            } else if (Objects.equals(menuName, "menuLoggedIntervenant")) {
                return mainMenuLoggedIntervenant();
            }
        }
        return wrongPageChoice(menuName);
    }

    public static String mainMenu() {
        System.out.println("Entrez un numéro pour être redirigé vers ces " +
            "pages:");
        System.out.println("[1] Connection");
        System.out.println("[2] Inscription");
        String response = scanner.nextLine();
        if (Objects.equals(response, "1")) {
            return "Connection";
        } else if (Objects.equals(response, "2")) {
            return "Inscription";
        } else {
            return wrongPageChoice("mainMenu");
        }
    }

    public static String mainMenuLoggedResident() {
        System.out.println("Entrez un numéro pour être redirigé vers ces " +
            "pages:");
        System.out.println("[1] Consulter ou rechercher des travaux");
        System.out.println("[2] Consulter les entraves routières");
        System.out.println("[3] Soumettre une requête de travail");
        System.out.println("[4] Signaler un problème à la ville");
        System.out.println("[5] Notifications");
        System.out.println("[6] Consulter son profil");
        System.out.println("[7] Se Déconnecter");
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
                return "Signaler un problème à la ville";
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
                return wrongPageChoice("menuLoggedResident");
            }
        }
    }

    public static String mainMenuLoggedIntervenant() {
        System.out.println("Entrez un numéro pour être redirigé vers ces " +
            "pages:");
        System.out.println("[1] Consulter la liste des requêtes de travail");
        System.out.println("[2] Soumettre un nouveau projet de travaux");
        System.out.println("[3] Mettre à jour les informations d'un chantier");
        System.out.println("[4] Consulter son profil");
        System.out.println("[5] Se Déconnecter");
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
                return wrongPageChoice("menuLoggedIntervenant");
            }
        }
    }
}
