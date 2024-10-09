package Prototype.Pages;

import Prototype.Auth.GlobalUserInfo;

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
            if (email.equals(GlobalUserInfo.emailRes) && Objects.equals(password, GlobalUserInfo.passwordRes)) {
                GlobalUserInfo.setCurrentRole("RESIDENT");
                System.out.println("Connection réussie");
                isLoggedIn = true;
                return true;
            } else if (email.equals(GlobalUserInfo.emailInt) && Objects.equals(password, GlobalUserInfo.passwordInt)) {
                GlobalUserInfo.setCurrentRole("INTERVENANT");
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
