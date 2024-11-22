package com.app.controllers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SoumettreRequeteTravailController {

    // Méthode pour soumettre une nouvelle requête de travail
    public static String soumettreRequeteTravail(String titre,
                                                 String description,
                                                 String typeTravaux,
                                                 String dateDebutEspere,
                                                 String demandeurRequeteId) {
        try {
            URL url = new URL("http://localhost:8000/requete-travail");
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
                return "Requête de travail ajoutée avec succès.";
            } else {
                return "Erreur : " + conn.getResponseCode();
            }
        } catch (Exception e) {
            return "Erreur lors de la soumission de la requête " + "de" + " " +
                "travail.";
        }
    }

}
