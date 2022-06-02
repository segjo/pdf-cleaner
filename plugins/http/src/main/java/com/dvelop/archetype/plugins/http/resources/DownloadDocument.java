package com.dvelop.archetype.plugins.http.resources;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvelop.archetype.plugins.context.TenantHolder;

@Path(DownloadDocument.PATH)
public class DownloadDocument {
    public static final String PATH = "/document/download";

    // Benötigte Variablen initialisieren
    Logger log = LoggerFactory.getLogger(this.getClass());
    String repositoryId;
    String docID;
    String downloadDokURL;
    String AuthSessionID;
    List<String> headerValueList = new ArrayList<>();
    String tmpLocation;
    String newLine = System.getProperty("line.separator");
    String updateBody = "";
    String success = "Nein :(";

    @Inject
    // Brauchen wir für die BaseURI
    TenantHolder tenantHolder;

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response testRepo(@CookieParam("AuthSessionId") String authKey, @Context HttpHeaders headers, @QueryParam("docId") String docID, @QueryParam("repoId") String repositoryId) throws MalformedURLException, IOException {        

        downloadDokURL = tenantHolder.getBaseUri() + "/dms/r/" + repositoryId + "/o2/" + docID + "/v/current/b/main/c";
        /*
        //Download des Dokumentes
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(downloadDokURL).openConnection();
        if(authKey != null) {
            String myCookie = "AuthSessionId=" + authKey;
            httpConnection.setRequestProperty("Cookie", myCookie);
        }
        else {
            headerValueList = headers.allValues("Authorization");

            if (headerValueList.size() > 0) {
                AuthSessionID = headerValueList.get(0);
            }
            httpConnection.setRequestProperty("Authorization", "Bearer " + AuthSessionID);
        }

        httpConnection.setRequestMethod("GET");

        File file = File.createTempFile( "tempfile", ".pdf");
        InputStream inStream = httpConnection.getInputStream();
        byte[] buffer = new byte[4096];
        int n;
        OutputStream output = new FileOutputStream(file);
        
        while ((n = inStream.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        output.close();

        boolean newVersion=false;
        //Aufrufen der anderen Funktion
        int pagecount = 0;
        List<Integer> pagesToRemove = new ArrayList<>();

        //PDDocument document = PDDocument.load(file);
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
        //Temporärer Upload der geänderten Datei
        httpConnection = (HttpURLConnection) new URL(uploadDokURL).openConnection();
        httpConnection.setRequestProperty("Content-Type", "application/octet-stream");
        httpConnection.setRequestProperty("Origin", tenantHolder.getBaseUri());
        if(authKey != null) {
            String myCookie = "AuthSessionId=" + authKey;
            httpConnection.setRequestProperty("Cookie", myCookie);
        }
        else {
            headerValueList = headers.allValues("Authorization");

            if (headerValueList.size() > 0) {
                AuthSessionID = headerValueList.get(0);
            }
            httpConnection.setRequestProperty("Authorization", "Bearer " + AuthSessionID);
        }
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        
        try {
            OutputStream os = httpConnection.getOutputStream();
            byte[] bytearray = Files.readAllBytes(file.toPath());
                os.write(bytearray, 0, bytearray.length);
        } catch (Exception ex) {
            //Something to do
        }

        int responseCode = httpConnection.getResponseCode();
        log.info("responseCode Upload: " + responseCode);

        if (responseCode == 201) {
            String locationHeader = httpConnection.getHeaderField("Location");
            tmpLocation = locationHeader;
        }
        else {
            return Response.ok(success).build();
        }

        // Senden des Updates
        httpConnection = (HttpURLConnection) new URL(updateDokURL).openConnection();
        httpConnection.setRequestProperty("Content-Type", "application/hal+json");
        httpConnection.setRequestProperty("Origin", tenantHolder.getBaseUri());
        if(authKey != null) {
            String myCookie = "AuthSessionId=" + authKey;
            httpConnection.setRequestProperty("Cookie", myCookie);
        }
        else {
            headerValueList = headers.allValues("Authorization");

            if (headerValueList.size() > 0) {
                AuthSessionID = headerValueList.get(0);
            }
            httpConnection.setRequestProperty("Authorization", "Bearer " + AuthSessionID);
        }
        httpConnection.setRequestProperty("Accept", "application/hal+json");
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("PUT");

        updateBody = "{\"alterationText\": \"updated file\", \"contentLocationUri\": \"" + tmpLocation + "\" }";

        try(OutputStream os = httpConnection.getOutputStream()) {
            byte[] input = updateBody.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        responseCode = httpConnection.getResponseCode();
        log.info("responseCode Update : " + responseCode);

        if (responseCode == 200) {
            success = "Ja :)";
        }
        else {
            return Response.ok(success).build();
        }
            return Response.ok(success).build();
        }else{
            return Response.ok("no blank pages detected / document has only one page").build();
        }
        */
        return Response.ok("no blank pages detected / document has only one page").build();
    }
}
