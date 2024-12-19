package com.volkerbecker.hdifferenz;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PDFSearcher {
        public String textvonPDF(String suchbegriff, File file){

            try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(file))) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                String searchTerm = suchbegriff;
                int index = text.indexOf(searchTerm);

                if (index != -1) {
                    System.out.println("Suchbegriff gefunden!");
                    String result = text.substring(index);
                    System.out.println(result);
                    return result;
                } else {
                    System.out.println("Suchbegriff nicht gefunden.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

