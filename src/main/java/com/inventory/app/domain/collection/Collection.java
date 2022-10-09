package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "collection")
public class Collection implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID collectionId = UUID.randomUUID();
    @OneToOne
    @JoinColumn(name = "ownerId", unique = true,  nullable = false)
    Owner owner;
    @OneToMany
    @JoinColumn(name = "collectionId")
    List<Game> gameList;

    private static final long serialVersionUID = 1L;

    public Collection(Owner owner, List<Game> gameList) {

        this.owner = owner;
        this.gameList = gameList;
    }

    public Collection() {

    }

    public List<Game> getGameList() {
        return gameList;
    }

    public UUID getCollectionId() {
        return collectionId;
    }

    public Owner getOwner() {
        return owner;
    }

    public void addGameToList(Game game) {

        gameList.add(game);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return Objects.equals(collectionId, that.collectionId) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, owner, gameList);
    }
}
