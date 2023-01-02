package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GameDTO {

    private @Getter @Setter String name;
    private @Getter @Setter String console;
    private @Getter @Setter LocalDate releaseDate;
    private @Getter @Setter String region;
}
