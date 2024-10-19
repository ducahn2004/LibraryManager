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
            if (bookItem != null) {
                bookItem.printDetails();
            } else {
                System.out.println("BookItem with barcode " + barcode + " not found.");
            }
        }
    }

}