package org.group4.module.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Logger;
import java.util.logging.Level;

public class QRCodeScanner {

    /** Logger for debugging and error messages. */
    private static final Logger logger = Logger.getLogger(QRCodeScanner.class.getName());

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Scans for a QR code using the default camera.
     *
     * @return the detected QR code text, or null if not found
     */
    public static String scanQRCodeFromCamera() {
        VideoCapture camera = new VideoCapture(0, Videoio.CAP_DSHOW);

        // Check if the camera is accessible
        if (!camera.isOpened()) {
            logger.severe("Error: Camera not accessible");
            return null;
        }

        Mat frame = new Mat(); // Create a new matrix to store the frame
        String qrCodeText = null;
        int attempts = 0;
        final int maxAttempts = 100; // Maximum number of attempts to scan QR code

        try {
            while (attempts < maxAttempts) {
                if (camera.read(frame)) {
                    // Convert the frame to grayscale
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

                    // Convert the frame (Mat) to a BufferedImage
                    BufferedImage bufferedImage = matToBufferedImage(frame);

                    // Try to decode a QR code from the image
                    try {
                        qrCodeText = decodeQRCodeFromImage(bufferedImage);

                        // If a QR code is detected, log it and break the loop
                        if (qrCodeText != null) {
                            logger.info("QR Code detected: " + qrCodeText);
                            break;
                        }
                    } catch (NotFoundException e) {
                        logger.log(Level.FINE, "QR Code not found in this frame", e);
                    }
                }
                attempts++;
            }
        } finally {
            camera.release(); // Ensure the camera is released
        }

        // Log a warning if no QR code is detected
        if (qrCodeText == null) {
            logger.warning("QR Code not detected after " + maxAttempts + " attempts");
        }

        return qrCodeText;
    }

    /**
     * Decodes a QR code from a {@link BufferedImage}.
     *
     * @param bufferedImage the image containing the QR code
     * @return the decoded QR code text, or null if not found
     * @throws NotFoundException if no QR code is found in the image
     */
    public static String decodeQRCodeFromImage(BufferedImage bufferedImage)
        throws NotFoundException {
        // Convert the BufferedImage to a LuminanceSource
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);

        // Decode the QR code from the LuminanceSource
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        // Return the decoded text if found
        Result result = new MultiFormatReader().decode(bitmap);
        return result != null ? result.getText() : null;
    }

    /**
     * Converts a {@link Mat} object to a {@link BufferedImage} object.
     *
     * @param mat the {@link Mat} object to convert
     * @return the converted {@link BufferedImage} object
     */
    private static BufferedImage matToBufferedImage(Mat mat) {
        // Create a BufferedImage with the same dimensions and type as the Mat
        int type = (mat.channels() == 1) ?
            BufferedImage.TYPE_BYTE_GRAY :
            BufferedImage.TYPE_3BYTE_BGR;

        // Convert the Mat to a BufferedImage
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);

        // Get the data buffer of the Mat and set it to the BufferedImage
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }
}