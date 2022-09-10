package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    CollectionRepository collectionRepository;
    CollectionFactoryInterface collectionFactoryInterface;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {

        this.collectionRepository = collectionRepository;
    }

    public Collection create(CollectionId collectionId, OwnerId ownerId, List<Game> gameList) {

        Collection newCollection = collectionFactoryInterface.createCollection(collectionId, ownerId, gameList);

        return collectionRepository.save(newCollection);
    }

    public Optional<Collection> findById(CollectionId collectionId) {

        return collectionRepository.findById(collectionId);
    }

    public Optional<Collection> addGame(Game game, CollectionId collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        collection.ifPresent(value -> value.getGameList().add(game));

        return collection;
    }
}
