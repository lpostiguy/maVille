package Prototype.Pages;

import Prototype.Auth.GlobalUserInfo;
import Prototype.User.User;

import java.util.Objects;

import static Prototype.Pages.MainMenuPage.mainMenuLoggedIntervenant;
import static Prototype.Pages.MainMenuPage.mainMenuLoggedResident;

public class Redirect {

    public static void redirect() {
        while (true) {
            boolean successfulLogin = false;
            boolean inscription = false;
            boolean quitLoggedMenu = false;
            User user = new User();
            String redirect = MainMenuPage.mainMenu();
            switch (redirect) {
                case "Connection" -> {
                    successfulLogin = LoginPage.loginPage();
                }
                case "Inscription" -> {
                    user = InscriptionPage.inscriptionPage();
                    if (user != null) {
                        inscription = true;
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
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Rechercher des travaux
                            }
                            case "Soumettre une requête de travail" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Soumettre " +
                                        "une requête " + "de travail");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Soumettre une requête de
                                //  travail
                            }
                            case "Signaler un problème à la ville" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Signaler un" +
                                        " problème à " + "la ville");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Signaler un problème à la
                                //  ville
                            }
                            case "Consulter les travaux en cours ou à venir" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Consulter les travaux en cours ou à venir");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Signaler un problème à la
                                //  ville
                            }
                            case "Notifications" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Notifications");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Signaler un problème à la
                                //  ville
                            }
                            case "Consulter son profil" -> {
                                if (inscription) {
                                    AccountPage.AccountPageMenu(user);
                                } else {
                                    System.out.println("Attention: Pour le " + "moment " + "il" + " est " + "seulement possible " + "d'accéder " + "à" + " cette " + "page en passant " + "par la création de " + "compte.");
                                }
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
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Consulter " +
                                        "la liste des " + "requ" + "êtes de " +
                                        "travail.");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Consulter la liste des
                                //  requêtes de travail
                            }
                            case "Soumettre un nouveau projet de travaux" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Soumettre " +
                                        "un nouveau " + "projet de" + " " +
                                        "travaux.");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Soumettre un nouveau
                                //  projet de travaux
                            }
                            case "Mettre à jour les informations d'un " +
                                    "chantier" -> {
                                System.out.println("Vous êtes maintenant " +
                                        "sur" + " la" + " page : Mettre à " +
                                        "jour les " + "informations" + " " +
                                        "d'un chantier.");
                                System.out.println("Pour l'instant, " +
                                        "cette " + "page " + "est statique " +
                                        "et il n'y a" + " pas encore de" +
                                        " logique.");
                                // TODO: Send user to Mettre à jour les
                                //  informations d'un chantier
                            }
                            case "Consulter son profil" -> {
                                if (inscription) {
                                    AccountPage.AccountPageMenu(user);
                                } else {
                                    System.out.println("Attention: Pour le " + "moment " + "il" + " est " + "seulement possible " + "d'accéder " + "à" + " cette " + "page en passant " + "par la création de " + "compte.");
                                }
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