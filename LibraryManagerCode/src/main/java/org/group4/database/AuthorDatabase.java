package org.group4.database;

import java.util.ArrayList;

import org.group4.base.entities.Author;

public class AuthorDatabase extends Database<Author> {
    private static final AuthorDatabase instance = new AuthorDatabase();

    private AuthorDatabase() {
        addItem(new Author("Nguyen Dog Ahn", "Red Flag", new ArrayList<>()));
        addItem(new Author("Tran Dog Anh", "Trap boy", new ArrayList<>()));
    }

    public static AuthorDatabase getInstance() {
        return instance;
    }
}