package com.examly.springapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(DriverDeletionException.class)
   public ResponseEntity<String> driverDeletionExceptionHandler(DriverDeletionException exception) {
      return ResponseEntity.status(409).body(exception.getMessage());
   }

   @ExceptionHandler(DuplicateDriverException.class)
   public ResponseEntity<String> duplicateDriverExceptionHandler(DuplicateDriverException exception) {
      return ResponseEntity.status(409).body(exception.getMessage());
   }

   @ExceptionHandler(DriverRequestDeletionException.class)
   public ResponseEntity<String> driverRequestDeletionExceptionHandler(DriverRequestDeletionException exception) {
      return ResponseEntity.status(404).body(exception.getMessage());
   }

   @ExceptionHandler(DuplicateDriverRequestException.class)
   public ResponseEntity<String> duplicateDriverRequestExceptionHandler(DuplicateDriverRequestException exception) {
      return ResponseEntity.status(409).body(exception.getMessage());
   }

   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
       Map<String, String> errors = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldName = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
       });
       return ResponseEntity.badRequest().body(errors);
   }

   @ExceptionHandler(DuplicateFeedbackException.class)
   public ResponseEntity<String> duplicateFeedbackExceptionHandler(DuplicateFeedbackException exception) {
      return ResponseEntity.status(409).body(exception.getMessage());
   }

   @ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<String> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
      return ResponseEntity.status(404).body(exception.getMessage());
   }

   // Bokya
   @ExceptionHandler(DuplicateUserException.class)
   public ResponseEntity<String> duplicateUserExceptionHandler(DuplicateUserException exception) {
      return ResponseEntity.status(409).body(exception.getMessage());
   }


}
