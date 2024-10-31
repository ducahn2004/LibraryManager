package org.group4.qr;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class QRCodeScanner {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();
        while (true) {
            if (camera.read(frame)) {
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                byte[] data = new byte[(int) (frame.total() * frame.channels())];
                frame.get(0, 0, data);

                try {
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
                    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    if (result != null) {
                        System.out.println("QR Code detected: " + result.getText());
                        break;
                    }
                } catch (IOException | NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        camera.release();
    }
}