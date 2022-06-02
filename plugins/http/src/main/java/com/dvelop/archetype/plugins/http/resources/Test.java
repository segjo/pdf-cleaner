package com.dvelop.archetype.plugins.http.resources;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\temp\\Test.pdf");
        PDDocument document = Loader.loadPDF(file);
        PDDocument outDocument = new PDDocument();
        int pagecount = 0;
        boolean newVersion = false;
        
        if(document.getNumberOfPages()>1){
            for (PDPage page : document.getPages()) {
                PDFTextStripper textStripper = new PDFTextStripper();
                textStripper.setStartPage(pagecount+1);
                textStripper.setEndPage(pagecount+1);
                String text = textStripper.getText(document);
                text = text.trim();
                if (text == null || text.equals("")) {
                    newVersion=true;
                } else {
                    outDocument.addPage(page);
                }
                pagecount++;
            }
            
            outDocument.save(file);
            outDocument.close();
            document.close();
            
        }
    }
}
