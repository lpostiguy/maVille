package com.app.pages;

import com.app.controllers.UserController;
import com.app.models.Candidature;
import com.app.models.RequeteTravail;
import com.app.models.User.User;
import org.bson.Document;

import java.util.*;

import static com.app.controllers.RequeteTravailController.*;

public class ConsulterRequetesTravailPage {

    // Méthode de menu pour l'intervenant
    public static void consulterRequeteTravailMenu(User user) {
        List<RequeteTravail> requetes = consulterRequetesTravail();
        if (requetes == null) {
            System.out.println("Il n'y a pas de requêtes de travail pour " +
                "l'instant.");
            Scanner scanner = new Scanner(System.in);
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("[1] Retour au menu principal");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    validChoice = true;
                    return;
                } else {
                    System.out.println("Veuillez entrer un choix valide.");
                }
            }

        }
        else {
            System.out.println("Sélectionnez la requête que vous voulez " +
                "consulter:");
            Map<Integer, Integer> indexMapping = new HashMap<>();

            int nbRequetePrint = 0;

            // Afficher les requêtes actives

            System.out.println("\n------ Requêtes -----");
            for (int i = 0; i < requetes.size(); i++) {
                if (requetes.get(i).getActif()) {
                    nbRequetePrint++;
                    indexMapping.put(nbRequetePrint, i); // Associer l'indice
                    // affiché à l'indice réel
                    System.out.printf("[%d] %s%n", nbRequetePrint,
                        requetes.get(i).getTitre());
                }
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
            printRequete(requeteChoisie);

            Candidature candidatureIntervenant = null;
            if (requeteChoisie.getCandidatures() != null) {
                for (Candidature candidature : requeteChoisie.getCandidatures()) {
                    if (Objects.equals(candidature.getUserId(), user.getUserId())) {
                        candidatureIntervenant = candidature;
                    }
                }
            }

            boolean isValidChoice = false;
            while (!isValidChoice) {
                System.out.println("\n---- Menu Suivi Requete Travail ----");
                System.out.println("[1] Retour au menu principal");
                System.out.println("[2] Voir une autre requête");
                if (requeteChoisie.getActif() && candidatureIntervenant == null ) {
                    System.out.println("[3] Soumettre une candidature");
                }
                else if (candidatureIntervenant != null) {
                    System.out.println("[3] Voir votre candidature");
                }
                System.out.println("------------------------------------");
                Scanner scanner2 = new Scanner(System.in);
                String choice = scanner2.nextLine();
                switch (choice) {
                    case "1" -> {
                        return;
                    }
                    case "3" -> {
                        if (requeteChoisie.getActif() && candidatureIntervenant == null ) {
                            isValidChoice = true;
                            CandidaturePage.soumettreCandidaturePage(user, requeteChoisie);
                        }
                        else if (candidatureIntervenant != null) {
                            isValidChoice = true;
                            CandidaturePage.suiviCandidaturePage(user, requeteChoisie, candidatureIntervenant);
                        }
                        else { System.out.println("L'option choisie n'est pas " +
                            "disponible."); }
                    }
                    case "2" -> {
                        isValidChoice = true;
                        consulterRequeteTravailMenu(user);
                    }
                    default -> { System.out.println("L'option choisie n'est pas " +
                        "disponible.");
                    }
                }
            }
        }
    }


    public static void suiviRequeteTravailMenu(User user) {
        List<RequeteTravail> requetes = consulterRequetesTravail(user);
        if (requetes.isEmpty()) {
            System.out.println("Vous n'avez pas de requêtes de travail pour " +
                "l'instant.");

            Scanner scanner = new Scanner(System.in);
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("[1] Retour au menu principal");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    validChoice = true;
                    return;
                } else {
                    System.out.println("Veuillez entrer un choix valide.");
                }
            }

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
                System.out.println("Aucune requête n'est active");
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
                System.out.println("Aucune requête n'est archivée");
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
            printRequete(requeteChoisie);

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
                System.out.println("[4] Consulter les candidatures");
                System.out.println("[5] Faire le suivi d'une autre requête");
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
                    case "4" -> {
                        isValidChoice = true;
                        CandidaturePage.consulterCandidaturePage(user, requeteChoisie);
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
                    case "5" -> {
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

    public static void printRequete(RequeteTravail requete) {

        Document resident = UserController.findUserById(requete.getDemandeurRequete());
        String firstName = resident.getString("firstName");
        String lastName = resident.getString("lastName");
        String quartier = resident.getString("boroughId");

        System.out.println("\n------ Détails de la requête ------");
        System.out.println("Demandeur : " + firstName + " " + lastName);
        System.out.println("Titre : " + requete.getTitre());
        System.out.println("Description : " + requete.getDescription());
        System.out.println("Type de travaux : " + requete.getTypeTravaux());
        System.out.println("Quartier : " + quartier);
        System.out.println("Date de début espéré : " + requete.getDateDebutEspere());
        System.out.println("Statut: " + ((requete.getActif()) ? "Ouverte" : "Fermée" ));
        System.out.println("-----------------------------------");
    }
}
