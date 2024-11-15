package com.app.pages;

import com.app.models.User.Resident;
import com.app.models.User.Intervenant;
import com.app.models.User.User;
import com.app.utils.GlobalUserInfo;

import java.util.Objects;
import java.util.Scanner;

import static com.app.pages.InscriptionPage.passwordEncryption;

public class LoginPage {

    static Scanner scanner = new Scanner(System.in);
    static boolean isLoggedIn = false;

    public static User loginPage() {
        System.out.println("[1] Retour");
        System.out.println("Entrez votre adresse courriel:");
        String email = scanner.nextLine();

        while (!isLoggedIn) {
            if (Objects.equals(email, "1")) {
                return null;
            } else if (email.contains("@")) {
                System.out.println("Entrez votre mot de passe:");
                String password = scanner.nextLine();
                // TODO: Ajouter la logique qui compare les données rentrées par
                //  le user et les données présentes dans une base de donnée
                //  Mongo DB.
                if (email.equals(GlobalUserInfo.emailRes) && Objects.equals(password, GlobalUserInfo.passwordRes)) {
                    GlobalUserInfo.setCurrentRole("RESIDENT");
                    System.out.println("Connection réussie");
                    return new Resident(GlobalUserInfo.firstNameRes,
                        GlobalUserInfo.lastNameRes, GlobalUserInfo.emailRes,
                        GlobalUserInfo.phoneNumberRes,
                        GlobalUserInfo.dateOfBirthRes,
                        GlobalUserInfo.homeAddressRes,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordRes));
                } else if (email.equals(GlobalUserInfo.emailInt) && Objects.equals(password, GlobalUserInfo.passwordInt)) {
                    GlobalUserInfo.setCurrentRole("INTERVENANT");
                    System.out.println("Connection réussie");
                    return new Intervenant(GlobalUserInfo.firstNameInt,
                        GlobalUserInfo.lastNameInt, GlobalUserInfo.emailInt,
                        GlobalUserInfo.entityTypeInt,
                        GlobalUserInfo.cityIdInt,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordInt));
                } else {
                    System.out.println("[1] Retour");
                    System.out.println("[2] Réessayer");
                    System.out.println("Mauvais mot de passe ou mauvaise " +
                        "adresse" + " courriel");
                    String response = scanner.nextLine();
                    if (Objects.equals(response, "1")) {
                        return null;
                    }
                }
            } else {
                System.out.println("[1] Retour");
                System.out.println("[2] Réessayer");
                System.out.println("Adresse courriel invalide");
                String response = scanner.nextLine();
                if (Objects.equals(response, "1")) {
                    return null;
                }
            }
        }
        return null;
    }

}
