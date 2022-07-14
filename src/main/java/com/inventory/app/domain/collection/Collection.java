package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Name;
import java.util.List;
import java.util.Objects;

public class Collection {

    Name ownerName;
    List<Game> gameList;

    public Collection(Name ownerName) {

        this.ownerName = ownerName;
    }

    public Collection(Name ownerName, List<Game> gameList) {

        this.ownerName = ownerName;
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return ownerName.equals(that.ownerName) && Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerName, gameList);
    }
}
