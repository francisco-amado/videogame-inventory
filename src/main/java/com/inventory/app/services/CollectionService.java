package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactory;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CreateCollectionDTO;
import com.inventory.app.exceptions.InvalidEntryDataException;
import com.inventory.app.repositories.CollectionRepository;
import com.inventory.app.utils.ServiceResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final GameService gameService;
    private final CollectionFactory collectionFactory;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, GameService gameService,
                             CollectionFactory collectionFactory) {

        this.collectionRepository = collectionRepository;
        this.gameService = gameService;
        this.collectionFactory = collectionFactory;
    }

    @Transactional
    public Collection createCollection(Owner owner, CreateCollectionDTO createCollectionDTO)
            throws InvalidEntryDataException, NoSuchElementException {

        if (createCollectionDTO == null) {
            throw new InvalidEntryDataException(ServiceResponses.getINVALID_ENTRY_DATA());
        }

        for(Game game : createCollectionDTO.getGameList()) {
            if(!gameService.existsById(game.getGameId())) {
                throw new NoSuchElementException(ServiceResponses.getGAME_NOT_FOUND());
            }
        }

        Collection newCollection = collectionFactory.createCollection(owner, createCollectionDTO);
        collectionRepository.save(newCollection);
        gameService.setCollection(createCollectionDTO.getGameList(), newCollection);
        return newCollection;
    }

    public boolean existsByOwner(Owner owner) {
        return collectionRepository.existsByOwner(owner);
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
