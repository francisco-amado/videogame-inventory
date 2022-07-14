package com.inventory.app.domain.game;

import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import java.time.LocalDate;
import java.util.Objects;

public class Game {

    Name name;
    Console console;
    LocalDate releaseDate;
    Region region;
    String location;
    String localBought;

    public Game(Name name, Console console, LocalDate releaseDate, Region region, String location, String localBought) {

        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
        this.location = location;
        this.localBought = localBought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return name.equals(game.name) &&
                console.equals(game.console) &&
                releaseDate.equals(game.releaseDate) &&
                region.equals(game.region) &&
                location.equals(game.location) &&
                localBought.equals(game.localBought);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, console, releaseDate, region, location, localBought);
    }
}
