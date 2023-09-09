package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactory;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CreateCollectionDTO;
import com.inventory.app.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public void createCollection(Owner owner) {

        List<Game> gameList = new ArrayList<>();
        CreateCollectionDTO createCollectionDTO = new CreateCollectionDTO();
        createCollectionDTO.setGameList(gameList);
        Collection newCollection = collectionFactoryInterface.createCollection(owner, createCollectionDTO);
        collectionRepository.save(newCollection);
        gameService.setCollectionForGameList(createCollectionDTO.getGameList(), newCollection);
    }

    public Optional<Collection> findById(UUID collectionId) {
        return collectionRepository.findById(collectionId);
    }

    @Transactional
    public void removeGame(Game game, UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            collection.get().getGameList().remove(game);
            game.setCollection(null);
            gameService.deleteGame(game.getGameId());
            collectionRepository.save(collection.get());
        }
    }

    public void deleteCollection(UUID collectionId) {

        Optional<Collection> collection = collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            gameService.deleteGameList(collectionId);
            collectionRepository.delete(collection.get());
        }
    }
}
