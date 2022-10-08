package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionFactoryInterface collectionFactoryInterface;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository,
                             CollectionFactoryInterface collectionFactoryInterface) {

        this.collectionRepository = collectionRepository;
        this.collectionFactoryInterface = collectionFactoryInterface;
    }

    public Collection createCollection(UUID ownerId, List<Game> gameList) {

        Collection newCollection = collectionFactoryInterface.createCollection(ownerId, gameList);

        return collectionRepository.save(newCollection);
    }

    public Optional<Collection> findById(UUID collectionId) {

        return collectionRepository.findById(collectionId);
    }

    public boolean existsByOwnerId(UUID ownerId) {

        return collectionRepository.existsByOwnerId(ownerId);
    }
    public boolean existsById(UUID collectionId) {

        return collectionRepository.existsById(collectionId);
    }

    public Optional<Collection> addGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        collection.ifPresent(value -> value.getGameList().add(game));

        return collection;
    }

    public Optional<Collection> removeGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        collection.ifPresent(value -> value.getGameList().remove(game));

        return collection;
    }
}
