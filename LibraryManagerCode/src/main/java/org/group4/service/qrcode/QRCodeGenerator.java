package org.group4.service.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.group4.model.book.BookItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);

    /**
     * Generates a QR code for the given text and saves it to the specified file path.
     *
     * @param text the text to encode
     * @param filePath the file path to save the QR code
     * @param width the width of the QR code
     * @param height the height of the QR code
     */
    public static void generateQRCode(String text, String filePath, int width, int height) {
        try {
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
        } catch (WriterException | IOException e) {
            logger.error("Error occurred while generating QR Code: {}", e.getMessage());
        }
    }

    /**
     * Generates a QR code for a given BookItem and saves it to the specified directory.
     *
     * <p>This method ensures the directory for storing QR code images exists. It then generates a
     * QR code based on the information provided by the {@code toQRCodeString()} method of the
     * {@link BookItem} class. The QR code is saved as a PNG file using the book's barcode as the
     * filename.
     *
     * @param bookItem the {@link BookItem} instance for which the QR code is generated
     * @return the file path of the generated QR code image
     * @throws IOException if an error occurs while creating the directory or saving the QR code
     */
    public static String generateQRCodeForBookItem(BookItem bookItem) throws IOException {
        // Ensure the directory exists
        Path path = Paths.get("LibraryManager/LibraryManagerCode/src/main/resources/Image");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Prepare the content for the QR code
        String bookItemInfo = bookItem.toQRCodeString();
        String imageName = bookItem.getBarcode() + ".png";
        String filePath =
            "LibraryManager/LibraryManagerCode/src/main/resources/Image/" + imageName;

        // Generate and save the QR code
        generateQRCode(bookItemInfo, filePath, 350, 350);
        return filePath;
    }
}