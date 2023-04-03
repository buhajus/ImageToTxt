package org.example;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

import javax.imageio.ImageIO;
import javax.media.*;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String url = "rtsp://admin:admin@192.168.1.87:554/stream1"; // replace with your RTSP stream URL

    public static void main(String[] args) throws TesseractException, IOException, FrameGrabber.Exception, InterruptedException, NoPlayerException, CannotRealizeException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        Tesseract tesseract = new Tesseract();
        Imgcodecs imgcodecs = new Imgcodecs();
        // Date date = new Date();
        LocalDate localDate = LocalDate.now();
        // LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Europe/Vilnius"));
        // System.out.println(dateTime);
        //SimpleDateFormat formater = new SimpleDateFormat("YYYY-mm-dd");

        String path = "src/img/5.jpg";
        String savePath = "src/img/records_" + localDate + ".jpg";
        tesseract.setDatapath("tessdata/");
        tesseract.setLanguage("eng");
        captu2();
        // capture2();
        Mat img = loadImages(path);
//        Mat newImg = cropImages(img);
//
//        //Size size = new Size(1000, 1000);
        Mat dst = new Mat();
//        // Imgproc.resize(newImg, dst, size);
//        System.out.println(newImg);
//        // crop loaded image
//
//
//        //Mat kernel = Mat.ones(0, 200, CvType.CV_32F);
//
//
//        //Imgproc.bilateralFilter(newImg, dst, 5, 50, 50, Core.BORDER_DEFAULT);
//        // Imgproc.morphologyEx(newImg, dst, Imgproc.MORPH_OPEN, kernel);
//
//
//        //write the image
//
//        imgcodecs.imwrite(savePath, newImg);
//
//
//        String result = tesseract.doOCR(new File(savePath));
//        List<String> list = Arrays.asList(result);
//        System.out.println(result);
        //   System.out.println(result.toString());


//        HighGui.imshow("Img", newImg);
//        HighGui.moveWindow("Img", 0, 0);
//        HighGui.waitKey(0);


    }

    public static Mat loadImages(String path) {
        Imgcodecs imgcodecs = new Imgcodecs();
        return imgcodecs.imread(path);

    }

    public static Mat cropImages(Mat matrix) {
        Rect rectCrop = new Rect(0, 0, 1000, 1000);
        Mat cropped = new Mat(matrix, rectCrop);
        return cropped;
    }

    public static void setImageSize(Mat matrix) {
        Mat dst = new Mat();
        Size size = new Size(1000, 1000);
        Imgproc.resize(matrix, dst, size);
    }

    public static void captureVideoFromCamera() throws IOException, FrameGrabber.Exception {
        //String url = "rtsp://admin:admin@192.168.1.87:554/stream1";
        OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(url);
        frameGrabber.setFormat("h264");
        frameGrabber.start();
        IplImage iPing = frameGrabber.grab();
        CanvasFrame canvasFrame = new CanvasFrame("Camera");
        canvasFrame.setCanvasSize(iPing.width(), iPing.height());
        while (canvasFrame.isVisible() && (iPing = frameGrabber.grab()) != null) {
            canvasFrame.showImage(iPing);
        }
        frameGrabber.stop();
        canvasFrame.dispose();
        System.exit(0);
//        Mat image = new Mat();
//        VideoCapture ipcamera = new VideoCapture(url);
//        ipcamera.read(image);
//        Imgcodecs imgcodecs = new Imgcodecs();
//
//        imgcodecs.imwrite("src/img/new/ss.jpg",image);


    }

    public static void captureImageFromCamera() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String url = "rtsp://admin:admin@192.168.1.87:554/stream1";
        System.setProperty("OPENCV_FFMPEG_CAPTURE_OPTIONS", "rtsp_transport;tcp");
        VideoCapture capture = new VideoCapture(url, Videoio.CAP_FFMPEG);
        Mat frame = new Mat();
//        try {
//            System.out.println("Delay waiting..");
//            Thread.sleep(10000); // wait while stream open from dvr
//            System.out.println("Delay end..");
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }

        if (!capture.isOpened()) {
            System.out.println("Cannot open RTSP stream");
            System.exit(-1);
        }
        while (true) {
            capture.read(frame);
            HighGui.imshow("Stream", frame);

            if (HighGui.waitKey(1) == 27) {
                break;
            }
        }
        capture.release();
        HighGui.destroyAllWindows();
        System.exit(0);
    }

    public static void capute() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String url = "rtsp://admin:admin@192.168.1.87/stream1"; // replace with your RTSP stream URL

        VideoCapture capture = new VideoCapture(url);

        Mat image = new Mat();

        capture.retrieve(image);
        System.out.println(image);
        capture.read(image);

        if (!image.empty()) {
            Imgcodecs.imwrite("image.jpg", image);
            System.out.println("Image saved.");
        } else {
            System.out.println("Failed to capture image.");
        }

        capture.release();
    }


    public static void captu2() throws CannotRealizeException, IOException, NoPlayerException {

        MediaLocator locator = new MediaLocator("rtsp://admin:admin@192.168.1.87:554/stream1");

        Player player = Manager.createRealizedPlayer(locator);

        // Start the player
        player.start();

        // Get the frame grabbing control from the player
        FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");

        // Grab a frame from the RTSP stream
        Buffer buf = fgc.grabFrame();

        // Get the video format of the captured frame
        VideoFormat vf = (VideoFormat) buf.getFormat();

        // Convert the ByteBuffer to a byte array
        byte[] data = (byte[]) buf.getData();

        // Create a BufferedImage from the captured frame data
        Dimension size = vf.getSize();
        BufferedImage img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        img.getRaster().setDataElements(0, 0, size.width, size.height, data);

        // Save the BufferedImage to a file
        File file = new File("snapshot.jpg");
        ImageIO.write(img, "jpg", file);

        // Stop the player
        player.stop();
        player.close();
    }


}

