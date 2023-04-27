package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component

public class TokenDTO extends RepresentationModel<TokenDTO> implements Serializable {

    private static final long serialVersionUID = 4L;

    private @Getter @Setter String token;
}
