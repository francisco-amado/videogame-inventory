package com.inventory.app.controllers;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.repositories.OwnerRepository;
import com.inventory.app.services.CollectionService;
import com.inventory.app.services.GameService;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollectionController {

    private final CollectionService collectionService;
    private final GameService gameService;
    private final OwnerService ownerService;

    @Autowired
    public CollectionController(CollectionService collectionService, GameService gameService,
                                OwnerService ownerService) {

        this.collectionService = collectionService;
        this.gameService = gameService;
        this.ownerService = ownerService;
    }

    @GetMapping(path = "/get/{id}", produces = "application/json")
    public ResponseEntity<Object> getCollection(@PathVariable(value="id") UUID collectionId) {

        List<Game> gameList = gameService.findGamesByCollectionId(collectionId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(gameList);
    }

    @PostMapping(path = "/create/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createCollection(@PathVariable(value="id") UUID ownerId,
                                                   @RequestBody CollectionDTO collectionDTO) {

        Optional<Owner> owner = ownerService.findById(ownerId);

        if (owner.isPresent() && collectionService.existsByOwner(owner.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        if (owner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner does not exist");
        } else {
            Collection collection = collectionService.createCollection(owner.get(), collectionDTO.getGameList());
            ownerService.updateOwnerCollection(owner.get(), collection);

            return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
        }
    }

    @PostMapping(path = "/add/{collectionid}/{gameid}",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> addGameToCollection(@PathVariable(value="collectionid")UUID collectionId,
                                                      @PathVariable(value="gameid") UUID gameId) {

        if (collectionService.existsById(collectionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Optional<Game> gameToAdd = gameService.findGameById(gameId);

        if (gameToAdd.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        collectionService.addGame(gameToAdd.get(), collectionId);

        return ResponseEntity.status(HttpStatus.OK).body("Game added successfully");
    }

    @DeleteMapping(path = "/remove/{collectionid}/{gameid}",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> removeGameFromCollection(@PathVariable(value="collectionid") UUID collectionId,
                                                      @PathVariable(value="gameid") UUID gameId) {

        if (collectionService.existsById(collectionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Optional<Game> gameToRemove = gameService.findGameById(gameId);

        if (gameToRemove.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        collectionService.removeGame(gameToRemove.get(), collectionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Game removed successfully");
    }

}
