package com.app.pages;

import com.app.models.Notification;
import com.app.models.User.Resident;
import org.bson.Document;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.app.controllers.NotificationsController.*;

public class ConsulterNotificationPage {

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
            System.out.println("Message notification " + (i + 1) + " : " + notifications.get(i).getMsg());
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
