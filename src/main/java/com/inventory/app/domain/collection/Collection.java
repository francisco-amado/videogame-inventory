package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.OwnerId;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "collection")
public class Collection implements Serializable {

    @EmbeddedId
    CollectionId collectionId;
    @Embedded
    OwnerId ownerId;
    @OneToMany(cascade = CascadeType.ALL)
    List<Game> gameList;

    private static final long serialVersionUID = 1L;

    public Collection(CollectionId collectionId, OwnerId ownerId, List<Game> gameList) {

        this.collectionId = collectionId;
        this.ownerId = ownerId;
        this.gameList = gameList;
    }

    public Collection() {

    }

    public List<Game> getGameList() {
        return gameList;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return Objects.equals(collectionId, that.collectionId) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, ownerId, gameList);
    }
}
