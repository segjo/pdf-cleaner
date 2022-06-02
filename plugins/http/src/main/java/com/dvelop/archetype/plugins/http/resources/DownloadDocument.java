package com.dvelop.archetype.plugins.http.resources;

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
    int responseCode;

    @Inject
    // Brauchen wir für die BaseURI
    TenantHolder tenantHolder;

    @GET
    @Produces({MediaType.TEXT_PLAIN, "text/plain"})
    public Response downloadDocument(@CookieParam("AuthSessionId") String authKey, @Context HttpHeaders headers, @QueryParam("docId") String docID, @QueryParam("repoId") String repositoryId) throws MalformedURLException, IOException {        

        downloadDokURL = tenantHolder.getBaseUri() + "/dms/r/" + repositoryId + "/o2/" + docID + "/v/current/b/main/c";

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

        responseCode = httpConnection.getResponseCode();
        if(file.length() > 0) {
            return Response.ok(file.getAbsolutePath()).build();
        }else{
            return Response.status(responseCode).build();
        }
    }
}
