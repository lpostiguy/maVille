package com.app.controllers;

import com.app.utils.JsonFormatting;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConsulterRequetesTravailController {

    // Méthode pour consulter les requêtes de travail
    public static void consulterRequetesTravail() {
        try {
            URL url = new URL("http://localhost:8000/requete-travail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                StringBuilder reponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        reponse.append(scanner.nextLine());
                    }
                }
                String formattedJson =
                    JsonFormatting.jsonToText(reponse.toString());
                System.out.println(formattedJson);

            } else {
                System.out.println("Erreur : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
