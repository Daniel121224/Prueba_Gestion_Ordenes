package com.example.ordenes.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Status '" + status + "' is not recognized");
    }
}