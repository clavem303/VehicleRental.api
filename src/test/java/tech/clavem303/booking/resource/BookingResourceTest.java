package tech.clavem303.booking.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
class BookingResourceTest {
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now();

    @Test
    void mustCreateBooking() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "vehicleId": 1,
                          "customerName": "Marcondes",
                          "startDate": "%s",
                          "endDate": "%s"
                        }
                        """.formatted(startDate, endDate.plusDays(7))
                )
                .post("/api/v1/bookings")
                .then()
                .statusCode(201);
    }

    @Test
    void mustNotCreateBookingInThePast() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "vehicleId": 1,
                          "customerName": "Marcondes",
                          "startDate": "2024-10-28",
                          "endDate": "2025-10-29"
                        }
                      """.formatted(startDate, endDate.minusDays(7))
                )
                .post("/api/v1/bookings")
                .then()
                .statusCode(400);
    }
}