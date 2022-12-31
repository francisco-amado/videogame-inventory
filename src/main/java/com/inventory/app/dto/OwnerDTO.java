package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class OwnerDTO {

    private @Getter @Setter String userName;
    private @Getter @Setter String email;
    private @Getter @Setter String password;
}
