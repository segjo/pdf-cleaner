package com.dvelop.archetype.plugins.http.resources;

//import com.dvelop.sdk.idp.filter.IDPRole;

import com.dvelop.archetype.plugins.http.dto.DmsobjectextensionDto;
import com.dvelop.archetype.plugins.http.template.AppInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(Dmsobjectextensions.PATH)
public class Dmsobjectextensions {
    public static final String PATH = "/dmsobjectextensions";

    public static class DmsobjectextensionsDto {
        DmsobjectextensionDto[] dmsobjectextensions;

        public DmsobjectextensionDto[] getDmsobjectextensions() {
            return dmsobjectextensions;
        }

        public void setDmsobjectextensions(DmsobjectextensionDto[] dmsobjectextensions) {
            this.dmsobjectextensions = dmsobjectextensions;
        }
    }

    @Inject
    AppInfo appInfo;

    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response getDmsobjectextensions() {

        DmsobjectextensionsDto dto = new DmsobjectextensionsDto();
        dto.dmsobjectextensions = new DmsobjectextensionDto[]{
                new DmsobjectextensionDto(
                        "hackathon-demo.open",
                        "DmsObjectDetailsContextAction",
                        "/" + appInfo.getName() + "?id={dmsobject.property_document_id}",
                        "dv-search"
                )
        };

        return Response.ok(dto).build();
    }
}
