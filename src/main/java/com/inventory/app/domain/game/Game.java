package com.inventory.app.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Game extends RepresentationModel<Game> implements Serializable {

    @Id
    @Column(name = "uuid")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final @Getter UUID gameId = UUID.randomUUID();

    @Embedded
    @NotNull
    private @Getter @Setter Name name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private @Getter @Setter Console console;

    @NotNull
    private @Getter @Setter LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private @Getter @Setter Region region;

    private @Getter @Setter String location;

    private @Getter @Setter String localBought;

    private @Getter @Setter Boolean wasGifted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection", referencedColumnName = "uuid")
    @JsonIgnore
    private @Getter @Setter Collection collection;

    private static final long serialVersionUID = 2L;

    public Game(Name name, Console console, LocalDate releaseDate,
                Region region, String location, String localBought, Boolean wasGifted) {

        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
        this.location = location;
        this.localBought = localBought;
        this.wasGifted = wasGifted;
    }

    public Game(Name name, Console console, Region region, LocalDate releaseDate) {
        this.name = name;
        this.console = console;
        this.region = region;
        this.releaseDate = releaseDate;
    }

    public Game() {}

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
                Objects.equals(collection, game.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, name, console, releaseDate, region, location, localBought, wasGifted, collection);
    }
}
