package com.app.pages;

import com.app.models.RequeteTravail;
import com.app.models.User.User;

import java.util.*;

import static com.app.controllers.RequeteTravailController.*;

public class ConsulterRequetesTravailPage {

    // Méthode de menu pour l'utilisateur
    public static void consulterRequeteTravailMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voici la liste de toutes les requêtes de " +
            "travail:");
        System.out.println(consulterRequetesTravail());
        System.out.println("[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("[1] Retour au menu principal");
        }
    }


    public static void suiviRequeteTravailMenu(User user) {
        List<RequeteTravail> requetes = consulterRequetesTravail(user);
        if (requetes.isEmpty()) {
            System.out.println("Vous n'avez pas de requêtes de travail pour " +
                "l'instant.");
        } else {
            System.out.println("Sélectionnez la requête dont vous voulez " +
                "faire le suivi:");

            // Table de correspondance entre l'indice affiché et l'indice réel
            Map<Integer, Integer> indexMapping = new HashMap<>();

            int nbRequetePrint = 0;

            // Afficher les requêtes actives

            boolean aucuneRequeteActive = true;
            System.out.println("\n------ Requêtes actives -----");
            for (int i = 0; i < requetes.size(); i++) {
                if (requetes.get(i).getActif()) {
                    aucuneRequeteActive = false;
                    nbRequetePrint++;
                    indexMapping.put(nbRequetePrint, i); // Associer l'indice
                    // affiché à l'indice réel
                    System.out.printf("[%d] %s%n", nbRequetePrint,
                        requetes.get(i).getTitre());
                }
            }

            if(aucuneRequeteActive) {
                System.out.println("Aucune requête est active");
            }

            boolean aucuneRequeteArchive = true;
            // Afficher les requêtes inactives
            System.out.println("\n----- Requêtes archivée -----");
            for (int i = 0; i < requetes.size(); i++) {
                if (!requetes.get(i).getActif()) {
                    aucuneRequeteArchive = false;
                    nbRequetePrint++;
                    indexMapping.put(nbRequetePrint, i); // Associer l'indice
                    // affiché à l'indice réel
                    System.out.printf("[%d] %s%n", nbRequetePrint,
                        requetes.get(i).getTitre());
                }
            }
            if(aucuneRequeteArchive) {
                System.out.println("Aucune requête est archivé");
            }

            // Lecture de l'entrée utilisateur
            Scanner scanner = new Scanner(System.in);
            int choix = -1;

            // Valider que le choix est dans les indices affichés
            while (!indexMapping.containsKey(choix)) {
                System.out.print("\nEntrez un numéro valide : ");
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                } else {
                    scanner.next(); // Consommer l'entrée invalide
                }
            }

            // Récupérer l'index réel dans la liste `requetes`
            int indexReel = indexMapping.get(choix);

            // Récupérer la requête choisie
            RequeteTravail requeteChoisie = requetes.get(indexReel);
            System.out.println("\n------ Détails de la requête ------");
            System.out.println("Titre : " + requeteChoisie.getTitre());
            System.out.println("Description : " + requeteChoisie.getDescription());
            System.out.println("Type de travaux : " + requeteChoisie.getTypeTravaux());
            System.out.println("Date de début espéré : " + requeteChoisie.getDateDebutEspere());
            System.out.println("Statut: " + requeteChoisie.getActif());
            System.out.println("-----------------------------------");

            boolean isValidChoice = false;
            boolean isValidSecondChoice = false;
            while (!isValidChoice) {
                System.out.println("\n---- Menu Suivi Requete Travail ----");
                System.out.println("[1] Retour au menu principal");
                if (requeteChoisie.getActif()) {
                    System.out.println("[2] Archiver la requête");
                } else {
                    System.out.println("[2] Désarchiver la requête");
                }
                System.out.println("[3] Effacer la requête");
                System.out.println("[4] Faire le suivi d'une autre requête");
                System.out.println("------------------------------------");
                Scanner scanner2 = new Scanner(System.in);
                String choice = scanner2.nextLine();
                switch (choice) {
                    case "1" -> {
                        return;
                    }
                    case "2" -> {
                        isValidChoice = true;
                        mettreAJourStatutRequeteTravail(!requeteChoisie.getActif(), requeteChoisie);
                        while (!isValidSecondChoice) {
                            System.out.println("[1] Retour au menu principal");
                            System.out.println("[2] Faire le suivi d'une " +
                                "autre " + "requête");
                            Scanner scanner3 = new Scanner(System.in);
                            String secondChoice = scanner3.nextLine();
                            if (Objects.equals(secondChoice, "1")) {
                                return;
                            } else if (Objects.equals(secondChoice, "2")) {
                                isValidSecondChoice = true;
                                suiviRequeteTravailMenu(user);
                            }
                        }
                    }
                    case "3" -> {
                        isValidChoice = true;
                        deleteRequeteTravail(requeteChoisie);
                        while (!isValidSecondChoice) {
                            System.out.println("[1] Retour au menu principal");
                            System.out.println("[2] Faire le suivi d'une " +
                                "autre " + "requête");
                            Scanner scanner3 = new Scanner(System.in);
                            String secondChoice = scanner3.nextLine();
                            if (Objects.equals(secondChoice, "1")) {
                                return;
                            } else if (Objects.equals(secondChoice, "2")) {
                                isValidSecondChoice = true;
                                suiviRequeteTravailMenu(user);
                            }
                        }
                    }
                    case "4" -> {
                        isValidChoice = true;
                        suiviRequeteTravailMenu(user);
                    }
                    default -> {
                        System.out.println("L'option choisie n'est pas " +
                            "disponible");
                    }
                }
            }
        }
    }
}
