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
        while (!isLoggedIn) {
        System.out.println("[1] Retour");
        System.out.println("Entrez votre adresse courriel:");
        String email = scanner.nextLine();
            if (Objects.equals(email, "1")) {
                return null;
            } else if (email.contains("@")) {
                System.out.println("Entrez votre mot de passe:");
                String password = scanner.nextLine();
                // TODO: Ajouter la logique qui compare les données rentrées par
                //  le user et les données présentes dans une base de donnée
                //  Mongo DB.
                if (email.equals(GlobalUserInfo.emailRes1) && Objects.equals(password, GlobalUserInfo.passwordRes1)) {
                    GlobalUserInfo.setCurrentRole("RESIDENT");
                    System.out.println("Connection réussie");
                    return new Resident(GlobalUserInfo.firstNameRes1,
                        GlobalUserInfo.lastNameRes1, GlobalUserInfo.emailRes1
                        , GlobalUserInfo.phoneNumberRes1,
                        GlobalUserInfo.dateOfBirthRes1,
                        GlobalUserInfo.homeAddressRes1,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordRes1));
                } else if (email.equals(GlobalUserInfo.emailRes2) && Objects.equals(password, GlobalUserInfo.passwordRes2)) {
                    GlobalUserInfo.setCurrentRole("RESIDENT");
                    System.out.println("Connection réussie");
                    return new Resident(GlobalUserInfo.firstNameRes2,
                        GlobalUserInfo.lastNameRes2, GlobalUserInfo.emailRes2
                        , GlobalUserInfo.phoneNumberRes2,
                        GlobalUserInfo.dateOfBirthRes2,
                        GlobalUserInfo.homeAddressRes2,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordRes2));
                } else if (email.equals(GlobalUserInfo.emailRes3) && Objects.equals(password, GlobalUserInfo.passwordRes3)) {
                    GlobalUserInfo.setCurrentRole("RESIDENT");
                    System.out.println("Connection réussie");
                    return new Resident(GlobalUserInfo.firstNameRes3,
                        GlobalUserInfo.lastNameRes3, GlobalUserInfo.emailRes3
                        , GlobalUserInfo.phoneNumberRes3,
                        GlobalUserInfo.dateOfBirthRes3,
                        GlobalUserInfo.homeAddressRes3,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordRes3));
                } else if (email.equals(GlobalUserInfo.emailInt1) && Objects.equals(password, GlobalUserInfo.passwordInt1)) {
                    GlobalUserInfo.setCurrentRole("INTERVENANT");
                    System.out.println("Connection réussie");
                    return new Intervenant(GlobalUserInfo.firstNameInt1,
                        GlobalUserInfo.lastNameInt1, GlobalUserInfo.emailInt1
                        , GlobalUserInfo.entityTypeInt1,
                        GlobalUserInfo.cityIdInt1,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordInt1));
                } else if (email.equals(GlobalUserInfo.emailInt2) && Objects.equals(password, GlobalUserInfo.passwordInt2)) {
                    GlobalUserInfo.setCurrentRole("INTERVENANT");
                    System.out.println("Connection réussie");
                    return new Intervenant(GlobalUserInfo.firstNameInt2,
                        GlobalUserInfo.lastNameInt2, GlobalUserInfo.emailInt2
                        , GlobalUserInfo.entityTypeInt2,
                        GlobalUserInfo.cityIdInt2,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordInt2));
                } else if (email.equals(GlobalUserInfo.emailInt3) && Objects.equals(password, GlobalUserInfo.passwordInt3)) {
                    GlobalUserInfo.setCurrentRole("INTERVENANT");
                    System.out.println("Connection réussie");
                    return new Intervenant(GlobalUserInfo.firstNameInt3,
                        GlobalUserInfo.lastNameInt3, GlobalUserInfo.emailInt3
                        , GlobalUserInfo.entityTypeInt3,
                        GlobalUserInfo.cityIdInt3,
                        passwordEncryption.encrypt(GlobalUserInfo.passwordInt3));
                } else {
                    System.out.println("Mauvais mot de passe ou mauvaise " +
                        "adresse" + " courriel");
                }
            } else {
                System.out.println("Adresse courriel invalide");
            }
        }
        return null;
    }

}
