module org.group.librarymanagercode {
  requires javafx.fxml;
  requires javafx.web;
  requires junit;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires com.almasb.fxgl.all;
  requires com.jfoenix;
  requires java.logging;
  requires annotations;
  requires java.desktop;
  requires okhttp;
  requires com.google.gson;
  requires org.json;
  requires com.google.zxing;
  requires com.google.zxing.javase;
  requires opencv;
  requires com.google.api.client.auth;
  requires com.google.api.client.extensions.java6.auth;
  requires com.google.api.client;
  requires com.google.api.client.json.gson;
  requires jakarta.mail;
  requires google.api.client;
  requires com.google.api.client.extensions.jetty.auth;
  requires jdk.httpserver;
  requires com.google.api.services.gmail;
  requires org.testng;
  requires jbcrypt;

  opens org.group4.librarymanagercode to javafx.fxml;
  exports org.group4.librarymanagercode;

  exports org.group4.base.books;
  opens org.group4.base.books to javafx.fxml;

  exports org.group4.base.enums;
  opens org.group4.base.enums to javafx.fxml;

  exports org.group4.base.users;
  opens org.group4.base.users to javafx.fxml;

  exports org.group4.base.manager;
  opens org.group4.base.manager to javafx.fxml;

  exports org.group4.base.catalog;
  opens org.group4.base.catalog to javafx.fxml;

  exports org.group4.base.notifications;
  opens org.group4.base.notifications to javafx.fxml;

  exports org.group4.database;
  opens org.group4.database to javafx.fxml;

  exports org.group4.test;
  opens org.group4.test to junit;
}