package org.group4.base.books;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an author who can write multiple books.
 * This class manages the author's name and their associated books.
 * It provides methods to add or remove books from the author's list.
 */
public class Author {
    // Name of the author
    private String name;
    // A set of books written by the author
    private Set<Book> books = new HashSet<>();

    /**
     * Constructs an Author instance with the specified name.
     *
     * @param name The name of the author.
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the author.
     *
     * @return The author's name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name The new name of the author.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the set of books associated with this author.
     *
     * @return A Set of Book objects written by the author.
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Sets the set of books associated with this author.
     *
     * @param books A Set of Book objects to be associated with the author.
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Adds a book to the author's list of books.
     *
     * @param book The book to be added to the author's list.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book from the author's list of books.
     *
     * @param book The book to be removed from the author's list.
     */
    public void removeBook(Book book) {
        books.remove(book);
    }
}
