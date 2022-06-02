package com.dvelop.archetype.plugins.http.resources;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        boolean newVersion=false;
        //Aufrufen der anderen Funktion
        int pagecount = 0;
        List<Integer> pagesToRemove = new ArrayList<>();

        //PDDocument document = PDDocument.load(file);
        byte[] buffer = java.nio.file.Files.readAllBytes(Paths.get(filePath));

        File file = new File("src/test/resources/targetFile.tmp");
        OutputStream outStream = new FileOutputStream(file);
        outStream.write(buffer);

        IOUtils.closeQuietly(outStream);
        PDDocument document = Loader.loadPDF(file);
        if(document.getNumberOfPages()>1){
            for (PDPage pdPage : document.getPages()) {
                PDFTextStripper textStripper = new PDFTextStripper();
                textStripper.setStartPage(pagecount+1);
                textStripper.setEndPage(pagecount+1);
                String text = textStripper.getText(document);
                text = text.trim();
                if (text == null || text.equals("")) {
                    newVersion=true;
                    pagesToRemove.add(pagecount);
                }
                pagecount++;
            }
        }

        for (Integer pagenumber : pagesToRemove) {
            System.out.println("remove page: " + pagenumber);
            document.removePage(pagenumber);
        }

        document.save(file);
        document.close();

        if(newVersion){
            return Response.ok(file.getAbsolutePath()).build();
        }else{
            return Response.ok("no blank pages detected / document has only one page").build();
        }
    }
}
