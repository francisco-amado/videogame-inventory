package com.inventory.app.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GameDTO {

    private String name;
    private String console;
    private LocalDate releaseDate;
    private String region;

    public String getName() {
        return name;
    }

    public String getConsole() {
        return console;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getRegion() {
        return region;
    }
}
