package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordDTO {

    @Getter @Setter String newPassword;
    @Getter @Setter String oldPassword;
    @Getter @Setter String confirmPassword;
    @Getter @Setter String email;
}
