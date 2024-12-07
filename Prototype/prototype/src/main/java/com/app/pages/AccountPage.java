package com.app.pages;

import com.app.models.User.User;
import com.app.models.User.Resident;
import com.app.models.User.Intervenant;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AccountPage {

    static Scanner scanner = new Scanner(System.in);

    public static boolean accountPageMenu(User user) {
        System.out.println("\nEntrez un numéro pour être redirigé vers ces " +
            "pages:");
        System.out.println("-------- Menu Info Compte --------");
        System.out.println("[1] Retour au menu principal");
        System.out.println("[2] Voir toutes mes données");
        if (Objects.equals(user.getUserRole(), "RESIDENT")) {
            System.out.println("[3] Voir mes préférence d'horaire");
        }
        System.out.println("-----------------------------------");
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
                if (Objects.equals(user.getUserRole(), "RESIDENT")) {
                    residentPrintInfo((Resident) user);
                }
                // Information specific to intervenant role
                else {
                    intervenantPrintInfo((Intervenant) user);
                }
                System.out.println("\n[1] Retour au menu principal");
                String responseDisplayUserInfo = scanner.nextLine();
                if (Objects.equals(responseDisplayUserInfo, "1")) {
                    return false;
                }

            }
                case "3" -> {
                    if (user instanceof Resident resident) {
                    System.out.println("Voici vos préférence horaire:");
                        System.out.println("-----------------------------------");
                        List<String> preferencesHoraires = resident.getPreferencesHoraires();
                    if (preferencesHoraires != null && !preferencesHoraires.isEmpty()) {
                        for (String préférence : preferencesHoraires) {
                            System.out.println(préférence);
                        }
                        System.out.println("-----------------------------------");
                        System.out.println("\n[1] Retour");
                        System.out.println("[2] Modifier mes préférence d'horaire");
                        String responseModifyUserInfo = scanner.nextLine();
                        if (Objects.equals(responseModifyUserInfo, "1")) {
                            return false;
                        }
                        else if(Objects.equals(responseModifyUserInfo, "2")) {
                            // TODO: Ajouter un fonction qui permet de modifier ses préférences d'horaires
                        }
                    }
                    else {
                        System.out.println("Aucune préférence horaire.");
                        System.out.println("-----------------------------------");
                        System.out.println("\n[1] Retour");
                        System.out.println("[2] Ajouter des préférences d'horaires");
                        String responseModifyUserInfo = scanner.nextLine();
                        if (Objects.equals(responseModifyUserInfo, "1")) {
                            return false;
                        }
                        else if(Objects.equals(responseModifyUserInfo, "2")) {
                            // TODO: Ajouter un fonction qui permet d'ajouter des préférences d'horaires
                        }
                    }
                }
                    else {
                        return accountPageMenu(user);
                    }
            }
            default -> {
                return accountPageMenu(user);
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
