package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final GameService gameService;
    private final CollectionFactoryInterface collectionFactoryInterface;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, GameService gameService,
                             CollectionFactoryInterface collectionFactoryInterface) {

        this.collectionRepository = collectionRepository;
        this.gameService = gameService;
        this.collectionFactoryInterface = collectionFactoryInterface;
    }

    public Collection createCollection(Owner owner, CollectionDTO collectionDTO) throws IllegalStateException {

        if (collectionDTO == null) {
            throw new IllegalStateException("Invalid entry data");
        }

        Collection newCollection = collectionFactoryInterface.createCollection(owner, collectionDTO);
        collectionRepository.save(newCollection);
        return newCollection;
    }

    public boolean existsByOwner(Owner owner) {
        return collectionRepository.existsByOwner(owner);
    }

    public boolean existsById(UUID collectionId) {
        return collectionRepository.existsById(collectionId);
    }

    public Optional<Collection> findById(UUID collectionID) {
        return collectionRepository.findById(collectionID);
    }

    @Transactional
    public void addGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            collection.get().getGameList().add(game);
            game.setCollection(collection.get());
            gameService.save(game);
            collectionRepository.save(collection.get());
        }
    }

    @Transactional
    public void removeGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            collection.get().getGameList().remove(game);
            game.setCollection(null);
            gameService.save(game);
            collectionRepository.save(collection.get());
        }
    }
}
