package com.levik.tiftopdf;

import java.io.*;
import com.itextpdf.text.DocumentException;
import com.levik.tiftopdf.utils.ConvectorUtils;

/**
 * @author Polukov Evgen
 * @version 1.0
 * @since jdk1.6.0_20
 * Date: 26.10.13
 * Time: 20:34
 */
public class Executor {

    private static final String PATH = "C:\\";
    private static final String NAME = "test";
    private static final String TIF_TYPE = ".tif";
    private static final String PDF_TYPE = ".pdf";

    public static void convertTifToPdf() throws IOException, DocumentException {
        InputStream tifFile = null;
        OutputStream pdfDocument = null;

        try{
            tifFile = new FileInputStream(PATH + NAME + TIF_TYPE);
            System.out.println("Tif file was load, name is " + NAME);

            pdfDocument = new FileOutputStream(PATH + NAME + PDF_TYPE);
            System.out.println("Pdf file was create in memory, name is " + NAME);

            pdfDocument = ConvectorUtils.convert(tifFile, pdfDocument);
            System.out.println("Tif document was was transformed successful to Pdf");
        }finally {
            if (tifFile != null) {
                try{
                    tifFile.close();
                    System.out.println("TifFile file was close");
                }catch (IOException e){
                    System.err.println("Can't close tifFile. Error message is " + e.getMessage());
                    throw e;
                }finally{
                    try{
                        if (pdfDocument != null){
                            pdfDocument.close();
                            System.out.printf("PdfDocument file was close");
                        }
                    }catch (IOException e){
                        System.err.println("Can't close pdfDocument. Error message is " + e.getMessage());
                        throw e;
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws IOException, DocumentException {
        Executor.convertTifToPdf();
    }
}
