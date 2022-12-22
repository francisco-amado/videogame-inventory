package com.inventory.app.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Game implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final UUID gameId = UUID.randomUUID();
    @Embedded
    private Name name;
    @Enumerated(EnumType.STRING)
    private Console console;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private Region region;
    private String location;
    private String localBought;
    private Boolean wasGifted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection collection;

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

    public Game() {

    }

    public UUID getGameId() {
        return gameId;
    }

    public Name getName() {
        return name;
    }

    public Console getConsole() {
        return console;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Region getRegion() {
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

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLocalBought(String localBought) {
        this.localBought = localBought;
    }

    public void setWasGifted(Boolean wasGifted) {
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
                Objects.equals(wasGifted, game.wasGifted) &&
                Objects.equals(collection, game.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, name, console, releaseDate, region, location, localBought, wasGifted, collection);
    }
}
