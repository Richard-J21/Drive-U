package com.examly.springapp.exceptions;

public class DuplicateDriverRequestException extends Exception {
    public DuplicateDriverRequestException() {
        super();
    }

    public DuplicateDriverRequestException(String message) {
        super(message);
    }

}
