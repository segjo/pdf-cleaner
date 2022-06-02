package com.dvelop.archetype.plugins.http.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
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

@Path(UpdateDocument.PATH)
public class UpdateDocument {
    public static final String PATH = "/document/update";

    // Benötigte Variablen initialisieren
    Logger log = LoggerFactory.getLogger(this.getClass());
    String repositoryId;
    String docID;
    String updateDokURL;
    String AuthSessionID;
    List<String> headerValueList = new ArrayList<>();
    String updateBody = "";
    int responseCode;

    @Inject
    // Brauchen wir für die BaseURI
    TenantHolder tenantHolder;

    @GET
    @Produces({MediaType.TEXT_PLAIN, "text/plain"})
    public Response testRepo(@CookieParam("AuthSessionId") String authKey, @Context HttpHeaders headers, @QueryParam("docId") String docID, @QueryParam("repoId") String repositoryId, @QueryParam("chunkPath") String chunkPath) throws MalformedURLException, IOException {        
        updateDokURL = tenantHolder.getBaseUri() + "/dms/r/" + repositoryId + "/o2m/" + docID;

        // Senden des Updates
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(updateDokURL).openConnection();
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

        updateBody = "{\"alterationText\": \"updated file\", \"contentLocationUri\": \"" + chunkPath + "\" }";

        try(OutputStream os = httpConnection.getOutputStream()) {
            byte[] input = updateBody.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        responseCode = httpConnection.getResponseCode();
        if (responseCode == 200) {
            return Response.ok("Successfully updated").build();
        }
        else {
            return Response.status(responseCode).build();
        }
    }
}
