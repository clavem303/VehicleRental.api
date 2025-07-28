package tech.clavem303.booking.dto;

import tech.clavem303.booking.model.Booking;
import tech.clavem303.booking.model.BookingStatus;

import java.time.LocalDate;

public record BookingResponse(
        Long id,
        Long vehicleId,
        String customerName,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatus status) {

        public BookingResponse (Booking booking) {
            this(
                    booking.getId(),
                    booking.getVehicleId(),
                    booking.getCustomerName(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getStatus()
            );
        }
}
