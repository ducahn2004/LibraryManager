package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;

import org.group4.base.notifications.Notification;

public class NotificationDatabase {
    private final static List<Notification> notifications = new ArrayList<>();

    public static List<Notification> getNotifications() {
        return notifications;
    }
}
