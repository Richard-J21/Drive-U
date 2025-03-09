package com.examly.springapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private String feedbackText;
    private Long userId;
    private Long driverId;
    private String category;
    private Integer rating;
}
 
 
 