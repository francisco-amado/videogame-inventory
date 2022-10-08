package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CollectionFactoryInterface {

    Collection createCollection(UUID ownerId, List<Game> gameList);
}
