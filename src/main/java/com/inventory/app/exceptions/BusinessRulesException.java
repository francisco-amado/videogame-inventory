package com.inventory.app.exceptions;

public class BusinessRulesException extends RuntimeException {

    public BusinessRulesException(String errorMessage) {
        super(errorMessage);
    }
}

