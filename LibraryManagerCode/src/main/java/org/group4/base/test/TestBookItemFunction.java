package org.group4.base.test;

import org.group4.base.books.BookItem;

public class TestBookItemFunction {
    public static void main(String[] args) {
        testFetchBookItemDetails();
    }

    public static void testFetchBookItemDetails() {
        String[] barcodes = {"1234567890", "0987654321"};
        for (String barcode : barcodes) {
            BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
            printBookItemDetails(bookItem, barcode);
        }
    }

    private static void printBookItemDetails(BookItem bookItem, String barcode) {
        if (bookItem != null) {
            System.out.println("Book found with barcode: " + barcode);
            System.out.println("Title: " + bookItem.getTitle());
            System.out.println("Authors: " + bookItem.getAuthors());
            System.out.println("Price: " + bookItem.getPrice());
            System.out.println("Format: " + bookItem.getFormat());
            System.out.println("Status: " + bookItem.getStatus());
            System.out.println("Date of purchase: " + bookItem.getDateOfPurchase());
            System.out.println("Publication date: " + bookItem.getPublicationDate());
        } else {
            System.out.println("Book not found with barcode: " + barcode);
        }
    }




}