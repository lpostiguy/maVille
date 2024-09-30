package Prototype.Pages;

import java.util.Objects;
import java.util.Scanner;

public class LoginPage {

    static Scanner scanner = new Scanner(System.in);
    static boolean isLoggedIn = false;

    public static boolean loginPage() {
        System.out.println("[1] Retour");
        System.out.println("Entrez votre adresse courriel:");
        String email = scanner.nextLine();
        if (Objects.equals(email, "1")) {
            return false;
        } else if (email.contains("@")) {
            System.out.println("Entrez votre mot de passe");
            String password = scanner.nextLine();
            // TODO: Ajouter la logique qui compare les données rentrées par
            //  le user et les données présentes dans une base de donnée.
            if (!Objects.equals(password, "")) {
                System.out.println("Connection réussie");
                isLoggedIn = true;
                return true;
            } else {
                System.out.println("[1] Retour");
                System.out.println("[2] Réessayer");
                System.out.println("Mauvais mot de passe ou mauvaise " +
                        "adresse" + " courriel");
                String response = scanner.nextLine();
                if (Objects.equals(response, "1")) {
                    return false;
                } else {
                    loginPage();
                }
            }
        } else {
            System.out.println("[1] Retour");
            System.out.println("[2] Réessayer");
            System.out.println("Adresse courriel invalide");
            String response = scanner.nextLine();
            if (Objects.equals(response, "1")) {
                return false;
            } else {
                loginPage();
            }
        }
        return isLoggedIn;
    }

}
