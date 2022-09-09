package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.OwnerId;

import java.util.List;

public class CollectionFactory implements CollectionFactoryInterface{

    public Collection createCollection(OwnerId ownerId, List<Game> gameList) {

        return new Collection(ownerId, gameList);
    }
}
