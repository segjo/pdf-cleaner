package com.dvelop.archetype.plugins.http.resources;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvelop.archetype.plugins.context.TenantHolder;

@Path(ProcessDocument.PATH)
public class ProcessDocument {
    public static final String PATH = "/document/process";

    // Benötigte Variablen initialisieren
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    // Brauchen wir für die BaseURI
    TenantHolder tenantHolder;

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response processDocument(@QueryParam("filePath") String filePath) throws MalformedURLException, IOException {        
        //Aufrufen der anderen Funktion
        int pagecount = 0;
        boolean newVersion = false;
        File file = new File(filePath);
        PDDocument document = Loader.loadPDF(file);
        PDDocument outDocument = new PDDocument();
        
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

        if(newVersion){
            return Response.ok(file.getAbsolutePath()).build();
        }else{
            return Response.ok("no blank pages detected / document has only one page").build();
        }
    }
}
