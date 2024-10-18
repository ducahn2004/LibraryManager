package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;
import org.group4.base.entities.Author;


public class AuthorDatabase {
    private static List<Author> authors = new ArrayList<>();

    static {
        // Add authors to the database
        authors.add(new Author("Joshua Bloch", "Author of Effective Java", new ArrayList<>()));
        authors.add(new Author("Robert Martin", "Author of Clean Code", new ArrayList<>()));
    }

    public static List<Author> getAuthors() {
        return authors;
    }
}
