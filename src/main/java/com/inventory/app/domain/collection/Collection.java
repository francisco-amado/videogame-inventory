package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.OwnerId;
import java.util.List;
import java.util.Objects;

public class Collection {

    OwnerId ownerId;
    List<Game> gameList;

    public Collection(OwnerId ownerId) {

        this.ownerId = ownerId;
    }

    public Collection(OwnerId ownerId, List<Game> gameList) {

        this.ownerId = ownerId;
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return Objects.equals(ownerId, that.ownerId) && Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, gameList);
    }
}
