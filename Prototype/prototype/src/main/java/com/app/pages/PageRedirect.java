package com.app.pages;


import java.util.Objects;

import com.app.models.User.User;
import com.app.utils.GlobalUserInfo;

import static com.app.pages.ConsulterRequetesTravailPage.consulterRequeteTravailMenu;
import static com.app.pages.MainMenuPage.mainMenuLoggedIntervenant;
import static com.app.pages.MainMenuPage.mainMenuLoggedResident;


public class PageRedirect {

    public static void pageRedirect() {
        while (true) {
            boolean successfulLogin = false;
            boolean quitLoggedMenu = false;
            User user = new User();
            String redirect = MainMenuPage.mainMenu();
            switch (redirect) {
                case "Connection" -> {
                    user = LoginPage.loginPage();
                    if (user != null) {
                        successfulLogin = true;
                    }
                }
                case "Inscription" -> {
                    user = InscriptionPage.inscriptionPage();
                    if (user != null) {
                        successfulLogin = true;
                    }
                }
            }
            if (successfulLogin) {
                // Access the loggedIn menu
                // TODO: Check if the user is a Intervenant or a resident
                if (Objects.equals(GlobalUserInfo.currentRole, "RESIDENT") || Objects.equals(user.getRole(), "RESIDENT")) {
                    while (!quitLoggedMenu) {
                        redirect = mainMenuLoggedResident();
                        switch (redirect) {
                            case "Rechercher des travaux" -> {
                                System.out.println("Vous êtes maintenant " +
                                    "sur" + " la" + " page : Rechercher " +
                                    "des travaux");
                                System.out.println("Pour l'instant, " +
                                    "cette " + "page " + "est statique " +
                                    "et il n'y a" + " pas encore de" + " " +
                                    "logique.");
                                // TODO: Send user to Rechercher des travaux
                            }
                            case "Soumettre une requête de travail" -> {
                                SoumettreRequeteTravailPage.soumettreRequeteTravailMenu(user);
                            }
                            case "Signaler un problème à la ville" -> {
                                System.out.println("Vous êtes maintenant " +
                                    "sur" + " la" + " page : Signaler un" +
                                    " problème à " + "la ville");
                                System.out.println("Pour l'instant, " +
                                    "cette " + "page " + "est statique " +
                                    "et il n'y a" + " pas encore de" + " " +
                                    "logique.");
                                // TODO: Send user to Signaler un problème à la
                                //  ville
                            }
                            case "Consulter les travaux en cours ou à venir" -> {
                                ConsulterTravauxPage.consulterTravauxEnCoursMenu();
                            }
                            case "Consulter les entraves routières" -> {
                                ConsulterEntravesPage.consulterEntraveMenu();
                            }
                            case "Notifications" -> {
                                System.out.println("Vous êtes maintenant " +
                                    "sur" + " la" + " page : Notifications");
                                System.out.println("Pour l'instant, " +
                                    "cette " + "page " + "est statique " +
                                    "et il n'y a" + " pas encore de" + " " +
                                    "logique.");
                                // TODO: Send user to Signaler un problème à la
                                //  ville
                            }
                            case "Consulter son profil" -> {
                                AccountPage.accountPageMenu(user);
                                // TODO: Access the user info from the data
                                //  base
                            }
                            case "Se Déconnecter" -> {
                                quitLoggedMenu = true;
                                System.out.println("Vous êtes déconnecté");
                            }
                        }
                    }
                } else if (Objects.equals(GlobalUserInfo.currentRole,
                    "INTERVENANT") || Objects.equals(user.getRole(),
                    "INTERVENANT")) {
                    while (!quitLoggedMenu) {
                        redirect = mainMenuLoggedIntervenant();
                        switch (redirect) {
                            case "Consulter la liste des requêtes de " +
                                "travail" -> {
                                consulterRequeteTravailMenu();
                            }
                            case "Soumettre un nouveau projet de travaux" -> {
                                System.out.println("Vous êtes maintenant " +
                                    "sur" + " la" + " page : Soumettre " +
                                    "un nouveau " + "projet de" + " " +
                                    "travaux.");
                                System.out.println("Pour l'instant, " +
                                    "cette " + "page " + "est statique " +
                                    "et il n'y a" + " pas encore de" + " " +
                                    "logique.");
                                // TODO: Send user to Soumettre un nouveau
                                //  projet de travaux
                            }
                            case "Mettre à jour les informations d'un " +
                                "chantier" -> {
                                System.out.println("Vous êtes maintenant " +
                                    "sur" + " la" + " page : Mettre à " +
                                    "jour les " + "informations" + " " + "d" + "'un chantier.");
                                System.out.println("Pour l'instant, " +
                                    "cette " + "page " + "est statique " +
                                    "et il n'y a" + " pas encore de" + " " +
                                    "logique.");
                                // TODO: Send user to Mettre à jour les
                                //  informations d'un chantier
                            }
                            case "Consulter son profil" -> {
                                AccountPage.accountPageMenu(user);
                                // TODO: Access the user info from the data
                                //  base
                            }
                            case "Se Déconnecter" -> {
                                quitLoggedMenu = true;
                                System.out.println("Vous êtes déconnecté");
                            }
                        }
                    }
                }
            }
        }
    }
}
