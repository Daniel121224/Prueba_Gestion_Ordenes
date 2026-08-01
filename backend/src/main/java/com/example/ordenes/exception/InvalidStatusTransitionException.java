package com.example.ordenes.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String currentStatus, String newStatus) {
        super("Invalid status transition from " + currentStatus + " to " + newStatus);
    }
}