package com.app.pages;

import com.app.models.User.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static com.app.utils.RegexChecker.isValidDateFormat;

public class SoumettreRequeteTravailPage {

    // Méthode pour soumettre une nouvelle requête de travail
    // Méthode pour soumettre une nouvelle requête de travail
    private static void soumettreRequeteTravail(String titre,
                                                String description,
                                                String typeTravaux,
                                                String dateDebutEspere,
                                                String demandeurRequeteId) {
        try {
            URL url = new URL("http://localhost:7000/requete-travail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Construire les données JSON en fonction des entrées utilisateur
            String jsonInputString = String.format("{ \"titre\": \"%s\", " +
                "\"description\": \"%s\", \"typeTravaux\": \"%s\", " +
                "\"dateDebutEspere\": \"%s\", \"demandeurRequeteId\": \"%s\" "
                + "}", titre, description, typeTravaux, dateDebutEspere,
                demandeurRequeteId);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201) {
                System.out.println("Requête de travail ajoutée avec succès.");
            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la soumission de la requête " + "de" + " travail.");
        }
    }

    // Méthode de menu pour l'utilisateur
    public static void soumettreRequeteTravailMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer les informations suivantes pour " + "soumettre une nouvelle requête de travail.");

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
                System.out.println("La date de début espérée entrée n'est " + "pas du format YYYY-MM-DD");
            }
        }

        // Appel de la méthode pour soumettre la requête avec les données
        // entrées
        soumettreRequeteTravail(titre, description, typeTravaux,
            dateDebutEspere, user.getId());
        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }
}
