package org.group4.database;

import org.group4.base.books.Author;

public class AuthorDatabase extends Database<Author> {
    private static final AuthorDatabase instance = new AuthorDatabase();

    private AuthorDatabase() {
        addItem(new Author("Nguyen Dog Ahn"));
        addItem(new Author("Tran Dog Anh"));
    }

    public static AuthorDatabase getInstance() {
        return instance;
    }
}