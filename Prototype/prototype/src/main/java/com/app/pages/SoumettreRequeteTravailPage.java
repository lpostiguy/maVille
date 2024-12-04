package com.app.pages;

import com.app.models.RequeteTravail;
import com.app.models.User.User;

import java.util.List;
import java.util.Scanner;

import static com.app.controllers.RequeteTravailController.consulterRequetesTravail;
import static com.app.controllers.RequeteTravailController.soumettreRequeteTravail;
import static com.app.utils.RegexChecker.isValidDateFormat;

public class SoumettreRequeteTravailPage {

    // Méthode de menu pour l'utilisateur
    public static void soumettreRequeteTravailMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer les informations suivantes pour " +
            "soumettre une nouvelle requête de travail.");

        System.out.print("Titre : ");
        String titre = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Type de travaux : ");
        String typeTravaux = scanner.nextLine();

        boolean dateDebutEspereValide = false;
        String dateDebutEspere = "";
        while (!dateDebutEspereValide) {
            System.out.println("Date de début espérée (format YYYY-MM-DD):");
            dateDebutEspere = scanner.nextLine();
            if (isValidDateFormat(dateDebutEspere)) {
                dateDebutEspereValide = true;
            } else {
                System.out.println("La date de début espérée entrée n'est " +
                    "pas du format YYYY-MM-DD");
            }
        }

        // Appel de la méthode pour soumettre la requête avec les données
        // entrées
        System.out.println(soumettreRequeteTravail(titre, description,
            typeTravaux, dateDebutEspere, user.getUserId(), true));
        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }

    public static void consulterRequeteTravailMenu(User user) {
        List<RequeteTravail> requetes = consulterRequetesTravail(user);
        if (requetes.isEmpty()) System.out.println("Vous n'avez pas de requêtes de travail pour l'instant.");
        else {
            System.out.println("Sélectionné la requête dont vous voulez faire le suivi:");
            for (int i = 0; i < requetes.size(); i++) {
                System.out.printf("[%d] %s%n", i + 1, requetes.get(i).getTitre());
            }

            // Lecture de l'entrée utilisateur
            Scanner scanner = new Scanner(System.in);
            int choix = -1;

            while (choix < 1 || choix > requetes.size()) {
                System.out.print("Entrez un numéro valide : ");
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                } else {
                    scanner.next(); // Consomme l'entrée invalide
                }
            }

            // Affichage des détails de la requête sélectionnée
            RequeteTravail requeteChoisie = requetes.get(choix - 1);
            System.out.println("\n--- Détails de la requête ---");
            System.out.println("Titre : " + requeteChoisie.getTitre());
            System.out.println("Description : " + requeteChoisie.getDescription());
            System.out.println("Type de travaux : " + requeteChoisie.getTypeTravaux());
            System.out.println("Date de début espéré : " + requeteChoisie.getDateDebutEspere());
            System.out.println("Identifiant de la requête : " + requeteChoisie.getId().getString("$oid"));
            System.out.println("Statut: " + requeteChoisie.getOpen());
        }
    }
}
