package com.app.pages;

import com.app.models.Notification;
import com.app.models.User.User;
import org.bson.Document;


import java.util.List;
import java.util.stream.Collectors;

import static com.app.controllers.NotificationsController.consulterNotificationsNonLues;
import static com.app.controllers.NotificationsController.mettreAJourStatutNotification;

public class NotificationPage {

    public static void consulterNotifications(User user) {

        List<Document> notificationsNonLues = consulterNotificationsNonLues(user);

        if (!notificationsNonLues.isEmpty()) {
            // Convertir les `Document` en `Notification`
            List<Notification> notifications = notificationsNonLues.stream().map(Notification::fromDocument).collect(Collectors.toList());

            int i = 0;
            for(Notification notification : notifications) {
                i++;
                System.out.println("Message notification " + i + " : " + notification.getMsg());
            }

            mettreAJourStatutNotification(true, user, notifications);
        }
    }
}
