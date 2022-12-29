package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class OwnerDTO {

    private @Getter @Setter String userName;
    private @Getter @Setter String email;
    private @Getter @Setter String password;
}
