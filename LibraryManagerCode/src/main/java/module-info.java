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
  requires org.slf4j;
  requires mysql.connector.j;
  requires org.mockito;
  requires org.apache.logging.log4j;
  requires java.sql;

  exports org.group4.controller;
  opens org.group4.controller to javafx.fxml;

  exports org.group4.model.notification;
  opens org.group4.model.notification to javafx.fxml;

  exports org.group4.model.user;
  opens org.group4.model.user to javafx.fxml;

  exports org.group4.model.book;
  opens org.group4.model.book to javafx.fxml;

  exports org.group4.model.enums;
  opens org.group4.model.enums to javafx.fxml;

  exports org.group4.model.transaction;
  opens org.group4.model.transaction to javafx.fxml;

  exports org.group4.service.transaction;
  opens org.group4.service.transaction to javafx.fxml;

  exports org.group4.test;
  opens org.group4.test to junit;
  exports org.group4.service.book;
  opens org.group4.service.book to javafx.fxml;
  exports org.group4.service.user;
  opens org.group4.service.user to javafx.fxml;
  exports org.group4.service.interfaces;
  opens org.group4.service.interfaces to javafx.fxml;
}