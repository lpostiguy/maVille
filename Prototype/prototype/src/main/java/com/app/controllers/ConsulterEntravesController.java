package com.app.controllers;

import com.app.models.Entrave;
import com.app.utils.ReponseApi;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsulterEntravesController {

    // Méthode pour consulter les entraves
    public static void consulterEntraves() {
        try {
            URL url =
                new URL("https://donnees.montreal" + ".ca/api/3/action" +
                    "/datastore_search?resource_id=a2bc8014-488c" + "-495d" +
                    "-941b" + "-e7ae1999d1bd");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                Gson gson = new Gson();
                try (InputStreamReader reader =
                         new InputStreamReader(conn.getInputStream())) {
                    ReponseApi reponse = gson.fromJson(reader,
                        ReponseApi.class);

                    if (reponse != null && reponse.isSuccess() && reponse.getResult() != null) {
                        ReponseApi.ResultatEntraves resultatEntraves =
                            gson.fromJson(gson.toJson(reponse.getResult()),
                                ReponseApi.ResultatEntraves.class);

                        if (resultatEntraves.getEntraveRecords() != null) {
                            for (Entrave entrave :
                                resultatEntraves.getEntraveRecords()) {
                                System.out.println("Nom de la rue affectée : "
                                    + entrave.getName());
                                System.out.println("Impact sur la rue : " + entrave.getStreetimpacttype());
                                System.out.println("Impact sur le trottoir : "
                                    + entrave.getSidewalk_blockedtype());
                                System.out.println("Type de blocage de la " + "voie réservée : " + entrave.getReservedlane_blockedtype());
                                System.out.println("Nom de début de rue : " + entrave.getFromname());
                                System.out.println("Nom de fin de rue : " + entrave.getToname());
                                System.out.println("Longueur de l'impact : " + entrave.getLength() + " mètres");
                                System.out.println(
                                    "----------------------------");
                            }
                        } else {
                            System.out.println("Aucune donnée trouvée dans " + "'records'.");
                        }
                    } else {
                        System.out.println("Erreur : La réponse ne contient " + "pas les données attendues.");
                    }
                }
            } else {
                System.out.println("Erreur HTTP : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de la " +
                "consultation des entraves : " + e.getMessage());
        }
    }
}
