package com.examly.springapp.exceptions;

public class DuplicateFeedbackException extends Exception {

    public DuplicateFeedbackException() {
        super();
    }

    public DuplicateFeedbackException(String message) {
        super(message);
    }
}
