package tech.clavem303.vehicle.dto;

import tech.clavem303.vehicle.model.Vehicle;
import tech.clavem303.vehicle.model.VehicleStatus;

public record VehicleResponse(
        Long id,
        String brand,
        String model,
        int year,
        String engine,
        VehicleStatus status,
        String carTitle) {

    public VehicleResponse(Vehicle vehicle) {
        this(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getEngine(),
                vehicle.getStatus(),
                "%s %s %s".formatted(
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getEngine()
                )
        );
    }
}
