package com.app.pages;

import com.app.controllers.CandidatureController;
import com.app.controllers.NotificationsController;
import com.app.controllers.UserController;
import com.app.models.Candidature;
import com.app.models.RequeteTravail;
import com.app.models.User.Intervenant;
import com.app.models.User.User;
import org.bson.Document;

import java.util.*;

import static com.app.controllers.CandidatureController.soumettreCandidature;
import static com.app.utils.RegexChecker.estFormatDateValide;

public class CandidaturePage {

    public static void soumettreCandidaturePage(User user, RequeteTravail requete) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVeuillez entrer les informations suivantes pour" +
            " " + "soumettre une candidature pour cette requête.");

        boolean dateDebutValide = false;
        String dateDebut = "";

        while (!dateDebutValide) {
            System.out.println("Date de début estimée (format YYYY-MM-DD):");
            dateDebut = scanner.nextLine();
            if (estFormatDateValide(dateDebut)) {
                dateDebutValide = true;
            } else {
                System.out.println("La date de début estimée entrée n'est pas du format YYYY-MM-DD");
            }
        }

        boolean dateFinValide = false;
        String dateFin = "";

        while (!dateFinValide) {
            System.out.println("Date de fin estimée (format YYYY-MM-DD):");
            dateFin = scanner.nextLine();
            if (estFormatDateValide(dateFin)) {
                dateFinValide = true;
            } else {
                System.out.println("La date de fin estimée entrée n'est pas du format YYYY-MM-DD");
            }
        }

        System.out.println("Entrez optionnellement un message au résident:");
        String message = scanner.nextLine();

        // Appel de la méthode pour soumettre la candidature avec les données
        // entrées
        String result = soumettreCandidature(requete.getId(), requete.getDemandeurRequete(), dateFin, dateDebut,
            "PENDING", false, user.getUserId(), message, "");
        if (result == "Requête de travail ajoutée avec succès.") {
            System.out.print(result);
            NotificationsController.envoyerNotification("" + user.getFirstName() + " " + user.getLastName()
            + " a appliqué pour votre requête de travail: " + requete.getTitre(), requete.getDemandeurRequete());
        }

        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }

    public static void consulterCandidaturePage(User user, RequeteTravail requete) {
        List<Candidature> candidatures = requete.getCandidatures();

        if (candidatures.isEmpty())
            System.out.println("Il n'y a pas de candidatures pour l'instant.");
        else {
            System.out.println("Sélectionnez la candidature que vous voulez " +
                "consulter:");

            Map<Integer, Integer> indexMapping = new HashMap<>();

            int nbCandidaturePrint = 0;

            // Afficher les candidatures

            System.out.println("\n------ Candidatures -----");
            for (int i = 0; i < candidatures.size(); i++) {
                Document intervenant = UserController.findUserById(candidatures.get(i).getUserId());
                String firstName = intervenant.getString("firstName");
                String lastName = intervenant.getString("lastName");
                nbCandidaturePrint++;
                indexMapping.put(nbCandidaturePrint, i); // Associer l'indice
                // affiché à l'indice réel
                System.out.printf("[%d] %s%n", nbCandidaturePrint, firstName + " " + lastName);
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

            // Récupérer l'index réel dans la liste `candidatures`
            int indexReel = indexMapping.get(choix);

            // Récupérer la candidature choisie
            Candidature candidatureChoisie = candidatures.get(indexReel);

            Document intervenant = UserController.findUserById(candidatureChoisie.getUserId());
            String firstName = intervenant.getString("firstName");
            String lastName = intervenant.getString("lastName");
            String type = intervenant.getString("entityType");

            System.out.println("\n------ Détails de la requête ------");
            System.out.println("Nom de l'intervenant : " + firstName + " " + lastName);
            System.out.println("Type d'entreprise : " + type);
            System.out.println("Date de début prévue : " + candidatureChoisie.getDateDebut());
            System.out.println("Date de fin prévue : " + candidatureChoisie.getDateFin());
            if (!Objects.equals(candidatureChoisie.getIntervenantMsg(), "")) {
                System.out.println("Message de l'intervenant : " + candidatureChoisie.getIntervenantMsg());
            }
            System.out.println("Statut: " + candidatureChoisie.getStatus());
            System.out.println("-----------------------------------");

            boolean isValidChoice = false;
            while (!isValidChoice) {
                System.out.println("\n---- Menu Suivi Candidature ----");
                System.out.println("[1] Retour au menu principal");
                System.out.println("[2] Faire le suivi d'une autre candidature");
                if (Objects.equals(candidatureChoisie.getStatus(), "PENDING")) {
                    System.out.println("[3] Accepter la candidature");
                    System.out.println("[4] Refuser la candidature");
                }
                System.out.println("------------------------------------");
                Scanner scanner2 = new Scanner(System.in);
                String choice = scanner2.nextLine();

                switch (choice) {
                    case "1" -> {
                        return;
                    }
                    case "2" -> {
                        isValidChoice = true;
                        consulterCandidaturePage(user, requete);
                    }

                    case "3" -> {
                        if (Objects.equals(candidatureChoisie.getStatus(), "PENDING")) {
                            isValidChoice = true;
                            changerStatutCandidaturePage(user, requete, candidatureChoisie, "ACCEPTED");
                        }
                    }

                    case "4" -> {
                        if (Objects.equals(candidatureChoisie.getStatus(), "PENDING")) {
                            isValidChoice = true;
                            changerStatutCandidaturePage(user, requete, candidatureChoisie, "REFUSED");
                        }
                    }
                }
            }
        }
    }

    public static void changerStatutCandidaturePage(User user, RequeteTravail requete, Candidature candidature, String accept) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez optionnellement un message à l'intervenant : ");
        String message = scanner.nextLine();
        if (Objects.equals(accept, "ACCEPTED")) candidature.setAccepted("ACCEPTED");
        else if (Objects.equals(accept, "REFUSED")) candidature.setAccepted("REFUSED");
        candidature.setResidentMsg(message);

        String response = CandidatureController.modifierStatutCandidature(requete.getId(), candidature.getId(), candidature.getStatus(),
            candidature.getResidentMsg());
        if (Objects.equals(response, "Candidature mise à jour partiellement avec succès.")) {
            System.out.println("Votre réponse a été envoyée à l'intervenant.");
        }
        System.out.println(response);
    }
}
