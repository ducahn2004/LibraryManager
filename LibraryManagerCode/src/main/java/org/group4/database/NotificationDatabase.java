package org.group4.database;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.group4.base.notifications.Notification;
import org.group4.base.users.Member;

public class NotificationDatabase {
    private final static Map<Member, List<Notification>> notifications = new HashMap<>();

    public static void addNotification(Member member, Notification notification) {
        notifications.computeIfAbsent(member, k -> new ArrayList<>()).add(notification);
    }

    public static List<Notification> getNotifications(Member member) {
        return notifications.getOrDefault(member, new ArrayList<>());
    }
}