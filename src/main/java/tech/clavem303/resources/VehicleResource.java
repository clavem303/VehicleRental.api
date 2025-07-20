package tech.clavem303.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import tech.clavem303.dto.CreateVehicleRequest;
import tech.clavem303.model.Vehicle;

@Path("/api/v1/vehicles")
public class VehicleResource {

    @POST
    @Transactional
    public Response create(CreateVehicleRequest request){
        Vehicle vehicle = new Vehicle(request.model(), request.year(), request.engine(), request.brand());

        vehicle.persist();

        return Response.status(Response.Status.CREATED)
                .entity("New vehicle created!")
                .build();
    }


}
