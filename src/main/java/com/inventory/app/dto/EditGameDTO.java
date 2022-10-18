package com.inventory.app.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EditGameDTO {

    String name;
    String console;
    LocalDate releaseDate;
    String region;
    String location;
    String localBought;
    Boolean wasGifted;

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

    public String getLocation() {
        return location;
    }

    public String getLocalBought() {
        return localBought;
    }

    public Boolean getWasGifted() {
        return wasGifted;
    }
}
