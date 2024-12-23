package com.app.controllers;

import com.app.models.Entrave;
import com.app.models.RequeteTravail;
import com.app.models.Travail;
import com.app.utils.ReponseApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Classe qui gère la consultation des entraves via l'API de la ville de
 * Montréal. Cette classe permet d'effectuer des recherches, de filtrer et
 * d'afficher les informations des entraves.
 */
public class ConsulterEntravesController {

    private static void displayEntrave(Entrave entrave) {
        System.out.println("Nom de la rue affectée : " + entrave.getName());
        System.out.println("Impact sur la rue : " + entrave.getStreetimpacttype());
        System.out.println("Impact sur le trottoir : " + entrave.getSidewalk_blockedtype());
        System.out.println("Type de blocage de la " + "voie réservée : " + entrave.getReservedlane_blockedtype());
        System.out.println("Nom de début de rue : " + entrave.getFromname());
        System.out.println("Nom de fin de rue : " + entrave.getToname());
        System.out.println("Longueur de l'impact : " + entrave.getLength() + " mètres");
        System.out.println("----------------------------");
        }

    /**
     * Consulte les entraves à partir de l'API ouverte de la ville de Montréal.
     * Cette méthode permet de filtrer et d'afficher les résultats en fonction
     * des critères donnés.
     *
     * @param filterPar        Le champ par lequel les entraves doivent être
     *                         regroupées, par exemple "street_id".
     *                         Peut être null si aucun regroupement n'est
     *                         nécessaire.
     * @param recherche        Le terme de recherche à utiliser pour filtrer les
     *                         entraves. Si effectuerRecherche est true
     *                         et recherche est non vide, la recherche sera
     *                         effectuée sur ce champ.
     * @param effectuerRecherche Indicateur booléen pour savoir si une recherche
     *                          doit être effectuée.
     *                          Si true, la méthode applique la recherche selon
     *                          filterPar et recherche.
     */
    public static void consulterEntraves(String filterPar, String recherche, boolean effectuerRecherche) {
        try {
            URL url =
                new URL("https://donnees.montreal" + ".ca/api/3/action" +
                    "/datastore_search?resource_id=a2bc8014-488c" + "-495d" + "-941b" + "-e7ae1999d1bd");
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

                        List<Entrave> entraves =
                            resultatEntraves.getEntraveRecords();

                        if (entraves != null) {
                            if (effectuerRecherche && recherche != null && !recherche.isEmpty()) {
                                System.out.println("Recherche en cours...");

                                System.out.println("Résultat de " + "votre " + "recherche pour " + recherche + ":\n");
                                switch (filterPar.toLowerCase()) {
                                    case "request_id":
                                        entraves =
                                            entraves.stream().filter(t -> t.getId_request() != null && recherche.equals(t.getId_request())).toList();
                                        break;

                                    case "street_id":
                                        List<String> motsRecherche =
                                            Arrays.stream(recherche.split("\\s+")).toList();
                                        entraves =
                                            entraves.stream().filter(t -> t.getStreetid() != null && motsRecherche.stream().anyMatch(word -> t.getStreetid().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
                                        break;

                                    default:
                                        System.out.println("Type de " +
                                            "recherche" + " inconnu : " + filterPar);
                                        return;
                                }

                                entraves.forEach(entrave -> displayEntrave(entrave));

                            }

                            if (!effectuerRecherche && filterPar != null) {
                                Map<String, List<Entrave>> groupedEntraves =
                                    entraves.stream().collect(Collectors.groupingBy(Entrave::getStreetid));

                                groupedEntraves.forEach((key, group) -> {
                                    System.out.println("\n" + filterPar + ": "
                                        + key + "\n");
                                    group.forEach(entrave -> displayEntrave(entrave));
                                });
                            }
                        } else {
                            System.out.println("Erreur : La réponse ne contient " + "pas les données attendues.");
                        }
                    }
                }
            } else {
                    System.out.println("Erreur HTTP : " + conn.getResponseCode());
                }
        }
        catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de la " +
                "consultation des entraves : " + e.getMessage());
        }
    }
}
