package com.examly.springapp.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequestDTO {
    private Long userId;
    private Long driverId;
    private LocalDate tripDate;
    private String timeSlot;
    private String pickupLocation;
    private String dropLocation;
    private String estimatedDuration;
    private String comments;
}
