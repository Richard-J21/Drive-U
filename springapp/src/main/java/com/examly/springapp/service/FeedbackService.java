package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;

import jakarta.persistence.EntityNotFoundException;

public interface FeedbackService {

    Feedback createFeedback(FeedbackDTO feedbackDto) throws Exception;

    Feedback getFeedbackById(Long feedbackId) throws EntityNotFoundException;

    List<Feedback> getAllFeedbacks();

    String deleteFeedback(Long feedbackId) throws EntityNotFoundException;

    List<Feedback> getFeedbacksByUserId(Long userId) throws EntityNotFoundException;

}
