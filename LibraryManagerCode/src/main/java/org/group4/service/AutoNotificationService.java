package org.group4.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.group4.dao.BookItemDAO;
import org.group4.dao.FactoryDAO;

import org.group4.model.books.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.model.enums.NotificationType;
import org.group4.model.notifications.EmailNotification;
import org.group4.model.transactions.BookLending;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to handle automatic notifications for book lending.
 * This service periodically checks for books nearing due date or overdue
 * and sends appropriate email notifications to the borrowers.
 */
public class AutoNotificationService {

    /** Logger for the AutoNotificationService class. */
    private final Logger logger = LoggerFactory.getLogger(AutoNotificationService.class);

    /** Scheduler for running the notification service. */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /** Data Access Object for book items. */
    private final BookItemDAO bookItemDAO = new BookItemDAO();

    /**
     * Starts the notification service.
     * Schedules the task to run daily at fixed intervals.
     */
    public void start() {
        scheduler.scheduleAtFixedRate(this::sendNotifications, 0, 1, TimeUnit.DAYS);
    }

    /**
     * Main method to process notifications.
     * This method fetches loaned books, checks their lending status,
     * and sends notifications based on their due date.
     */
    private void sendNotifications() {
        try {
            LocalDate today = LocalDate.now();

            // Fetch all books currently loaned
            List<BookItem> bookItems = getLoanedBookItems();

            // Process each book and its active lendings
            for (BookItem bookItem : bookItems) {
                List<BookLending> activeLendings = getActiveBookLendings(bookItem);

                // Process notifications for each active lending
                for (BookLending bookLending : activeLendings) {
                    processNotification(bookLending, today);
                }
            }
        } catch (Exception e) {
            logger.error("Error sending due date or overdue notifications", e);
        }
    }

    /**
     * Retrieves all book items that are currently loaned.
     *
     * @return List of loaned book items.
     */
    private List<BookItem> getLoanedBookItems() {
        return bookItemDAO.getAll()
            .stream()
            .filter(bookItem -> bookItem.getStatus() == BookStatus.LOANED)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves active lendings for a given book item.
     * An active lending is defined as one that has no return date.
     *
     * @param bookItem The book item whose lendings are to be retrieved.
     * @return List of active book lendings for the given book item.
     */
    private List<BookLending> getActiveBookLendings(BookItem bookItem) {
        return FactoryDAO.getBookLendingDAO()
            .getByBarcode(bookItem.getBarcode())
            .stream()
            .filter(bookLending -> bookLending.getReturnDate().isEmpty())
            .collect(Collectors.toList());
    }

    /**
     * Processes and sends the appropriate notification for a book lending.
     * Sends a "Due Date Reminder" if the book is 2 days before its due date,
     * or an "Overdue Notification" if the book is past its due date.
     *
     * @param bookLending The book lending to process notifications for.
     * @param today The current date for comparison.
     * @throws Exception If an error occurs while sending the notification.
     */
    private void processNotification(BookLending bookLending, LocalDate today) throws Exception {
        LocalDate dueDate = bookLending.getDueDate();

        // Send due date reminder
        if (today.isEqual(dueDate.minusDays(2))) {
            EmailNotification.sendNotification(
                NotificationType.DUE_DATE_REMINDER,
                bookLending.getMember().getEmail(),
                bookLending.getBookItem().toString()
            );
        }
        // Send overdue notification
        else if (today.isAfter(dueDate)) {
            EmailNotification.sendNotification(
                NotificationType.OVERDUE_NOTIFICATION,
                bookLending.getMember().getEmail(),
                bookLending.getBookItem().toString()
            );
        }
    }

    /**
     * Shuts down the notification service.
     * Stops the scheduler and waits for any pending tasks to complete.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
