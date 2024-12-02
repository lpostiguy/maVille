package com.app.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PostalCodeMapping {

    private Map<String, String> postalCodeToDistrict = new HashMap<>();

    public PostalCodeMapping(String csvFilePath) {
        loadPostalCodeData(csvFilePath);
    }

    private void loadPostalCodeData(String csvFilePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvFilePath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
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

    public String getDistrictByPostalCode(String address) {
        String[] parts = address.split(",");
        String postalCode = "";

        if (parts.length == 3) {
            postalCode = parts[2].trim();
        }
        else return null;

        if (postalCode.length() == 6) {
            String prefix = postalCode.substring(0, 3);
            return postalCodeToDistrict.getOrDefault(prefix, "Quartier inconnu");
        }
        else return "Quartier inconnu";
    }
}
