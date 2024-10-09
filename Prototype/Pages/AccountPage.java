package Prototype.Pages;

import Prototype.User.Intervenant;
import Prototype.User.Resident;
import Prototype.User.User;

import java.util.Objects;
import java.util.Scanner;

public class AccountPage {

    static Scanner scanner = new Scanner(System.in);

    public static boolean AccountPageMenu(User user) {
        System.out.println("Entrez un numéro pour être redirigé vers ces " + "pages:");
        System.out.println("[1] Retour");
        System.out.println("[2] Voir toutes mes données");
        System.out.println("[3] Modifier mes données");
        String responseMenu = scanner.nextLine();
        switch (responseMenu) {
            case "1" -> {
                return false;
            }
            case "2" -> {
                System.out.println("Voici toutes les données associées " +
                        "avec votre compte:");
                System.out.println("Prénom: " + user.getFirstName());
                System.out.println("Nom: " + user.getLastName());
                System.out.println("Adresse courriel: " + user.getEmail());
                // Information specific to resident role
                if (Objects.equals(user.getRole(), "RESIDENT")) {
                    residentPrintInfo((Resident) user);
                }
                // Information specific to intervenant role
                else {
                    intervenantPrintInfo((Intervenant) user);
                }
                System.out.println("[1] Retour");
                String responseDisplayUserInfo = scanner.nextLine();
                if (Objects.equals(responseDisplayUserInfo, "1")) {
                    return false;
                }

            }
            case "3" -> {
                // TODO: Ajouter un fonction qui permet de modifier toutes
                //  les données de l'utilisateur.
                System.out.println("Il n'est pas encore possible de modifier" +
                        " les informations de l'utilisateur.");
                System.out.println("[1] Retour");
                String responseModifyUserInfo = scanner.nextLine();
                if (Objects.equals(responseModifyUserInfo, "1")) {
                    return false;
                }
            }
            default -> {
                return AccountPageMenu(user);
            }
        }
        return false;
    }

    public static void residentPrintInfo(Resident resident) {
        System.out.println("Adresse de résidence: " + resident.getHomeAddress());
        if (!Objects.equals(resident.getPhoneNumber(), "")) {
            System.out.println("Numéro de téléphone: " + resident.getPhoneNumber());
        }
        System.out.println("Date de naissance: " + resident.getDateOfBirth());
    }

    public static void intervenantPrintInfo(Intervenant intervenant) {
        System.out.println("Type d'entité: " + intervenant.getEntityType());
        System.out.println("Identifiant de la ville: " + intervenant.getCityId());
    }
}
