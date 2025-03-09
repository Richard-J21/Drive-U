package com.examly.springapp.exceptions;

public class DriverDeletionException extends Exception {

    public DriverDeletionException (){
        super();
    }

    public DriverDeletionException(String message){
        super(message);
    }
}
