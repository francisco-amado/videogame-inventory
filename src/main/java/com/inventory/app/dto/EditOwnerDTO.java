package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class EditOwnerDTO {

    private @Getter @Setter String userName;
}
