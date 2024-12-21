package com.app.pages;

import java.util.Objects;
import java.util.Scanner;

import static com.app.controllers.ConsulterEntravesController.consulterEntraves;

public class ConsulterEntravesPage {

    // Méthode de menu pour l'utilisateur
    public static void consulterEntraveMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean filtrer;
        boolean rechercher;
        while (true) {
            System.out.println("\n------ Menu Consulter Entraves ------");
            System.out.println("\n[1] Retour au menu principal");
            System.out.println("[2] Rechercher des entraves");
            System.out.println("[3] Filtrer les entraves par rue");
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
                                "Entraves" +
                                " -----");
                            System.out.println("\n[1] Retour");
                            System.out.println("[2] Rechercher des entraves par travail");
                            System.out.println("[3] Rechercher des entraves par rue");
                            System.out.println("------------------------------------");
                            responseRecherche = scanner.nextLine();
                            switch (responseRecherche) {
                                case "1", "2", "3" -> validEntry = true;
                                default ->
                                    System.out.println("Entrée invalide");
                            }

                        }
                        switch (responseRecherche) {
                            case "1" -> {
                                rechercher = false;
                            }
                            case "2" -> {
                                System.out.println("Entrer l'identifiant du travail :");
                                String titreRecherche = scanner.nextLine();
                                consulterEntraves("request_id",
                                    titreRecherche, true);
                            }
                            case "3" -> {
                                System.out.println("Entrer le nom de la rue :");
                                String titreRecherche = scanner.nextLine();
                                consulterEntraves("street_id",
                                    titreRecherche, true);
                            }
                        }
                        if (rechercher) {
                            validEntry = false;
                            String responseExit;
                            while (!validEntry) {
                                System.out.println("\n[1] Retour");
                                System.out.println("[2] Faire une autre " +
                                    "recherche");
                                Scanner scanner2 = new Scanner(System.in);
                                responseExit = scanner2.nextLine();
                                switch (responseExit) {
                                    case "1", "2" -> {
                                        validEntry = true;
                                    }
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
                    System.out.println("Voici la liste de toutes " + "les entraves groupées par rue: ");
                    consulterEntraves("street_id", null, false);


                        if (filtrer) {
                            boolean validEntry = false;
                            String responseExit = null;
                            while (!validEntry) {
                                System.out.println("\n[1] Retour");
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

//        System.out.println("Voici la liste de toutes les entraves routières: ");
//        consulterEntraves();
//        System.out.println("\n[1] Retour au menu principal");
//        while (!scanner.nextLine().equals("1")) {
//            System.out.println("\n[1] Retour au menu principal");

            }
        }
    }
