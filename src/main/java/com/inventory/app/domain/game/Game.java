package com.inventory.app.domain.game;

import com.inventory.app.domain.valueobjects.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "game")
public class Game implements Serializable {

    @EmbeddedId
    GameId gameId;
    @Embedded
    Name name;
    @Enumerated(EnumType.STRING)
    Console console;
    LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    Region region;
    String location;
    String localBought;
    Boolean wasGifted;
    @Embedded
    CollectionId collectionId;

    private static final long serialVersionUID = 2L;

    public Game(GameId gameId, Name name, Console console, LocalDate releaseDate,
                Region region, String location, String localBought, Boolean wasGifted, CollectionId collectionId) {

        this.gameId = gameId;
        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
        this.location = location;
        this.localBought = localBought;
        this.wasGifted = wasGifted;
        this.collectionId = collectionId;
    }

    public Game(GameId gameId, Name name, Console console, Region region, LocalDate releaseDate) {

        this.gameId = gameId;
        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
    }

    public Game() {

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
                Objects.equals(wasGifted, game.wasGifted) &&
                Objects.equals(collectionId, game.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, name, console, releaseDate, region,
                location, localBought, wasGifted, collectionId);
    }
}
