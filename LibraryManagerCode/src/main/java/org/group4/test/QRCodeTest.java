package org.group4.test;

import org.group4.qr.QRCodeGenerator;
import org.group4.qr.QRCodeReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;

public class QRCodeTest {

    public static void main(String[] args) {
        String bookInfo = "Book: Harry Potter and the Philosopher's Stone\nAuthor: J.K. Rowling\nISBN: 9781408855652";
        try {
            // Generate QR Code
            String filePath = "";
            QRCodeGenerator.generateQRCode(bookInfo, filePath, 350, 350);
            System.out.println("QR Code generated successfully.");

            // Read QR Code
            String decodedText = QRCodeReader.readQRCode(filePath);
            System.out.println("Decoded text from QR Code: " + decodedText);

        } catch (WriterException | IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}