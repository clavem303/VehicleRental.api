package tech.clavem303.vehicle.dto;

public record CreateVehicleRequest(String brand, String model, Integer year, String engine) {
}
