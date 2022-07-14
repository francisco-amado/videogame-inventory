package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import java.util.List;
import java.util.Objects;

public class Collection {

    String owner;
    List<Game> gameList;

    public Collection(String owner) {

        this.owner = owner;
    }

    public Collection(String owner, List<Game> gameList) {

        this.owner = owner;
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return owner.equals(that.owner) && Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, gameList);
    }
}
