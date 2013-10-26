package com.levik.tiftopdf.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Polukov Evgen
 * @version 1.0
 * @since jdk1.6.0_20
 * Date: 26.10.13
 * Time: 20:35
 */
public class ConvectorUtils {

    private static final int FIT_WIDTH = 500;
    private static final int FIT_HEIGHT = 900;

    public static OutputStream convert(InputStream tifFile, OutputStream pdfFile) throws IOException, DocumentException {
        Document pdfDocument = null;
        RandomAccessFileOrArray tifWrapperDocument = null;
        PdfWriter pdfWriter = null;

        try {
            pdfDocument = new Document();
            System.out.println("PfDocument was created");

            tifWrapperDocument = new RandomAccessFileOrArray(tifFile);
            System.out.println("Tif was wrapped");
            try {
                pdfWriter = PdfWriter.getInstance(pdfDocument, pdfFile);
                System.out.println("PdfWriter was created");
            } catch (DocumentException e) {
                System.err.println("Can't create pdfWriter. Error message is " + e.getMessage());
                throw e;
            }

            pdfDocument.open();
            System.out.println("PdfDocument was open");

            for (int index = 1; index <= TiffImage.getNumberOfPages(tifWrapperDocument); index++) {
                Image tempImage = TiffImage.getTiffImage(tifWrapperDocument, index);
                tempImage.scaleToFit(FIT_WIDTH, FIT_HEIGHT);
                try {
                    pdfDocument.add(tempImage);
                    System.out.println("PdfDocument was add tifWrapper");
                } catch (DocumentException e) {
                    System.err.println("Can't add tifImage top pdfDocument. Error message is " + e.getMessage());
                    throw e;
                }
            }
            return pdfFile;
        } finally {
            closeResources(pdfDocument, tifWrapperDocument,pdfWriter);
        }
    }

    private static void closeResources(Document pdfDocument, RandomAccessFileOrArray tifWrapperDocument, PdfWriter pdfWriter) throws IOException {
        try {
            if (pdfDocument != null) {
                pdfDocument.close();
                System.out.println("Wrapper of pdfDocument was close");
            }
        } finally {
            try {
                if (tifWrapperDocument != null) {
                    tifWrapperDocument.close();
                    System.out.println("Wrapper of tifWrapperDocument was close");
                }
            } catch (IOException e) {
                System.err.println("Can't close wrapper of tifDocument. Error message is " + e.getMessage());
                throw e;
            }finally {
                if (pdfWriter != null) {
                    pdfWriter.close();
                    System.out.println("PdfWriter was close");
                }
            }
        }
    }
}
