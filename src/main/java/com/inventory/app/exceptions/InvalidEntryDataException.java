package com.inventory.app.exceptions;

public class InvalidEntryDataException extends RuntimeException{

    public InvalidEntryDataException(String errorMessage) {
        super(errorMessage);
    }
}
