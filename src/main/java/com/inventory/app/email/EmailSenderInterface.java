package com.inventory.app.email;

public interface EmailSenderInterface {

    void send(String to, String email);

    String buildEmail(String userName, String link);
}
