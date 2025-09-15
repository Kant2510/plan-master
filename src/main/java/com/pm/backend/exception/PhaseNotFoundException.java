package com.pm.backend.exception;

public class PhaseNotFoundException extends RuntimeException {
    public PhaseNotFoundException(String message) {
        super(message);
    }
}
