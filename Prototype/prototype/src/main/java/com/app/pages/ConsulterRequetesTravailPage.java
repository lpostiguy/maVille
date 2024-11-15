package com.app.pages;

import com.app.utils.JsonFormatting;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class ConsulterRequetesTravailPage {

    // Méthode pour consulter les requêtes de travail
    private static void consulterRequetesTravail() {
        try {
            URL url = new URL("http://localhost:7000/requete-travail");
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

    // Méthode de menu pour l'utilisateur
    public static void consulterRequeteTravailMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voici la liste de toutes les requêtes de " +
            "travail:");
        consulterRequetesTravail();
        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }
}
