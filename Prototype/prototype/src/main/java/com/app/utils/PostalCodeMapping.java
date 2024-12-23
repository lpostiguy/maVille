package com.app.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet de charger et de gérer une correspondance entre les
 * codes postaux et les quartiers. Elle charge les données de codes postaux
 * depuis un fichier CSV.
 */
public class PostalCodeMapping {

    private final Map<String, String> postalCodeToDistrict = new HashMap<>();

    public PostalCodeMapping(String csvFilePath) {
        loadPostalCodeData(csvFilePath);
    }

    private void loadPostalCodeData(String csvFilePath) {
        try (InputStream inputStream =
                 getClass().getClassLoader().getResourceAsStream(csvFilePath)) {
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String postalPrefix = parts[0].trim();
                    String district = parts[1].trim();
                    postalCodeToDistrict.put(postalPrefix, district);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier CSV : " + e.getMessage());
        }
    }

    /**
     * Récupère le district correspondant à un code postal dans une adresse donnée.
     * Le code postal est extrait de l'adresse, et si le préfixe du code postal est trouvé,
     * le district correspondant est renvoyé. Si le code postal est invalide ou inconnu,
     * un message approprié est retourné.
     *
     * @param address L'adresse contenant le code postal. L'adresse doit être au format :
     *                [rue, ville, code postal].
     * @return Le district correspondant au code postal ou un message d'erreur si le code
     *         postal est inconnu ou invalide.
     */
    public String getDistrictByPostalCode(String address) {
        String[] parts = address.split(",");
        String postalCode = "";

        if (parts.length == 3) {
            postalCode = parts[2].trim();
        } else
            return null;

        if (postalCode.length() == 6) {
            String prefix = postalCode.substring(0, 3);
            return postalCodeToDistrict.getOrDefault(prefix, "Quartier " +
                "inconnu");
        } else
            return "Quartier inconnu";
    }
}
