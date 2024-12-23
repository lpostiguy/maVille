package com.app.pages;


import java.util.Objects;

import com.app.models.User.Resident;
import com.app.models.User.User;

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
                if (Objects.equals(user.getUserRole(), "RESIDENT")) {
                    while (!quitLoggedMenu) {
                        redirect = mainMenuLoggedResident((Resident) user);
                        switch (redirect) {
                            case "Consulter ou rechercher des travaux" -> {
                                ConsulterTravauxPage.consulterTravauxMenu();
                            }
                            case "Soumettre une requête de travail" -> {
                                SoumettreRequeteTravailPage.soumettreRequeteTravailMenu(user);
                            }
                            case "Faire le suivi d'une requête de travail" -> {
                                ConsulterRequetesTravailPage.suiviRequeteTravailMenu(user);
                            }
                            case "Consulter les entraves routières" -> {
                                ConsulterEntravesPage.consulterEntraveMenu();
                            }
                            case "Notifications" -> {
                                ConsulterNotificationPage.consulterNotifications((Resident) user);
                            }
                            case "Consulter son profil" -> {
                                AccountPage.accountPageMenu(user);
                            }
                            case "Se Déconnecter" -> {
                                quitLoggedMenu = true;
                                System.out.println("Vous êtes déconnecté");
                            }
                        }
                    }
                } else if (Objects.equals(user.getUserRole(), "INTERVENANT")) {
                    while (!quitLoggedMenu) {
                        redirect = mainMenuLoggedIntervenant();
                        switch (redirect) {
                            case "Consulter la liste des requêtes de " +
                                "travail" -> {
                                consulterRequeteTravailMenu(user);
                            }
                            case "Soumettre ou consulter des projets" -> {
                                ProjetPage.projetMenu(user);
                            }
                            case "Consulter son profil" -> {
                                AccountPage.accountPageMenu(user);
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
