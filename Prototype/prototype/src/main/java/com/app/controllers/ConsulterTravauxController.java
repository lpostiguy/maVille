package com.app.controllers;

import com.app.models.Travail;
import com.app.utils.ReponseApi;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsulterTravauxController {

    // Méthode pour consulter les travaux
    public static void consulterTravauxEnCours() {
        try {
            URL url =
                new URL("https://donnees.montreal" + ".ca/api/3/action" +
                    "/datastore_search?resource_id=cc41b532-f12d" + "-40fb" +
                    "-9f55" + "-eb58c9a2b12b");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                Gson gson = new Gson();
                try (InputStreamReader reader =
                         new InputStreamReader(conn.getInputStream())) {
                    ReponseApi reponse = gson.fromJson(reader,
                        ReponseApi.class);

                    if (reponse != null && reponse.isSuccess() && reponse.getResult() != null) {
                        ReponseApi.ResultatTravaux resultatTravaux =
                            gson.fromJson(gson.toJson(reponse.getResult()),
                                ReponseApi.ResultatTravaux.class);

                        if (resultatTravaux.getTravauxRecords() != null) {
                            for (Travail travail :
                                resultatTravaux.getTravauxRecords()) {
                                System.out.println("Titre : " + travail.getOccupancy_name());
                                System.out.println("Catégorie : " + travail.getPermitcategory());
                                System.out.println("Arrondissement : " + travail.getBoroughid());
                                System.out.println("Date de début : " + travail.getDuration_start_date());
                                System.out.println("Date de fin : " + travail.getDuration_end_date());
                                System.out.println("Longitude : " + travail.getLongitude());
                                System.out.println("Latitude : " + travail.getLatitude());
                                System.out.println(
                                    "----------------------------");
                            }
                        } else {
                            System.out.println("Aucune donnée trouvée ou " +
                                "erreur dans la réponse de l'API.");
                        }
                    }
                }
            } else {
                System.out.println("Erreur HTTP : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de la " +
                "consultation des travaux : " + e.getMessage());
        }
    }

}
