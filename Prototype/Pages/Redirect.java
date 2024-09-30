package Prototype.Pages;

import Prototype.Auth.Connection;

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
                    redirect = MainMenuPage.mainMenuLoggedResident();
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
                    }
                } else {
                    redirect();
                }
            }
            case "Inscription" -> {
                boolean successfullInscription;
                successfullInscription = InscriptionPage.inscriptionPage();
                if (successfullInscription) {
                    // Access the loggedIn menu
                    // TODO: Check if the user is a Intervenant or a resident
                    redirect = MainMenuPage.mainMenuLoggedResident();
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
                    }
                } else {
                    redirect();
                }
            }
        }
    }

}
