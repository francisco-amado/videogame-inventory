package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
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

    public Collection createCollection(Owner owner, List<Game> gameList) {

        Collection newCollection = collectionFactoryInterface.createCollection(owner, gameList);

        return collectionRepository.save(newCollection);
    }

    public Optional<Collection> findById(UUID collectionId) {

        return collectionRepository.findById(collectionId);
    }

    public boolean existsByOwner(Owner owner) {

        return collectionRepository.existsByOwner(owner);
    }
    public boolean existsById(UUID collectionId) {

        return collectionRepository.existsById(collectionId);
    }

    public Optional<Collection> addGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if(collection.isPresent()) {

            collection.get().addGameToList(game);
            game.setCollection(collection.get());
            collectionRepository.save(collection.get());
        }

        return collection;
    }

    public Optional<Collection> removeGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if(collection.isPresent()) {

            collection.get().getGameList().remove(game);
            game.setCollection(null);
            collectionRepository.save(collection.get());
        }

        return collection;
    }
}
