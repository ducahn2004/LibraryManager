package org.group4.service.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeReader {

    /**
     * Reads the QR code from the given file path.
     *
     * @param filePath the file path of the QR code image
     * @return the text encoded in the QR code
     * @throws IOException if an I/O error occurs
     * @throws NotFoundException if the QR code cannot be found
     */
    public static String readQRCode(String filePath) throws IOException, NotFoundException {
        // Read the image file into a BufferedImage
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));

        // Convert the BufferedImage to a LuminanceSource
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);

        // Decode the QR code from the binary bitmap
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }
}