package com.inventory.app.controllers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.GameId;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.services.CollectionService;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollectionController {

    private final CollectionService collectionService;
    private final GameService gameService;

    @Autowired
    public CollectionController(CollectionService collectionService, GameService gameService) {

        this.collectionService = collectionService;
        this.gameService = gameService;
    }

    @PostMapping(path = "/create/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createCollection(@PathVariable(value="id") OwnerId id,
                                                   @RequestBody CollectionDTO collectionDTO) {

        if (collectionService.existsByOwnerId(id)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        collectionService.createCollection(id, collectionDTO.getGameList());

        return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
    }

    @PostMapping(path = "/add/{collectionid}/{gameid}",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> addGameToCollection(@PathVariable(value="collectionid")CollectionId collectionId,
                                                      @PathVariable(value="gameid") GameId gameId) {

        if (!collectionService.existsById(collectionId)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Optional<Game> gameToAdd = gameService.findGameById(gameId);

        if (gameToAdd.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        collectionService.addGame(gameToAdd.get(), collectionId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Game added successfully");
    }

    @DeleteMapping(path = "/remove/{collectionid}/{gameid}",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> removeGameFromCollection(@PathVariable(value="collectionid")CollectionId collectionId,
                                                      @PathVariable(value="gameid") GameId gameId) {

        if (!collectionService.existsById(collectionId)) {

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
