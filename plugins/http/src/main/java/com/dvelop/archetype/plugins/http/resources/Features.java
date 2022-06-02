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
                        "pdf cleaner",
                        "for usage go to a pdf document context-menu",
                        "demo app d-velop hackathon 19.07.2022-20.07.2022",
                        "remove blank sites from a selected pdf document in d.velop documents",
                        "/hackathon-demo/",
                        "#adff2f",
                        "https://cdn-icons-png.flaticon.com/512/2890/2890529.png"
                )
        };

        return Response.ok(dto).build();
    }
}
