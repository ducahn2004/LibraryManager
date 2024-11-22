package org.group4.service.notification;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import org.group4.dao.base.FactoryDAO;
import org.group4.model.enums.NotificationType;
import org.group4.model.notification.EmailNotification;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailNotificationService {

  private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
  private static final EmailNotificationService instance = new EmailNotificationService();

  /** The singleton instance of the {@code EmailNotificationService}. */
  private EmailNotificationService() {}

  /**
   * Returns the singleton instance of the {@code EmailNotificationService}.
   *
   * @return The singleton instance of the {@code EmailNotificationService}.
   */
  public static EmailNotificationService getInstance() {
    return instance;
  }

  /**
   * Sends an email notification.
   *
   * @param type    The type of notification.
   * @param email   The recipient's email address.
   * @param details Additional details for the notification.
   */
  public synchronized void sendNotification(NotificationType type, String email, String details) {
    try {
      EmailNotification emailNotification = new EmailNotification(type, email);
      String content = generateContent(type, details);

      String subject = "Library Notification: " + type;
      emailNotification.setContent(content);
      FactoryDAO.getEmailNotificationDAO().add(emailNotification);
      sendEmail("me", "me", email, subject, content);
    } catch (Exception e) {
      logger.error("Error sending email notification: {}", e.getMessage());
    }
  }

  /**
   * Generates email content based on notification type and details.
   *
   * @param type    The type of notification.
   * @param details Additional details for the email.
   * @return The generated email content.
   */
  private String generateContent(NotificationType type, String details) {
    return switch (type) {
      case FINE_TRANSACTION -> "You have a pending fine transaction. \n" + details;
      case OVERDUE_NOTIFICATION -> "You have overdue books to return. \n" + details;
      case DUE_DATE_REMINDER -> "You have books due soon. \n" + details;
      case FORGOT_PASSWORD -> "You have requested a password reset. \n" + details;
      case BOOK_BORROW_SUCCESS -> "You have successfully borrowed a book. \n" + details;
      case BOOK_RETURN_SUCCESS -> "You have successfully returned a book. \n" + details;
      default -> "This is an email notification.";
    };
  }

  /**
   * Retrieves OAuth2 credentials required for accessing the Gmail API.
   *
   * @param HTTP_TRANSPORT The HTTP transport used for API communication.
   * @return The OAuth2 {@code Credential} object.
   * @throws IOException If an error occurs while retrieving or loading credentials.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    InputStream in = EmailNotification.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }

    // Load client secrets
    GoogleClientSecrets clientSecrets = GoogleClientSecrets
        .load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();

    // Authorize the user
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  /**
   * Sends an email message using the Gmail API.
   *
   * @param userId The user ID (email address of the sender).
   * @param from   The sender's email address.
   * @param to     The recipient's email address.
   * @param subject The subject line of the email.
   * @param bodyText The body content of the email.
   * @throws Exception If an error occurs while sending the email.
   */
  public static void sendEmail(String userId, String from, String to, String subject,
      String bodyText) throws Exception {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    // Build Gmail service
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();

    // Create email
    MimeMessage email = createEmail(to, from, subject, bodyText);
    sendMessage(service, userId, email);
  }

  /**
   * Creates a {@code MimeMessage} object with the provided details.
   *
   * @param to       The recipient's email address.
   * @param from     The sender's email address.
   * @param subject  The subject line of the email.
   * @param bodyText The body content of the email.
   * @return A {@code MimeMessage} representing the email.
   * @throws MessagingException If an error occurs while creating the message.
   */
  private static MimeMessage createEmail(String to, String from, String subject, String bodyText)
      throws MessagingException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    // Create email
    MimeMessage email = new MimeMessage(session);
    email.setFrom(new InternetAddress(from));
    email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
    email.setSubject(subject);
    email.setText(bodyText);
    return email;
  }

  /**
   * Sends the specified email message through the Gmail API.
   *
   * @param service The Gmail service instance.
   * @param userId  The user ID (email address of the sender).
   * @param email   The {@code MimeMessage} to send.
   * @throws MessagingException If an error occurs while sending the message.
   * @throws IOException        If an I/O error occurs.
   */
  private static void sendMessage(Gmail service, String userId, MimeMessage email)
      throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    String encodedEmail = Base64.getUrlEncoder().encodeToString(rawMessageBytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    service.users().messages().send(userId, message).execute();
  }
}
