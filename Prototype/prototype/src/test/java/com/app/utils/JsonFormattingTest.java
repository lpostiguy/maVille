package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormattingTest {

    @Test
    public void jsonToTextFormatterJsonEnTexteSansId() {
        // JSON d'entrée
        String inputJson =
            "{\n" + "  \"titre\": \"Réparation de route\",\n" + "  " +
                "\"description\": \"Nid de poule nécessitant une réparation " + "immédiate\",\n" + "  \"typeTravaux\": \"Réparation\",\n" + "  \"dateDebutEspere\": \"2024-12-01\",\n" + "  " + "\"demandeurRequeteId\": \"67890\"\n" + "}";

        // Texte attendu
        String expectedOutput = "titre: Réparation de route\n" + "description"
            + ": Nid de poule nécessitant une réparation immédiate\n" +
            "typeTravaux: Réparation\n" + "dateDebutEspere: 2024-12-01\n" +
            "demandeurRequeteId: 67890\n";

        String actualOutput = JsonFormatting.jsonToTextRequeteTravail(inputJson);

        assertEquals(expectedOutput, actualOutput);
    }
}
