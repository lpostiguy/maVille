package com.app.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalCodeMappingTest {

    // Vérifier que la classe renvoie une exception lorsqu'un fichier introuvable dans le
    // projet est passé
    @Test
    public void unknownFileTest() {
        assertThrows(NullPointerException.class, ()-> new PostalCodeMapping("csv"));
    }

    // Vérifier que la méthode getDistrictByPostalCode associe le code postal
    // au bon quartier de la ville de Montréal
    @Test
    public void validFileTest() {
        PostalCodeMapping postalCodeMapping = new PostalCodeMapping("codesPostaux.csv");
        String actualMapping1 = postalCodeMapping.getDistrictByPostalCode("198, Rue, H1A3W8");
        String actualMapping2 = postalCodeMapping.getDistrictByPostalCode("198, Rue, H2Y3W8");
        assertEquals("Rivière-des-Prairies-Pointe-aux-Trembles", actualMapping1);
        assertEquals("Ville-Marie", actualMapping2);
    }

    // Vérifier que la méthode getDistrictByPostalCode renvoie "Quartier inconnu"
    // lorsque le code postal n'est pas dans le fichier
    @Test
    public void notAValidBorough() {
        PostalCodeMapping postalCodeMapping = new PostalCodeMapping("codesPostaux.csv");
        String actualMapping = postalCodeMapping.getDistrictByPostalCode("198, Rue, HHH3W8");
        assertEquals("Quartier inconnu", actualMapping);
    }

}
