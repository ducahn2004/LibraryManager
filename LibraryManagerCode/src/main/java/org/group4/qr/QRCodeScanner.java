package org.group4.qr;

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

    private static final Logger logger = Logger.getLogger(QRCodeScanner.class.getName());

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        VideoCapture camera = new VideoCapture(0, Videoio.CAP_DSHOW);
        if (!camera.isOpened()) {
            logger.severe("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();
        while (true) {
            if (camera.read(frame)) {
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                BufferedImage bufferedImage = matToBufferedImage(frame);
                try {
                    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    if (result != null) {
                        logger.info("QR Code detected: " + result.getText());
                        break;
                    }
                } catch (NotFoundException e) {
                    logger.log(Level.WARNING, "QR Code not found", e);
                }
            }
        }
        camera.release();
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        int type = (mat.channels() == 1) ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }
}