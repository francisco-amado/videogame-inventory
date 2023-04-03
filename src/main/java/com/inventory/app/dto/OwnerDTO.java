package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class OwnerDTO extends RepresentationModel<OwnerDTO> implements Serializable {

    private @Getter @Setter String userName;
    private @Getter @Setter String email;
}
