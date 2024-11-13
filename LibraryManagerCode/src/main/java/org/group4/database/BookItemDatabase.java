package org.group4.database;

import java.time.LocalDate;
import java.util.Set;
import org.group4.base.books.Author;
import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.catalog.Rack;
import org.group4.base.enums.BookFormat;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {
        initializeBookItems();
    }
    public static BookItemDatabase getInstance() {
        return instance;
    }
    private void initializeBookItems() {
        // Sample authors set, replace or expand with real Author data if available.
        Set<Author> authors = Set.of(new Author("Author Name")); // Replace with actual authors.

        // Tạo các đối tượng sách với ISBN đã cung cấp
        Book book1 = new Book("978-0-7475-3269-9", "Harry Potter and the Philosopher's Stone", "Fantasy", "Bloomsbury", "English", 223, authors);
        Book book2 = new Book("978-0-452-28423-4", "1984", "Dystopian", "Signet Classics", "English", 328, authors);
        Book book3 = new Book("978-0-06-112008-4", "To Kill a Mockingbird", "Fiction", "Harper Perennial", "English", 336, authors);
        Book book4 = new Book("978-0-19-283355-4", "Pride and Prejudice", "Romance", "Oxford University Press", "English", 279, authors);
        Book book5 = new Book("978-0-7432-7356-5", "The Da Vinci Code", "Thriller", "Anchor", "English", 489, authors);

        // Tạo các bản sao BookItem cho mỗi Book
        createBookItemsForBook(book1, BookFormat.HARDCOVER, 20.0, LocalDate.of(2022, 1, 10), LocalDate.of(1997, 6, 26));
        createBookItemsForBook(book2, BookFormat.PAPERBACK, 15.0, LocalDate.of(2023, 2, 5), LocalDate.of(1949, 6, 8));
        createBookItemsForBook(book3, BookFormat.EBOOK, 18.5, LocalDate.of(2021, 3, 15), LocalDate.of(1960, 7, 11));
        createBookItemsForBook(book4, BookFormat.AUDIOBOOK, 12.0, LocalDate.of(2020, 5, 10), LocalDate.of(1813, 1, 28));
        createBookItemsForBook(book5, BookFormat.HARDCOVER, 25.0, LocalDate.of(2023, 6, 20), LocalDate.of(2003, 3, 18));
    }

    private void createBookItemsForBook(Book book, BookFormat format, double price, LocalDate dateOfPurchase, LocalDate publicationDate) {
        for (int i = 1; i <= 5; i++) {
            Rack rack = new Rack(i, "R" + i + "-A" + i);
            BookItem bookItem = new BookItem(book, false, price, format, dateOfPurchase, publicationDate, rack);
            addItem(bookItem); // Thêm BookItem vào cơ sở dữ liệu
        }
    }
}