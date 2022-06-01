package com.dvelop.archetype.plugins.http.resources;

//import com.dvelop.sdk.idp.filter.IDPRole;

import com.dvelop.archetype.plugins.http.dto.FeatureDto;
import com.dvelop.archetype.plugins.http.template.AppInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(Features.PATH)
public class Features {
    public static final String PATH = "/features";

    public static class FeaturesDto {
        FeatureDto[] features;

        public FeatureDto[] getFeatures() {
            return features;
        }

        public void setFeatures(FeatureDto[] features) {
            this.features = features;
        }
    }

    @Inject
    AppInfo appInfo;

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response getFeatures() {
        System.out.println("getFeatures called");

        FeaturesDto dto = new FeaturesDto();
        dto.features = new FeatureDto[]{
                new FeatureDto(
                        "Vacation management",
                        "Manage vacation requests",
                        "Apply for vacation and approve the vacation requests of your employees",
                        "Your vacation requests and the requests of your employees",
                        "/hackathon-demo/requests",
                        "#adff2f",
                        "https://cdn-icons-png.flaticon.com/512/2890/2890529.png"
                )
        };

        return Response.ok(dto).build();
    }
}
