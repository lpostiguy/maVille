package com.app.pages;


import java.util.Objects;

import com.app.models.User.Resident;
import com.app.models.User.User;

import static com.app.pages.ConsulterRequetesTravailPage.consulterRequeteTravailMenu;
import static com.app.pages.MainMenuPage.mainMenuLoggedIntervenant;
import static com.app.pages.MainMenuPage.mainMenuLoggedResident;

/**
 * Cette classe est responsable de la gestion de la redirection entre les pages
 * en fonction du rôle de l'utilisateur et de son état de connexion.
 * Elle gère l'affichage des menus de connexion, d'inscription, ainsi que des
 * menus après connexion pour les utilisateurs de type "RESIDENT" ou
 * "INTERVENANT".
 */
public class PageRedirect {

    /**
     * Méthode principale qui gère la redirection vers les différentes pages de
     * l'application.
     * Elle permet à l'utilisateur de se connecter ou de s'inscrire, puis
     * redirige vers un menu spécifique en fonction du rôle de l'utilisateur
     * ("RESIDENT" ou "INTERVENANT").
     * Après la connexion, selon le rôle de l'utilisateur, un menu différent
     * sera affiché avec des options de navigation adaptées.
     */
    public static void pageRedirect() {
        while (true) {
            boolean successfulLogin = false;
            boolean quitLoggedMenu = false;
            User user = new User();
            String redirect = MainMenuPage.mainMenu();
            switch (redirect) {
                case "Connexion" -> {
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
                                ProjetPage.soumettreProjetMenu(user);
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
