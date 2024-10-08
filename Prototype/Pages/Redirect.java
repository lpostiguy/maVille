package Prototype.Pages;

import Prototype.User.User;

import java.util.Objects;

import static Prototype.Pages.AccountPage.AccountPageMenu;
import static Prototype.Pages.MainMenuPage.mainMenuLoggedIntervenant;
import static Prototype.Pages.MainMenuPage.mainMenuLoggedResident;

public class Redirect {

    public static void redirect() {
        String redirect = MainMenuPage.mainMenu();
        switch (redirect) {
            case "Connection" -> {
                boolean successfullLogin;
                successfullLogin = LoginPage.loginPage();
                if (successfullLogin) {
                    // Access the loggedIn menu
                    // TODO: Check if the user is a Intervenant or a resident
                    redirect = mainMenuLoggedResident();
                    switch (redirect) {
                        case "Rechercher des travaux" -> {
                            // TODO: Send user to Rechercher des travaux
                        }
                        case "Soumettre une requête de travail" -> {
                            // TODO: Send user to Soumettre une requête de 
                            //  travail
                        }
                        case "Signaler un problème à la ville" -> {
                            // TODO: Send user to Signaler un problème à la 
                            //  ville
                        }
                        case "Modifier ses informations de compte" -> {
                            System.out.println("Attention: Pour le moment " +
                                    "il" + " est " + "seulement possible " +
                                    "d'accéder à cette " + "page en passant " +
                                    "par la création de " + "compte.");
                            // TODO: Access the user info from the data base
                        }
                    }
                } else {
                    redirect();
                }
            }
            case "Inscription" -> {
                User user = new User();
                user = InscriptionPage.inscriptionPage();
                if (user != null) {
                    // Access the loggedIn menu
                    // TODO: Check if the user is a Intervenant or a resident
                    redirect = mainMenuLoggedResident();
                    switch (redirect) {
                        case "Rechercher des travaux" -> {
                            // TODO: Send user to Rechercher des travaux
                        }
                        case "Soumettre une requête de travail" -> {
                            // TODO: Send user to Soumettre une requête de 
                            //  travail
                        }
                        case "Signaler un problème à la ville" -> {
                            // TODO: Send user to Signaler un problème à la 
                            //  ville
                        }
                        case "Modifier ses informations de compte" -> {
                            // TODO: Fix this menu handling, because if
                            //  return from accountPage 2 times the program
                            //  ends.
                            boolean response = AccountPageMenu(user);
                            if(!response) {
                                if(Objects.equals(user.getRole(), "RESIDENT")) {
                                    mainMenuLoggedResident();
                                }
                                else {
                                    mainMenuLoggedIntervenant();
                                }
                            }
                        }
                    }
                } else {
                    redirect();
                }
            }
        }
    }

}
