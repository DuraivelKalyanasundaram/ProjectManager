package com.cognizant.fse.exception;

public class EmployeeExistsException extends Exception {
    String message;

    public EmployeeExistsException(String message) {
        super(message);
    }
}
