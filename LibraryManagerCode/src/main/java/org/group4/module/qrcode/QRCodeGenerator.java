package org.group4.module.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    /**
     * Generates a QR code for the given text and saves it to the specified file path.
     *
     * @param text the text to encode
     * @param filePath the file path to save the QR code
     * @param width the width of the QR code
     * @param height the height of the QR code
     * @throws WriterException if an error occurs while encoding the text
     * @throws IOException if an error occurs while writing the QR code to the file
     */
    public static void generateQRCode(String text, String filePath, int width, int height)
        throws WriterException, IOException {
        // Create a QRCodeWriter object to generate the QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // Configure encoding hints for the QR code generation
        // These hints specify the character set to use for encoding the text (UTF-8 in this case)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // The BitMatrix object contains the binary representation of the QR code pixels
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // Define the output file path where the QR code image will be saved
        Path path = FileSystems.getDefault().getPath(filePath);

        // Write the BitMatrix to the specified file path as a PNG image
        // MatrixToImageWriter is a utility class that converts the BitMatrix into an image
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}