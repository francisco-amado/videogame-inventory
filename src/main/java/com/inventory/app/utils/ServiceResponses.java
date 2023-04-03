package com.inventory.app.utils;

import lombok.Getter;

public class ServiceResponses {

    @Getter private final static String OWNER_NOT_FOUND = "The requested owner does not exist";
    @Getter private final static String COLLECTION_NOT_FOUND = "The requested collection does not exist";
    @Getter private final static String INVALID_ENTRY_DATA = "Invalid entry data";
    @Getter private final static String INVALID_USER_DETAILS = "Invalid user details";
    @Getter private final static String INVALID_PASSWORD = "Invalid password";
    @Getter private final static String GAME_NOT_FOUND = "The requested game does not exist";
    @Getter private final static String CANNOT_DELETE_GAME = "Game cannot be deleted if it belongs to a collection";
    @Getter private final static String TOKEN_RESPONSE = "Confirmed";
    @Getter private final static String TOKEN_NOT_FOUND = "Token not found";
    @Getter private final static String TOKEN_EXPIRED = "Token expired";
    @Getter private final static String EMAIL_CONFIRMED = "E-mail already confirmed";
    @Getter private final static String EMAIL_LINK = "http://localhost:8080/api/v1/owners/confirm?token=";
}
