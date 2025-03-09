package com.examly.springapp.service;
 
import java.time.LocalDate;
import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.examly.springapp.exceptions.DuplicateFeedbackException;
import com.examly.springapp.model.Driver;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.DriverRepo;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.UserRepo;
 
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
 
    // Repository to handle feedback-related database operations
    private final FeedbackRepo feedbackRepo;
 
    private final UserRepo userRepo;
   
    private final DriverRepo driverRepo;
 
    /**
     * Creates a new Feedback entry.
     * @param feedback The Feedback entity to be created.
     * @return The created Feedback entity.
     * @throws DuplicateFeedbackException If feedback from the same user for the same driver already exists.
     */
    @Override
    public Feedback createFeedback(FeedbackDTO feedbackDto) throws Exception {
        Feedback existingFeedback = feedbackRepo.getFeedbackByUserAndDriverId(feedbackDto.getUserId(), feedbackDto.getDriverId())
                .orElse(null);
        if (existingFeedback != null) {
            throw new DuplicateFeedbackException("Feedback for current Driver already exists!");
        }
 
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackDto.getFeedbackText());
        feedback.setCategory(feedbackDto.getCategory());
        feedback.setRating(feedbackDto.getRating());
        feedback.setDate(LocalDate.now());
 
        User user = userRepo.findById(feedbackDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User is not Found!"));
        Driver driver = driverRepo.findById(feedbackDto.getDriverId()).orElseThrow(() -> new EntityNotFoundException("Driver is not Found!"));
 
        feedback.setUser(user);
        feedback.setDriver(driver);
        return feedbackRepo.save(feedback);
 
    }
 
    /**
     * Retrieves a Feedback entry by its ID.
     * @param feedbackId The ID of the Feedback to be retrieved.
     * @return The Feedback entity with the given ID.
     * @throws EntityNotFoundException If no Feedback is found with the given ID.
     */
    @Override
    public Feedback getFeedbackById(Long feedbackId) throws EntityNotFoundException {
 
        Feedback feedback = feedbackRepo.findById(feedbackId).orElse(null);
        if (feedback == null) {
            throw new EntityNotFoundException("The Feedback with the feedback Id " + feedbackId + " does not exists!");
        }
        return feedback;
    }
 
    /**
     * Retrieves all Feedback entries.
     * @return A list of all Feedback entities.
     */
    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }
 
    /**
     * Deletes a Feedback entry by its ID.
     * @param feedbackId The ID of the Feedback to be deleted.
     * @return The deleted Feedback entity.
     * @throws EntityNotFoundException If no Feedback is found with the given ID.
     */
    @Override
    public String deleteFeedback(Long feedbackId) throws EntityNotFoundException {
        feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with " + feedbackId + " not found."));
        feedbackRepo.deleteById(feedbackId);
        return "Feedback deleted successfully";
    }
 
    /**
     * Retrieves all Feedback entries associated with a specific user ID.
     * @param userId The user ID to search for.
     * @return A list of Feedback entities with the given user ID.
     * @throws EntityNotFoundException If no Feedback is found with the given user ID.
     */
    @Override
    public List<Feedback> getFeedbacksByUserId(Long userId) throws EntityNotFoundException {
        return feedbackRepo.getFeedbacksByUserId(userId);
    }
 
}
 
 