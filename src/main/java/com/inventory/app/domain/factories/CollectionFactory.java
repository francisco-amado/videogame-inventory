package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.OwnerId;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollectionFactory implements CollectionFactoryInterface {

    public Collection createCollection(CollectionId collectionId, OwnerId ownerId, List<Game> gameList) {

        return new Collection(collectionId, ownerId, gameList);
    }
}
