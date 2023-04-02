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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_ANYCOLOR;
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

        String path = "src/img/3.jpg";
        String savePath = "src/img/records_" + localDate + ".jpg";
        // tesseract.setDatapath("tessdata/*");
        tesseract.setLanguage("eng");



        Mat img = loadImages(path);
        System.out.println(img);
        Mat newImg = cropImages(img);// crop loaded image

        Mat dst = new Mat();
        Mat kernel = Mat.ones(0, 200, CvType.CV_32F);


       Imgproc.bilateralFilter(newImg, dst, 5, 50, 50, Core.BORDER_DEFAULT);
        // Imgproc.morphologyEx(newImg, dst, Imgproc.MORPH_OPEN, kernel);


        //write the image

        imgcodecs.imwrite(savePath,dst);



        String result = tesseract.doOCR(new File(savePath));
        List <String>  list = Arrays.asList(result);
        System.out.println(result);
     //   System.out.println(result.toString());


        HighGui.imshow("Img", dst);
        HighGui.moveWindow("Img",1500,200);
        HighGui.waitKey(0);


    }

    public static Mat loadImages(String path) {
        Imgcodecs imgcodecs = new Imgcodecs();
        return imgcodecs.imread(path, IMREAD_GRAYSCALE);

    }

    public static Mat cropImages(Mat matrix) {
        Rect rectCrop = new Rect(0, 145, 200, 50);
        Mat cropped = new Mat(matrix, rectCrop);
        return cropped;
    }


}

