package org.example;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE;

public class Main {
    public static void main(String[] args) throws TesseractException {
        OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        Tesseract tesseract = new Tesseract();
        Imgcodecs imgcodecs = new Imgcodecs();
       // Date date = new Date();
        LocalDate localDate = LocalDate.now();
       // LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Europe/Vilnius"));
       // System.out.println(dateTime);
        //SimpleDateFormat formater = new SimpleDateFormat("YYYY-mm-dd");

        String path = "src/img/4.jpg";
        String savePath = "src/img/records_"+ localDate +".jpg";
        // tesseract.setDatapath("tessdata/*");
        tesseract.setLanguage("lit");
        // reading image

        Mat img = loadImages(path);
       HighGui.imshow("Image ", img);
           Rect rectCrop = new Rect(190, 55, 150, 100);
          Mat cropped = new Mat(img,rectCrop);
        //  Mat src = new Mat();
          Mat dst = new Mat();
         Mat kernel = Mat.ones(5, 5, CvType.CV_32F);


        Imgproc.bilateralFilter(cropped, dst, 15, 80, 80, Core.BORDER_DEFAULT);
          // Imgproc.morphologyEx(cropped, dst, Imgproc.MORPH_CLOSE, kernel);


        //write the image
        HighGui.imshow("Img", cropped);
        imgcodecs.imwrite(savePath,cropped);

        String result = tesseract.doOCR(new File(savePath));
        System.out.println(result);
    }

    public static Mat loadImages(String path) {
        Imgcodecs imgcodecs = new Imgcodecs();
        return imgcodecs.imread(path, IMREAD_GRAYSCALE);
        // return imgcodecs.imread(path );
    }


}

