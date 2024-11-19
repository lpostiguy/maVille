package com.app.pages;

import java.util.Scanner;

import static com.app.controllers.ConsulterRequetesTravailController.consulterRequetesTravail;

public class ConsulterRequetesTravailPage {

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
