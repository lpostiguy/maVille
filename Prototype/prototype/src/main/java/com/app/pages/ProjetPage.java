package com.app.pages;

import com.app.models.Projet;
import com.app.models.User.User;

import java.util.*;

import static com.app.controllers.NotificationsController.envoyerNotification;
import static com.app.controllers.ProjetController.*;
import static com.app.controllers.UserController.findUsersByBoroughId;
import static com.app.utils.RegexChecker.estFormatDateValide;

public class ProjetPage {

    // Méthode de menu pour l'utilisateur
    public static void soumettreProjetMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n------ Menu Projets ------");
            System.out.println("[1] Retour au menu principal");
            System.out.println("[2] Consulter ses projets");
            System.out.println("[3] Soumettre un projet");
            System.out.println("------------------------------------");
            String responseMenu = scanner.nextLine();
            switch (responseMenu) {
                case "1" -> {
                    return;
                }
                case "2" -> {

                    System.out.println("\n------ Vos Projets -----");

                    List<Projet> projets = consulterProjet(user);
                    int i = 1;
                    if (projets.isEmpty()) {
                        System.out.println("Vous n'avez pas encore soumis de " + "projet");
                    } else {
                        for (i = 1; i < projets.size(); i++) {
                            System.out.printf("\n[%d] %s%n", i, projets.get(i).toString());
                        }
                    }
                    boolean entreValide = false;

                    while (!entreValide) {
                        System.out.println("\n[1] Retour");
                        System.out.println("[2] Modifier le statut d'un projet");
                        Scanner scanner2 = new Scanner(System.in);
                        String choix2 = scanner2.nextLine();
                        if (Objects.equals(choix2, "1")) {
                            entreValide = true;
                        } else if (Objects.equals(choix2, "2")) {
                            entreValide = true;
                            System.out.println("Veuillez entrer le numéro du " +
                                "projet dont vous souhaitez modifier le statut:");
                            Scanner scanner3 = new Scanner(System.in);
                            int numProjet = scanner3.nextInt();

                            while (!(numProjet <= i)) {
                                System.out.print("\nEntrez un numéro valide : ");
                                if (scanner.hasNextInt()) {
                                    numProjet = scanner.nextInt();
                                } else {
                                    scanner.next();
                                }
                            }
                            System.out.print("\nEntrer un statut (En cours, Suspendu, Terminé): ");
                            String statut = scanner2.nextLine();
                            boolean miseAJour = mettreAJourStatutProjet(statut, projets.get(numProjet));

                            if (miseAJour) {
                                List<String> userIds = new ArrayList<>();
                                for (String quartierAffecte :
                                    projets.get(numProjet).getQuartiersAffectes()) {
                                    List<String> userBoroughIds = findUsersByBoroughId(quartierAffecte);
                                    if (!userBoroughIds.isEmpty()) {
                                        userIds.addAll(userBoroughIds);
                                    }
                                }
                                if (!userIds.isEmpty()) {
                                    for (String userId : userIds) {
                                        envoyerNotification("Le statut du " +
                                                "projet " + projets.get(numProjet).getTitre() +
                                                " dans votre quartier a été " +
                                                "mis à jour de: '" + projets.get(numProjet).getStatut() + "' à: '" + statut + "'.",
                                            userId);
                                    }
                                }
                            }
                        }
                    }
                }
                case "3" -> {
                    System.out.println("\nVeuillez entrer les informations suivantes pour soumettre un projet.");

                    System.out.print("Titre du projet: ");
                    String titre = scanner.nextLine();

                    System.out.print("Description du projet: ");
                    String description = scanner.nextLine();

                    System.out.print("Type de travaux : ");
                    String typeTravaux = scanner.nextLine();

                    // Collecte des quartiers affectés
                    List<String> quartiersAffectes = new ArrayList<>();
                    System.out.println("Entrez les quartiers affectés (tapez 'fin' pour terminer) :");
                    while (true) {
                        System.out.print("Quartier : ");
                        String quartier = scanner.nextLine();
                        if (quartier.equalsIgnoreCase("fin")) {
                            break;
                        }
                        quartiersAffectes.add(quartier);
                    }

                    // Collecte des rues affectées
                    List<String> ruesAffectees = new ArrayList<>();
                    System.out.println("Entrez les rues affectées (tapez 'fin' pour terminer) :");
                    while (true) {
                        System.out.print("Rue : ");
                        String rue = scanner.nextLine();
                        if (rue.equalsIgnoreCase("fin")) {
                            break;
                        }
                        ruesAffectees.add(rue);
                    }

                    boolean dateDebutValide = false;
                    String dateDebut = "";
                    while (!dateDebutValide) {
                        System.out.println("Date de début (format YYYY-MM-DD):");
                        dateDebut = scanner.nextLine();
                        if (estFormatDateValide(dateDebut)) {
                            dateDebutValide = true;
                        } else {
                            System.out.println("La date de début entrée n'est pas du format YYYY-MM-DD");
                        }
                    }

                    boolean dateFinValide = false;
                    String dateFin = "";
                    while (!dateFinValide) {
                        System.out.println("Date de fin (format YYYY-MM-DD):");
                        dateFin = scanner.nextLine();
                        if (estFormatDateValide(dateFin)) {
                            dateFinValide = true;
                        } else {
                            System.out.println("La date de fin entrée n'est pas du format YYYY-MM-DD");
                        }
                    }

                    // Appel de la méthode pour soumettre le projet avec les données entrées
                    System.out.println(soumettreProjet(user.getUserId(), titre, description, typeTravaux, dateDebut, dateFin, quartiersAffectes, ruesAffectees, "prévu"));
                    List<String> userIds = new ArrayList<>();
                    for (String quartierAffecte : quartiersAffectes) {
                        List<String> userBoroughIds = findUsersByBoroughId(quartierAffecte);
                        if (!userBoroughIds.isEmpty()) {
                            userIds.addAll(userBoroughIds);
                        }
                    }
                    if (!userIds.isEmpty()) {
                        String message = "'\n" + "Un nouveau projet à été " +
                            "créé dans votre quartier: \n" +
                            "------------------------------------\n" +
                            "Titre : '" + titre + "'\n" +
                            "Description : '" + description + "'\n" +
                            "Date de Debut : '" + dateDebut + "'\n" +
                            "Date de Fin : '" + dateFin + "'\n" +
                            "Type de Travaux : '" + typeTravaux + "'\n" +
                            "Quartiers Affectés : " + quartiersAffectes + "\n" +
                            "Rues Affectées : " + ruesAffectees + "\n" +
                            "Statut : 'prévu'";
                        String jsonSafeMessage = message.replace("\n", "\\n");
                        for (String userId : userIds) {
                            envoyerNotification(jsonSafeMessage, userId);
                        }
                    }
                }
            }
        }
    }
}
