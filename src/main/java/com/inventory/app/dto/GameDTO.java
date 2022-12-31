package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ToString
public class GameDTO {

    private @Getter @Setter String name;
    private @Getter @Setter String console;
    private @Getter @Setter LocalDate releaseDate;
    private @Getter @Setter String region;
}
