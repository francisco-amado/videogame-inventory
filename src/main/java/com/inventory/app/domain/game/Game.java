package com.inventory.app.domain.game;

import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.GameId;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import java.time.LocalDate;
import java.util.Objects;

public class Game {

    GameId gameId;
    Name name;
    Console console;
    LocalDate releaseDate;
    Region region;
    String location;
    String localBought;
    Boolean wasGifted;

    public Game(GameId gameId, Name name, Console console, LocalDate releaseDate,
                Region region, String location, String localBought, Boolean wasGifted) {

        this.gameId = gameId;
        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
        this.location = location;
        this.localBought = localBought;
        this.wasGifted = wasGifted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gameId, game.gameId) &&
                Objects.equals(name, game.name) &&
                Objects.equals(console, game.console) &&
                Objects.equals(releaseDate, game.releaseDate) &&
                Objects.equals(region, game.region) &&
                Objects.equals(location, game.location) &&
                Objects.equals(localBought, game.localBought) &&
                Objects.equals(wasGifted, game.wasGifted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, name, console, releaseDate, region, location, localBought, wasGifted);
    }
}
