package com.inventory.app.controllers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {

        this.collectionService = collectionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> saveCollection(@RequestParam OwnerId ownerId, @RequestParam List<Game> gameList) {

        if (collectionService.existsByOwnerId(ownerId)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        collectionService.createCollection(ownerId, gameList);

        return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
    }
}
