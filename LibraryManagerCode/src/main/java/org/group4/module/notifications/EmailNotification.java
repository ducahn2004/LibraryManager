package org.group4.module.notifications;

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

import java.time.LocalDate;
import org.group4.dao.FactoryDAO;
import org.group4.module.enums.NotificationType;

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

/**
 * Class to handle sending email notifications using the Gmail API.
 * <p>This class provides functionality to create and send email notifications through a
 * Gmail account using OAuth2 for authorization.</p>
 */
public class EmailNotification extends Notification {

  /** Application name for identifying API usage. */
  private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

  /** JSON factory used for parsing JSON data. */
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  /** Directory to store OAuth2 tokens for this application. */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /** Gmail API scope for sending emails. */
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);

  /** Path to the client credentials JSON file. */
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  /** Recipient's email address for sending the notification. */
  private final String email;

  /**
   * Constructs an {@code EmailNotification} instance with specified type, content, and recipient email.
   *
   * @param type    The type of notification (e.g., ALERT, REMINDER)
   * @param email   The email address to which the notification is sent
   */
  public EmailNotification(NotificationType type, String email) {
    super(type);
    this.email = email;
  }

  /**
   * Constructs an {@code EmailNotification} instance with specified ID, type, content, email,
   * and creation date.
   *
   * @param notificationId The unique ID for the email notification
   * @param type           The type of email notification
   * @param content        The message or content of the email notification
   * @param email          The recipient's email address
   * @param createdOn      The date when the email notification was created
   */
  public EmailNotification(String notificationId, NotificationType type, String content,
      String email, LocalDate createdOn) {
    super(notificationId, type, content, createdOn);
    this.email = email;
  }

  /**
   * Gets the recipient's email address for sending the notification.
   *
   * @return The recipient's email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sends an email notification to the specified email address.
   *
   * @param type    The type of notification to send
   * @param email   The recipient's email address
   * @param details Additional details to include in the notification
   * @throws Exception If an error occurs while sending the email
   */
  public static void sendNotification(NotificationType type, String email, String details)
      throws Exception {
    EmailNotification emailNotification = new EmailNotification(type, email);
    String content;

    switch (type) {
      case FINE_TRANSACTION -> content = "You have a pending fine transaction." + details;
      case OVERDUE_NOTIFICATION -> content = "You have overdue books to return." + details;
      case DUE_DATE_REMINDER -> content = "You have books due soon." + details;
      case FORGOT_PASSWORD -> content = "You have requested a password reset." + details;
      case BOOK_BORROW_SUCCESS -> content = "You have successfully borrowed a book." + details;
      case BOOK_RETURN_SUCCESS -> content = "You have successfully returned a book." + details;
      default -> content = "This is a email notification.";
    }

    String subject = "Library Notification: " + type;
    emailNotification.setContent(content);
    FactoryDAO.getEmailNotificationDAO().add(emailNotification);
    sendEmail("me", "me", email, subject, content);
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

    GoogleClientSecrets clientSecrets = GoogleClientSecrets
        .load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
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
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();

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
