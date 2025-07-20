package tech.clavem303.resources;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/vehicles")
public class VehicleResource {

    @POST
    public Response create(){
        return Response.status(Response.Status.CREATED)
                .entity("New vehicle created!")
                .build();
    }
}
