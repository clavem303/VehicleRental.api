package tech.clavem303.booking.dto;

import tech.clavem303.booking.model.BookingStatus;

public record UpdateBookingStatusRequest(BookingStatus status) {}