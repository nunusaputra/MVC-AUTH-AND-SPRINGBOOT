package com.example.job.utils;

public class ErrorHandling extends RuntimeException {
    public ErrorHandling(String message) {
        super(message);
    }
}
