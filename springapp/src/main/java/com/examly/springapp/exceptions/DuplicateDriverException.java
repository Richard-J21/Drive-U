package com.examly.springapp.exceptions;

public class DuplicateDriverException extends Exception{

    public DuplicateDriverException(){
        super();
    }

    public DuplicateDriverException(String message){
        super(message);
    }
    
}
