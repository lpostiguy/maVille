package Prototype.Pages;

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
        System.out.println("Entrez un numéro pour être redirigé vers ces " + "pages:");
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
        System.out.println("Entrez un numéro pour être redirigé vers ces " + "pages:");
        System.out.println("[1] Rechercher des travaux");
        System.out.println("[2] Soumettre une requête de travail");
        System.out.println("[3] Signaler un problème à la ville");
        String response = scanner.nextLine();
        switch (response) {
            case "1" -> {
                return "Rechercher des travaux";
            }
            case "2" -> {
                return "Soumettre une requête de travail";
            }
            case "3" -> {
                return "Signaler un problème à la ville";
            }
            case null, default -> {
                return wrongPageChoice("menuLoggedResident");
            }
        }
    }

    public static String mainMenuLoggedIntervenant() {
        System.out.println("Entrez un numéro pour être redirigé vers ces " + "pages:");
        System.out.println("[1] Consulter la liste des requêtes de travail");
        System.out.println("[2] Soumettre un nouveau projet de travaux");
        System.out.println("[3] Mettre à jour les informations d'un chantier");
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
            case null, default -> {
                return wrongPageChoice("menuLoggedIntervenant");
            }
        }
    }
}
