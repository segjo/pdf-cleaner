package com.dvelop.archetype.plugins.http.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

@Path(UploadDocument.PATH)
public class UploadDocument {
    public static final String PATH = "/document/upload";

    // Benötigte Variablen initialisieren
    Logger log = LoggerFactory.getLogger(this.getClass());
    String repositoryId;
    String uploadDokURL;
    String AuthSessionID;
    List<String> headerValueList = new ArrayList<>();
    int responseCode;

    @Inject
    // Brauchen wir für die BaseURI
    TenantHolder tenantHolder;

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response testRepo(@CookieParam("AuthSessionId") String authKey, @Context HttpHeaders headers, @QueryParam("repoId") String repositoryId, @QueryParam("filePath") String filePath) throws MalformedURLException, IOException {        
        uploadDokURL = tenantHolder.getBaseUri() + "/dms/r/" + repositoryId + "/blob/chunk/";

        //Temporärer Upload der geänderten Datei
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(uploadDokURL).openConnection();
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
        
        File file = new File(filePath);
        try {
            OutputStream os = httpConnection.getOutputStream();
            byte[] bytearray = Files.readAllBytes(file.toPath());
                os.write(bytearray, 0, bytearray.length);
        } catch (Exception ex) {
            //Something to do
        }

        responseCode = httpConnection.getResponseCode();

        if (responseCode == 201) {
            String locationHeader = httpConnection.getHeaderField("Location");
            return Response.ok(locationHeader).build();
        }
        else {
            return Response.status(responseCode).build();
        }
    }
}
