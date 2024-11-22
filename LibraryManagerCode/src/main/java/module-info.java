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
  requires google.api.client;
  requires com.google.api.client.extensions.jetty.auth;
  requires jdk.httpserver;
  requires com.google.api.services.gmail;
  requires org.testng;
  requires jbcrypt;
  requires javax.mail.api;
  requires com.zaxxer.hikari;
  requires java.sql;
  requires org.slf4j;
  requires mysql.connector.j;

  exports org.group4.controller;
  opens org.group4.controller to javafx.fxml;

  exports org.group4.model.notifications;
  opens org.group4.model.notifications to javafx.fxml;

  exports org.group4.model.users;
  opens org.group4.model.users to javafx.fxml;

  exports org.group4.model.books;
  opens org.group4.model.books to javafx.fxml;

  exports org.group4.model.enums;
  opens org.group4.model.enums to javafx.fxml;

  exports org.group4.model.transactions;
  opens org.group4.model.transactions to javafx.fxml;

  exports org.group4.model.manager;
  opens org.group4.model.manager to javafx.fxml;

  exports org.group4.test;
  opens org.group4.test to junit;
}