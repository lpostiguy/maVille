package com.app.pages;

import com.app.models.Projet;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.app.controllers.ConsulterTravauxController.consulterTravauxEnCours;
import static com.app.controllers.ProjetController.consulterProjets;

public class ConsulterTravauxPage {

    // Méthode de menu pour l'utilisateur
    public static void consulterTravauxMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean filtrer;
        boolean rechercher;
        while (true) {
            System.out.println("\n------ Menu Consulter Travaux et Projets------");
            System.out.println("[1] Retour au menu principal");
            System.out.println("[2] Rechercher des travaux");
            System.out.println("[3] Filtrer les travaux en cours");
            System.out.println("[4] Consulter les projets");
            System.out.println("------------------------------------");
            String responseMenu = scanner.nextLine();
            switch (responseMenu) {
                case "1" -> {
                    return;
                }
                case "2" -> {
                    rechercher = true;
                    while (rechercher) {
                        boolean validEntry = false;
                        String responseRecherche = null;
                        while (!validEntry) {
                            System.out.println("\n----- Menu Recherche " +
                                "Travaux" +
                                " -----");
                            System.out.println("\n[1] Retour");
                            System.out.println("[2] Rechercher des travaux " + "par titre");
                            System.out.println("[3] Rechercher des travaux " + "par type de travaux");
                            System.out.println("[4] Rechercher des travaux " + "par quartier");
                            System.out.println("------------------------------------");
                            responseRecherche = scanner.nextLine();
                            switch (responseRecherche) {
                                case "1", "2", "3", "4" -> validEntry = true;
                                default ->
                                    System.out.println("Entrée invalide");
                            }
                        }
                        switch (responseRecherche) {
                            case "1" -> {
                                rechercher = false;
                            }
                            case "2" -> {
                                System.out.println("Entrer un titre de " +
                                    "travaux à rechercher:");
                                String titreRecherche = scanner.nextLine();
                                consulterTravauxEnCours("occupancy_name",
                                    titreRecherche, true);
                            }
                            case "3" -> {
                                System.out.println("Type de travaux: ");
                                System.out.println("– Travaux routiers");
                                System.out.println("– Travaux de gaz ou " +
                                    "électricité");
                                System.out.println("– Construction ou " + "r" +
                                    "énovation");
                                System.out.println("– Entretien paysager");
                                System.out.println("– Travaux liés aux " +
                                    "transports en commun");
                                System.out.println("– Travaux de " +
                                    "signalisation et éclairage");
                                System.out.println("– Travaux souterrains");
                                System.out.println("– Travaux résidentiel");
                                System.out.println("– Entretien urbain");
                                System.out.println("– Entretien des réseaux " + "de télécommunication");
                                System.out.println(
                                    "----------------------------");
                                System.out.println("Entrer un type de " +
                                    "travaux à rechercher:");
                                String typeRecherche = scanner.nextLine();
                                consulterTravauxEnCours("reason_category",
                                    typeRecherche, true);
                            }
                            case "4" -> {
                                System.out.println("Entrer un quartier à " +
                                    "rechercher:");
                                String quartierRecherche = scanner.nextLine();
                                consulterTravauxEnCours("boroughid",
                                    quartierRecherche, true);
                            }
                            default -> {
                                System.out.println("Voici la liste de tout " + "les " + "travaux, sans recherche: ");
                                consulterTravauxEnCours(null, null, true);
                            }
                        }
                        if (rechercher) {
                            validEntry = false;
                            String responseExit = null;
                            while (!validEntry) {
                                System.out.println("\n[1] Retour");
                                System.out.println("[2] Faire une autre " +
                                    "recherche");
                                responseExit = scanner.nextLine();
                                switch (responseExit) {
                                    case "1", "2" -> validEntry = true;
                                    default ->
                                        System.out.println("Entrée invalide");
                                }
                            }
                            responseExit = scanner.nextLine();
                            if (Objects.equals(responseExit, "1")) {
                                rechercher = false;
                                break;
                            }
                        }
                    }
                }
                case "3" -> {
                    filtrer = true;
                    while (filtrer) {
                        System.out.println("\n------ Menu Filtrer " +
                            "Travaux" +
                            "------");
                        System.out.println("\n[1] Retour");
                        System.out.println("[2] Filter les travaux en cours " + "par type de travaux");
                        System.out.println("[3] Filter les travaux en cours " + "par quartier");
                        System.out.println("[4] Ne pas filter les travaux en "
                            + "cours.");
                        System.out.println("------------------------------------");
                        String responseFilter = scanner.nextLine();
                        switch (responseFilter) {
                            case "1" -> {
                                filtrer = false;
                            }
                            case "2" -> {
                                System.out.println("Voici la liste de tout " + "les travaux groupés par type de travaux:" + " ");
                                consulterTravauxEnCours("reason_category",
                                    null, false);
                            }
                            case "3" -> {
                                System.out.println("Voici la liste de tout " + "les travaux groupés par quartier: ");
                                consulterTravauxEnCours("boroughid", null,
                                    false);
                            }
                            default -> {
                                System.out.println("Voici la liste de tout " + "les travaux en cours sans filtre: ");
                                consulterTravauxEnCours(null, null, false);
                            }
                        }
                        if (filtrer) {
                            boolean validEntry = false;
                            String responseExit = null;
                            while (!validEntry) {
                                System.out.println("\n[1] Retour");
                                System.out.println("[2] Appliquer un filtre");
                                responseExit = scanner.nextLine();
                                switch (responseExit) {
                                    case "1", "2" -> validEntry = true;
                                    default ->
                                        System.out.println("Entrée invalide");
                                }
                            }
                            responseExit = scanner.nextLine();
                            if (Objects.equals(responseExit, "1")) {
                                filtrer = false;
                            }
                        }
                    }
                }
                case "4" -> {
                    List<Projet> projets = consulterProjets();
                    if(projets.isEmpty()) {
                        System.out.println("Aucun projets n'existe pour le moment");
                    }
                    else {
                        System.out.println("Voici la liste des projets:");
                        for (Projet projet : projets) {
                            System.out.println(projets.toString());
                        }
                    }
                    System.out.println("\n[1] Retour");
                    while (!scanner.nextLine().equals("1")) {
                        System.out.println("\n[1] Retour");
                    }
                }
            }
        }
    }
}
