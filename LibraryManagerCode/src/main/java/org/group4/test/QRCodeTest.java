package org.group4.test;

import org.group4.qr.QRCodeGenerator;
import org.group4.qr.QRCodeReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;

public class QRCodeTest {

    public static void main(String[] args) {
        String bookInfo = "ISBN: 978-3-16-148410-0\nTitle: Example Book\nAuthor: John Doe";
        String filePath = "book_qr.png";

        try {
            // Generate QR Code
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