package com.app.controllers;

import com.app.models.Travail;
import com.app.utils.ReponseApi;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsulterTravauxController {

    private static void displayTravail(Travail travail, boolean verifierActif) {
        if (!verifierActif | travailEstActif(travail)) {
            System.out.println("Titre : " + travail.getOccupancy_name());
            System.out.println("Type de travaux : " + travail.getReason_category());
            System.out.println("Quartier  : " + travail.getBoroughid());
            System.out.println("Date de début : " + travail.getDuration_start_date());
            System.out.println("Date de fin : " + travail.getDuration_end_date());
            System.out.println("----------------------------");
        }
    }

    // Méthode pour consulter les travaux en cours
    public static void consulterTravauxEnCours(String filterPar,
                                               String recherche,
                                               boolean effectuerRecherche) {
        try {
            URL url = new URL("https://donnees.montreal" + ".ca/api/3/action" +
                "/datastore_search?resource_id=cc41b532-f12d" + "-40fb-9f55" +
                "-eb58c9a2b12b");
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

                        List<Travail> travaux =
                            resultatTravaux.getTravauxRecords();

                        if (travaux != null) {
                            if (effectuerRecherche && recherche != null && !recherche.isEmpty()) {
                                System.out.println("Recherche en cours...");
                                List<String> motsRecherche =
                                    Arrays.stream(recherche.split("\\s+")).toList();
                                System.out.println("Résultat de " + "votre " + "recherche pour " + recherche + ":");
                                switch (filterPar.toLowerCase()) {
                                    case "occupancy_name":
                                        travaux =
                                            travaux.stream().filter(t -> t.getOccupancy_name() != null && motsRecherche.stream().anyMatch(word -> t.getOccupancy_name().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
                                        break;

                                    case "reason_category":
                                        travaux =
                                            travaux.stream().filter(t -> t.getReason_category() != null && motsRecherche.stream().anyMatch(word -> t.getReason_category().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
                                        break;

                                    case "boroughid":
                                        travaux =
                                            travaux.stream().filter(t -> t.getBoroughid() != null && motsRecherche.stream().anyMatch(word -> t.getBoroughid().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
                                        break;

                                    default:
                                        System.out.println("Type de " +
                                            "recherche" + " inconnu : " + filterPar);
                                        return;
                                }

                                travaux.forEach(travail -> displayTravail(travail, true));
                            } else if (!effectuerRecherche && filterPar != null) {
                                Map<String, List<Travail>> groupedTravaux =
                                    travaux.stream().collect(Collectors.groupingBy(filterPar.equalsIgnoreCase("reason_category") ? Travail::getReason_category : Travail::getBoroughid));

                                groupedTravaux.forEach((key, group) -> {
                                    System.out.println("\n" + filterPar + ": "
                                        + key);
                                    group.forEach(travail -> displayTravail(travail, true));
                                });
                            }
                            // Par défaut : Tout montrer
                            else {
                                travaux.forEach(travail -> displayTravail(travail, true));
                            }
                        } else {
                            System.out.println("Aucune donnée trouvée dans " +
                                "la" + " liste des travaux.");
                        }
                    } else {
                        System.out.println("Aucune donnée trouvée ou erreur " + "dans la réponse de l'API.");
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


    // Méthode qui vérifie que les travaux sont en cours ou planifiés pour
    // les trois prochains mois seulement.
    private static boolean travailEstActif(Travail travail) {
        LocalDate dateActuelle = LocalDate.now();
        LocalDate dansTroisMois = dateActuelle.plusMonths(3);

        // Utilisation du format ISO 8601 avec fuseau horaire
        DateTimeFormatter formatteur = DateTimeFormatter.ISO_DATE_TIME;

        try {
            // Conversion des chaînes de caractères en LocalDateTime puis en
            // LocalDate
            LocalDate dateDebut =
                LocalDateTime.parse(travail.getDuration_start_date(),
                    formatteur).atZone(ZoneId.of("UTC")).toLocalDate();
            LocalDate dateFin =
                LocalDateTime.parse(travail.getDuration_end_date(),
                    formatteur).atZone(ZoneId.of("UTC")).toLocalDate();

            // Vérification de la date de début et de la date de fin du
            // travail, pour savoir s'il est actif
            if (dateDebut.isBefore(dansTroisMois) && dateFin.isAfter(dateActuelle)) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse des dates : " + e.getMessage());
        }

        return false;
    }
}
