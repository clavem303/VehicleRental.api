package tech.clavem303.booking.resource;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.clavem303.booking.dto.BookingResponse;
import tech.clavem303.booking.dto.CreateBookingRequest;
import tech.clavem303.booking.dto.UpdateBookingStatusRequest;
import tech.clavem303.booking.model.Booking;
import tech.clavem303.vehicle.resource.VehicleResource;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    @POST
    @Transactional
    public Response create(@Valid CreateBookingRequest request) {
        Booking booking = new Booking(request.vehicleId(), request.customerName(), request.startDate(), request.endDate());

        booking.persist();

        return Response.status(Response.Status.CREATED)
                .entity("New booking created!")
                .build();
    }

    @GET
    public Response getAll() {
        List<Booking> bookings = Booking.listAll();
        List<BookingResponse> bookingResponseList = bookings
                .stream()
                .map(BookingResponse::new)
                .toList();
        return Response.ok(bookingResponseList).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Booking> bookingOptional = Booking.findByIdOptional(id);

        if (bookingOptional.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(new BookingResponse(bookingOptional.get())).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateStatus(@PathParam("id") Long id, UpdateBookingStatusRequest request) {
        Booking booking = Booking.findById(id);

        if (booking == null) return Response.status(Response.Status.NOT_FOUND).build();

        try {
            booking.setStatus(request.status());
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok(new BookingResponse(booking)).build();
    }
}
