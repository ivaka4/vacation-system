package com.example.vacation.exception;

public abstract class CustomBaseException extends RuntimeException {
    public CustomBaseException(String message){
        super(message);
    }
}
