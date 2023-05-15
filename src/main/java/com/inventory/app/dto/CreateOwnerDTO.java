package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class CreateOwnerDTO implements Serializable {

    private static final long serialVersionUID = 10L;

    private @Getter @Setter String userName;
    private @Getter @Setter String email;
    private @Getter @Setter String password;
    private @Getter @Setter String confirmPassword;

    public CreateOwnerDTO() {}
}
