package com.examly.springapp.controller;
 
import java.util.List;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.exceptions.DuplicateFeedbackException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;
import com.examly.springapp.service.FeedbackService;
 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
@Tag(name = "FeedbackController", description = "Feedback controller used for CRUD operations")
public class FeedbackController {
 
    private final FeedbackService feedbackService;
 
    /**
     * Adds a new Feedback.
     *
     * @param feedback - the Feedback object to add.
     * @return ResponseEntity containing the created Feedback.
     * @throws DuplicateFeedbackException if a duplicate Feedback is detected.
     */
    @Operation(
        summary = "Add new Feedback",
        description = "Adds a new Feedback to the system. Throws DuplicateFeedbackException if a duplicate Feedback is detected."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Feedback created successfully"),
        @ApiResponse(responseCode = "409", description = "Duplicate Feedback detected")
    })
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody FeedbackDTO feedbackDto) throws Exception {
        return ResponseEntity.status(201).body(feedbackService.createFeedback(feedbackDto));
    }
 
    /**
     * Retrieves a Feedback by its ID.
     *
     * @param feedbackId - the ID of the Feedback to retrieve.
     * @return ResponseEntity containing the Feedback.
     * @throws EntityNotFoundException if the Feedback is not found.
     */
    @Operation(
        summary = "Retrieve Feedback by ID",
        description = "Retrieves a Feedback by its ID. Throws EntityNotFoundException if the Feedback is not found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedback retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable long feedbackId) throws EntityNotFoundException {
        return ResponseEntity.status(200).body(feedbackService.getFeedbackById(feedbackId));
    }
 
    /**
     * Retrieves all Feedbacks.
     *
     * @return ResponseEntity containing a list of all Feedbacks.
     */
    @Operation(
        summary = "Retrieve all Feedbacks",
        description = "Retrieves a list of all Feedbacks in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All Feedbacks retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.status(200).body(feedbackService.getAllFeedbacks());
    }
 
    /**
     * Deletes an existing Feedback.
     *
     * @param feedbackId - the ID of the Feedback to delete.
     * @return ResponseEntity containing the deleted Feedback.
     * @throws EntityNotFoundException if the Feedback to delete is not found.
     */
    @Operation(
        summary = "Delete Feedback",
        description = "Deletes an existing Feedback by its ID. Throws EntityNotFoundException if the Feedback to delete is not found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedback deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) throws EntityNotFoundException {
        return ResponseEntity.status(200).body(feedbackService.deleteFeedback(feedbackId));
    }
 
    /**
     * Retrieves Feedbacks by User ID.
     *
     * @param userId - the ID of the user to retrieve Feedbacks for.
     * @return ResponseEntity containing a list of Feedbacks for the specified user.
     * @throws EntityNotFoundException if the Feedbacks are not found.
     */
    @Operation(
        summary = "Retrieve Feedbacks by User ID",
        description = "Retrieves Feedbacks for a specified user by their ID. Throws EntityNotFoundException if the Feedbacks are not found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Feedbacks not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(200).body(feedbackService.getFeedbacksByUserId(userId));
    }
 
}
 
 