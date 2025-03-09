package com.examly.springapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a driver entity with details like name, license number,
 * experience, contact information, availability, address, vehicle type,
 * hourly rate, and image.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @NotBlank(message = "Driver name is mandatory")
    @Size(min = 2, max = 50, message = "Driver name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]{2,50}$", message = "Driver name must only contain letters and spaces")
    private String driverName;

    @NotBlank(message = "License number is mandatory")
    @Pattern(regexp = "^[A-Za-z]{2}[0-9]{13}$", message = "License number must start with two alphabetic characters followed by thirteen numeric characters")
    @Column(unique = true)
    private String licenseNumber;

    @NotNull(message = "Experience years is mandatory")
    @Min(value = 0, message = "Experience years should not be less than 0")
    @Max(value = 50, message = "Experience years should not be more than 50")
    private Integer experienceYears;

    @NotBlank(message = "Contact number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    @NotBlank(message = "Availability status is mandatory")
    private String availabilityStatus;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @NotBlank(message = "Vehicle type is mandatory")
    private String vehicleType;

    @NotNull(message = "Hourly rate is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be greater than 0")
    private Double hourlyRate;

    /**
     * The image of the driver.
     * Stored as a large binary object (LONGBLOB).
     * Nullable.
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = true)
    private String image;
}
