package com.inventory.app.dto;

import com.inventory.app.domain.owner.Owner;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class OwnerDTO extends RepresentationModel<OwnerDTO> implements Serializable {

    private static final long serialVersionUID = 3L;

    private @Getter @Setter UUID ownerId;
    private @Getter @Setter String userName;
    private @Getter @Setter String email;
}
