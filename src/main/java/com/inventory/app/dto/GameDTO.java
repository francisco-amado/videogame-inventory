package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class GameDTO extends RepresentationModel<GameDTO> implements Serializable {

    private static final long serialVersionUID = 2L;

    private @Getter @Setter UUID gameId;
    private @Getter @Setter String name;
    private @Getter @Setter String console;
    private @Getter @Setter LocalDate releaseDate;
    private @Getter @Setter String region;
    private @Getter @Setter String location;
    private @Getter @Setter String localBought;
    private @Getter @Setter Boolean wasGifted;
}
