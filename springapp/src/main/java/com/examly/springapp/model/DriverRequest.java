package com.examly.springapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DriverRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverRequestId;

    /**
     * The user associated with the driver request.
     * Many-to-One relationship with the User entity.
     * Non-nullable, as every driver request must be associated with a user.
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    /**
     * The driver assigned to the request.
     * Many-to-One relationship with the Driver entity.
     * Nullable, as a request may not yet have an assigned driver.
     */
    @ManyToOne
    @JoinColumn(name = "driverId", nullable = true)
    private Driver driver;

    @NotNull(message = "Request date is mandatory")
    private LocalDate requestDate;

    private String status;

    @NotNull(message = "Trip date is mandatory")
    private LocalDate tripDate;

    private LocalTime timeSlot;

    @NotBlank(message = "Pickup location is mandatory")
    private String pickupLocation;

    @NotBlank(message = "Drop location is mandatory")
    private String dropLocation;

    @NotBlank(message = "Estimated duration is mandatory")
    private String estimatedDuration;

    @DecimalMin(value = "0.0", inclusive = false, message = "Payment amount must be greater than 0")
    private Double paymentAmount;

    private String comments;

    private LocalTime actualDropTime;

    private LocalDate actualDropDate;

    private String actualDuration;

}
