package com.examly.springapp.exceptions;

public class DriverRequestDeletionException extends Exception{

    public DriverRequestDeletionException(){
        super();
    }

    public DriverRequestDeletionException(String message){
        super(message);
    }
}
