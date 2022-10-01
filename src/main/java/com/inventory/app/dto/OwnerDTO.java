package com.inventory.app.dto;

import org.springframework.stereotype.Component;

@Component
public class OwnerDTO {

    private String userName;
    private String email;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
