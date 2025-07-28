package tech.clavem303.vehicle.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import tech.clavem303.vehicle.dto.CreateVehicleRequest;
import tech.clavem303.vehicle.dto.UpdateVehicleStatusRequest;
import tech.clavem303.vehicle.dto.VehicleResponse;
import tech.clavem303.vehicle.model.Vehicle;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/vehicles")
public class VehicleResource {

    @POST
    @Transactional
    public Response create(CreateVehicleRequest request) {
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

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Vehicle> vehicleOptional = Vehicle.findByIdOptional(id);

        if (vehicleOptional.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(new VehicleResponse(vehicleOptional.get())).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateStatus(@PathParam("id") Long id, UpdateVehicleStatusRequest request) {
        Vehicle vehicle = Vehicle.findById(id);

        if (vehicle == null) return Response.status(Response.Status.NOT_FOUND).build();

        try {
            vehicle.setStatus(request.status());
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok(new VehicleResponse(vehicle)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Vehicle vehicle = Vehicle.findById(id);

        if (vehicle == null) return Response.status(404).build();

        if (vehicle.isRented()) return Response.status(Response.Status.CONFLICT).build();

        vehicle.delete();

        return Response.noContent().build();
    }
}
