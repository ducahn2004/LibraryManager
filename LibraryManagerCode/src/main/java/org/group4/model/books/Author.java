package org.group4.model.books;

/**
 * Represents an author with an identifier and a name.
 * <p>This class provides basic information about an author, such as a unique ID
 * and name, along with methods to retrieve and set these details.</p>
 */
public class Author {

    private String authorId;
    private String name;

    /**
     * Constructs an {@code Author} with only a name.
     *
     * @param name the name of the author
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Constructs an {@code Author} with an ID and a name.
     *
     * @param authorId the unique ID of the author
     * @param name     the name of the author
     */
    public Author(String authorId, String name) {
        this.authorId = authorId;
        this.name = name;
    }

    /**
     * Retrieves the unique identifier of the author.
     *
     * @return the author's unique ID
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * Sets the unique identifier of the author.
     *
     * @param authorId the new ID to assign to the author
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    /**
     * Retrieves the name of the author.
     *
     * @return the author's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name the new name to assign to the author
     */
    public void setName(String name) {
        this.name = name;
    }
}
