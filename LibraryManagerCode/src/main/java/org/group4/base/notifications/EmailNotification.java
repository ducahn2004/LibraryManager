package org.group4.base.notifications;

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
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Base64;
import org.group4.base.enums.NotificationType;

/**
 * Class to handle sending email notifications using Gmail API.
 */
public class EmailNotification extends Notification {
  // Application name for the Gmail API.
  private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
  // JSON factory used for processing API responses.
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  // Path where the OAuth2 tokens are stored.
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  // List of Gmail API scopes required for sending email.
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  // Path to the credentials JSON file.
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  /**
   * Constructs an EmailNotification instance.
   *
   * @param type    The type of notification.
   * @param content The content of the notification.
   */
  public EmailNotification(NotificationType type, String content) {
    super(type, content);
  }

  /**
   * Returns the OAuth2 credentials for accessing Gmail API.
   *
   * @param HTTP_TRANSPORT The HTTP transport used for communication.
   * @return The OAuth2 credentials.
   * @throws IOException If an error occurs during credentials retrieval.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    InputStream in = EmailNotification.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }

    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  /**
   * Sends an email message using Gmail API.
   *
   * @param userId The user ID (email address).
   * @param from   The sender's email address.
   * @param to     The recipient's email address.
   * @param subject The subject of the email.
   * @param bodyText The body of the email.
   * @throws Exception If an error occurs while sending the email.
   */
  public static void sendEmail(String userId, String from, String to, String subject, String bodyText) throws Exception {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();

    MimeMessage email = createEmail(to, from, subject, bodyText);
    sendMessage(service, userId, email);
  }

  /**
   * Creates a MimeMessage object from the provided email details.
   *
   * @param to       The recipient's email address.
   * @param from     The sender's email address.
   * @param subject  The subject of the email.
   * @param bodyText The body content of the email.
   * @return A MimeMessage object.
   * @throws MessagingException If an error occurs while creating the message.
   */
  private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
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
   * Sends the email message through Gmail API.
   *
   * @param service The Gmail service instance.
   * @param userId  The user ID (email address).
   * @param email   The MimeMessage object to send.
   * @throws MessagingException If an error occurs while sending the message.
   * @throws IOException        If an I/O error occurs.
   */
  private static void sendMessage(Gmail service, String userId, MimeMessage email) throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    email.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    String encodedEmail = Base64.getUrlEncoder().encodeToString(rawMessageBytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    service.users().messages().send(userId, message).execute();
  }

}
