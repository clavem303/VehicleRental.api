package tech.clavem303.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import tech.clavem303.dto.CreateVehicleRequest;
import tech.clavem303.dto.VehicleResponse;
import tech.clavem303.model.Vehicle;

import java.util.List;

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

    @GET
    public Response getAll() {
        List<Vehicle> vehicles = Vehicle.listAll();
        List<VehicleResponse> vehicleResponseList = vehicles
                .stream()
                .map(VehicleResponse::new)
                .toList();
        return Response.ok(vehicleResponseList).build();
    }

}
