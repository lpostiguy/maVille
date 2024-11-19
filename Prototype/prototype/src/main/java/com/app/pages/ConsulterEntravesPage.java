package com.app.pages;

import java.util.Scanner;

import static com.app.controllers.ConsulterEntravesController.consulterEntraves;

public class ConsulterEntravesPage {

    // Méthode de menu pour l'utilisateur
    public static void consulterEntraveMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voici la liste de toutes les entraves routières: ");
        consulterEntraves();
        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }
}
