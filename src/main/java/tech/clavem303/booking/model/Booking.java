package tech.clavem303.booking.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Booking extends PanacheEntityBase {

    private static final Map<BookingStatus, Set<BookingStatus>> BOOKING_STATE_MACHINE = new HashMap<>() {};

    static {
        BOOKING_STATE_MACHINE.put(BookingStatus.CREATED, Set.of(BookingStatus.CANCELED, BookingStatus.FINISHED));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id")
    Long vehicleId;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    protected Booking() {}

    public Booking(Long vehicleId, String customerName, LocalDate startDate, LocalDate endDate) {
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.CREATED;
    }

    public Long getId() {
        return id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus incomingStatus) {
        Set<BookingStatus> possibleStatus = BOOKING_STATE_MACHINE.get(this.status);

        if (possibleStatus.contains(incomingStatus)) {
            this.status = incomingStatus;
        } else {
            throw new IllegalStateException("Status transition from '" + this.status +
                    "' to '" + incomingStatus + "' is not allowed. Allowed: " + possibleStatus);
        }
    }
}
