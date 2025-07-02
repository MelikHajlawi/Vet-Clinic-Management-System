package com.example.MiniProjetSoa.exceptions;

public class VaccinNotFoundException extends RuntimeException {
    public VaccinNotFoundException(String message) {
        super(message);
    }
}
