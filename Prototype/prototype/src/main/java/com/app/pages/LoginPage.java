package com.app.pages;

import com.app.models.User.Resident;
import com.app.models.User.Intervenant;
import com.app.models.User.User;
import com.app.utils.GlobalUserInfo;
import org.bson.Document;

import static com.app.controllers.UserController.findUserByEmail;

import java.util.Objects;
import java.util.Scanner;

import static com.app.pages.InscriptionPage.homeAddress;
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
            } else {
                Document userInfo = findUserByEmail(email);

                if (userInfo != null) {
                    System.out.println("Entrez votre mot de passe:");
                    String password = scanner.nextLine();
                    int encryptedPassword = passwordEncryption.encrypt(password);

                    Integer storedPassword = userInfo.getInteger("password");
                    if (Objects.equals(encryptedPassword, storedPassword)) {
                        String role = userInfo.getString("userRole");
                        GlobalUserInfo.setCurrentRole(role);
                        GlobalUserInfo.setCurrentUserId(userInfo.getString("userId"));
                        System.out.println("Connexion réussie!");

                        if (Objects.equals(role, "RESIDENT")) {
                            return new Resident(userInfo.getString("firstName"),
                                userInfo.getString("lastName"), userInfo.getString("email"),
                                userInfo.getString("phoneNumber"), userInfo.getString("dateOfBirth"),
                                userInfo.getString("homeAddress"), userInfo.getInteger("password"),
                                userInfo.getString("userId"));
                        }
                        else {
                            return new Intervenant(userInfo.getString("firstName"),
                                userInfo.getString("lastName"), userInfo.getString("email"),
                                userInfo.getString("entityType"), userInfo.getString("cityId"),
                                userInfo.getInteger("password"),
                                userInfo.getString("userId"));
                        }
                    }
                    else {
                        System.out.println("Mauvais mot de passe ou mauvaise " +
                            "adresse" + " courriel");
                    }
                }

                else {
                    System.out.println("Cette adresse courriel n'est pas " +
                        "associée à un compte.");
                    }
                }
        }
        return null;
    }

}
