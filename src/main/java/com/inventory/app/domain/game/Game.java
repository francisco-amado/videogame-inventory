package com.inventory.app.domain.game;

import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Region;
import java.time.LocalDate;

public class Game {

    String name;
    Console console;
    LocalDate releaseDate;
    Region region;

    public Game(String name, Console console, LocalDate releaseDate, Region region) {

        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
    }
}
