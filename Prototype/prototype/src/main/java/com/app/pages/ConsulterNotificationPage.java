package com.app.pages;

import com.app.models.Notification;
import com.app.models.User.Resident;
import org.bson.Document;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.app.controllers.NotificationsController.*;

/**
 * Cette classe gère l'affichage et la gestion des notifications pour un
 * résident. Elle permet de consulter les notifications non lues, d'afficher
 * toutes les notifications, et de mettre à jour leur statut.
 */
public class ConsulterNotificationPage {

    /**
     * Permet à un résident de consulter ses notifications.
     * Affiche les notifications non lues en priorité, puis toutes les
     * notifications si aucune n'est non lue. Les notifications non lues sont
     * marquées comme lues après consultation.
     *
     * @param resident l'utilisateur de type résident pour lequel les
     * notifications sont affichées.
     */
    public static void consulterNotifications(Resident resident) {
        try {
            List<Document> notificationsNonLues = consulterNotificationsNonLues(resident);

            if (!notificationsNonLues.isEmpty()) {
                afficherNotifications("Notifications non lues :", notificationsNonLues);
                mettreAJourStatutNotification(true, resident, convertirEnNotifications(notificationsNonLues));
            } else {
                List<Document> toutesLesNotifications = consulterToutesLesNotifications(resident);
                afficherNotifications("\nToutes les notifications :",
                    toutesLesNotifications);
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n[1] Retour au menu principal");
            while (!scanner.nextLine().equals("1")) {
                System.out.println("[1] Retour au menu principal");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la consultation des notifications : " + e.getMessage());
        }
    }

    private static void afficherNotifications(String titre, List<Document> documents) {
        List<Notification> notifications = convertirEnNotifications(documents);
        if(!notifications.isEmpty()) {
        System.out.println(titre);
        for (int i = 0; i < notifications.size(); i++) {
            System.out.println("\nMessage notification " + (i + 1) + " : \n" + notifications.get(i).getMsg());
        }
        }
        else {
            System.out.println("Vous n'avez pas encore reçu de notifications");
        }
    }

    private static List<Notification> convertirEnNotifications(List<Document> documents) {
        return documents.stream().map(Notification::fromDocument).collect(Collectors.toList());
    }
}
