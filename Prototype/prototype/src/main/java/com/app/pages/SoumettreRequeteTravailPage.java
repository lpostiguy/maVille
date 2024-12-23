package com.app.pages;

import com.app.MongoDBConnection;
import com.app.controllers.RequeteTravailController;
import com.app.models.User.User;

import java.util.ArrayList;
import java.util.Scanner;

import static com.app.controllers.RequeteTravailController.soumettreRequeteTravail;
import static com.app.utils.RegexChecker.estFormatDateValide;

/**
 * Cette classe gère l'interface utilisateur pour la soumission d'une requête
 * de travail. Elle permet à un résident de soumettre une nouvelle requête
 * en entrant les informations nécessaires.
 */

public class SoumettreRequeteTravailPage {

    /**
     * Affiche le menu pour soumettre une nouvelle requête de travail et guide
     * l'utilisateur dans la saisie des informations nécessaires à la création
     * de la requête. Après la soumission, l'utilisateur peut retourner au menu
     * principal.
     *
     * @param user L'utilisateur connecté qui soumet la requête de travail
     *             (résident)
     */
    public static void soumettreRequeteTravailMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVeuillez entrer les informations suivantes pour" +
            " " + "soumettre une nouvelle requête de travail.");

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
            if (estFormatDateValide(dateDebutEspere)) {
                dateDebutEspereValide = true;
            } else {
                System.out.println("La date de début espérée entrée n'est " + "pas du format YYYY-MM-DD");
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
}
