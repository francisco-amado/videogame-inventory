package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Name;
import java.util.List;

public interface CollectionFactoryInterface {

    Collection createCollection(Name ownerName, List<Game> gameList);
}
