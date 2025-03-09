package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @NotBlank(message = "Feedback text is mandatory")
    private String feedbackText;

    // @NotNull(message = "Date is mandatory")
    private LocalDate date;

    /**
     * The user associated with the feedback.
     * Many-to-One relationship with the User entity.
     * Non-nullable, as every feedback must be associated with a user.
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    /**
     * The driver associated with the feedback.
     * Many-to-One relationship with the Driver entity.
     * Nullable, as feedback may not be associated with a specific driver.
     */
    @ManyToOne
    @JoinColumn(name = "driverId", nullable = true)
    private Driver driver;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotNull(message = "Rating is mandatory")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
}
