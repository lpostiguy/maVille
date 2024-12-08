package com.app.pages;

import com.app.controllers.PreferenceHoraireController;
import com.app.models.User.User;
import com.app.models.User.Resident;
import com.app.models.User.Intervenant;
import com.app.utils.RegexChecker;
import org.bson.Document;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AccountPage {

    static Scanner scanner = new Scanner(System.in);

    public static boolean accountPageMenu(User user) {
        System.out.println("\nEntrez un numéro pour être redirigé vers ces pages:");
        System.out.println("-------- Menu Info Compte --------");
        System.out.println("[1] Retour au menu principal");
        System.out.println("[2] Voir toutes mes données");
        if (Objects.equals(user.getUserRole(), "RESIDENT")) {
            System.out.println("[3] Voir mes préférences horaires");
        }
        System.out.println("-----------------------------------");
        String responseMenu = scanner.nextLine();
        switch (responseMenu) {
            case "1" -> {
                return false;
            }
            case "2" -> {
                System.out.println("Voici toutes les données associées avec votre compte:");
                System.out.println("Prénom: " + user.getFirstName());
                System.out.println("Nom: " + user.getLastName());
                System.out.println("Adresse courriel: " + user.getEmail());
                // Information spécifique au rôle RESIDENT
                if (Objects.equals(user.getUserRole(), "RESIDENT")) {
                    residentPrintInfo((Resident) user);
                }
                // Information spécifique au rôle INTERVENANT
                else {
                    intervenantPrintInfo((Intervenant) user);
                }
                System.out.println("\n[1] Retour");
                while (!scanner.nextLine().equals("1")) {
                    System.out.println("[1] Retour");
                }
                accountPageMenu(user);
            }
            case "3" -> {
                if (user instanceof Resident resident) {
                    gérerPreferencesHoraires(resident.getUserId());
                } else {
                    return accountPageMenu(user);
                }
            }
            default -> {
                return accountPageMenu(user);
            }
        }
        return false;
    }

    private static void gérerPreferencesHoraires(String userId) {
        Boolean isValidInput = false;

        while (true) {
            System.out.println("\n-------- Mes préférences horaires --------");
            // Appeler le contrôleur pour récupérer les préférences horaires
            List<Document> preferencesHoraires = PreferenceHoraireController.recupererPreferencesHoraires(userId);

            if (preferencesHoraires.isEmpty()) {
                System.out.println("Aucune préférence horaire trouvée.");
            } else {
                System.out.println("Voici vos préférences horaires :");
                for (Document preference : preferencesHoraires) {
                    System.out.println("- " + preference.getString("jour") + " : " +
                        preference.getString("heureDebut") + " - " +
                        preference.getString("heureFin"));
                }
            }

            System.out.println("\n[1] Retour au menu principal");
            System.out.println("[2] Ajouter ou modifier une préférence horaire");
            System.out.println("[3] Supprimer une préférence horaire");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> {
                    return;
                }
                case "2" -> {
                    isValidInput = false;
                    String jour = "";
                    while (!isValidInput) {
                        System.out.print("Entrez le jour (ex: Lundi) : ");
                        jour = scanner.nextLine().trim();
                        if (!RegexChecker.estJourSemaineValide(jour)) {
                            System.out.println("Jour invalide. Veuillez entrer un jour valide (ex: Lundi).");
                        }
                        else {
                            isValidInput = true;
                        }
                    }

                    String heureDebut = "";
                    isValidInput = false;
                    while (!isValidInput) {
                        System.out.print("Entrez l'heure de début (format HH:mm) : ");
                        heureDebut = scanner.nextLine().trim();
                        if (!RegexChecker.estFormatHeureValide(heureDebut)) {
                            System.out.println("Format de l'heure invalide. Veuillez entrer une heure au format HH:mm.");
                        }
                        else {
                            isValidInput = true;
                        }
                    }

                    String heureFin = "";
                    isValidInput = false;
                    while (!isValidInput) {
                        System.out.print("Entrez l'heure de fin (format HH:mm) : ");
                        heureFin = scanner.nextLine().trim();
                        if (!RegexChecker.estFormatHeureValide(heureFin)) {
                            System.out.println("Format de l'heure invalide. Veuillez entrer une heure au format HH:mm.");
                        } else {
                            isValidInput = true;
                        }
                    }

                    boolean success = PreferenceHoraireController.ajouterOuModifierPreferenceHoraire(userId, jour, heureDebut, heureFin);
                    if (success) {
                        System.out.println("Préférence horaire ajoutée ou modifiée avec succès.");
                    } else {
                        System.out.println("Erreur lors de la modification de la préférence horaire.");
                    }
                }
                case "3" -> {
                    String jour = "";
                    isValidInput = false;
                    while (!isValidInput) {
                        System.out.print("Entrez le jour à supprimer (ex: Lundi) : ");
                        jour = scanner.nextLine().trim();
                        if (!RegexChecker.estJourSemaineValide(jour)) {
                            System.out.println("Jour invalide. Veuillez entrer un jour valide (ex: Lundi).");
                        }
                        else {
                            isValidInput = true;
                        }
                    }

                    boolean success = PreferenceHoraireController.supprimerPreferenceHoraire(userId, jour);
                    if (success) {
                        System.out.println("Préférence horaire supprimée avec succès.");
                    } else {
                        System.out.println("Erreur lors de la suppression de la préférence horaire.");
                    }
                }
                default -> {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }
        }
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
