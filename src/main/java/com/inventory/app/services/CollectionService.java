package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.repositories.CollectionRepository;
import com.inventory.app.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionFactoryInterface collectionFactoryInterface;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository,
                             CollectionFactoryInterface collectionFactoryInterface, OwnerRepository ownerRepository) {

        this.collectionRepository = collectionRepository;
        this.collectionFactoryInterface = collectionFactoryInterface;
        this.ownerRepository = ownerRepository;
    }

    public Collection createCollection(OwnerId ownerId, List<Game> gameList) {

        Collection newCollection = collectionFactoryInterface.createCollection(CollectionId.createCollectionId(),
                ownerId, gameList);

        return collectionRepository.save(newCollection);
    }

    public Optional<Collection> findById(CollectionId collectionId) {

        return collectionRepository.findById(collectionId);
    }

    public boolean existsByOwnerId(OwnerId ownerId) {

        return collectionRepository.existsByOwnerId(ownerId);
    }

    public Optional<Collection> addGame(Game game, CollectionId collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        collection.ifPresent(value -> value.getGameList().add(game));

        return collection;
    }
}
