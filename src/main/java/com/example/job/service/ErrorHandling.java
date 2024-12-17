package com.example.job.service;

public class ErrorHandling extends RuntimeException {
    public ErrorHandling(String message) {
        super(message);
    }
}
