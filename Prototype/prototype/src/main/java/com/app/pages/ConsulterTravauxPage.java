package com.app.pages;

import java.util.Scanner;

import static com.app.controllers.ConsulterTravauxController.consulterTravauxEnCours;

public class ConsulterTravauxPage {

    // Méthode de menu pour l'utilisateur
    public static void consulterTravauxEnCoursMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voici la liste de tout les travaux en cours: ");
        consulterTravauxEnCours();
        System.out.println("\n[1] Retour au menu principal");
        while (!scanner.nextLine().equals("1")) {
            System.out.println("\n[1] Retour au menu principal");
        }
    }
}
