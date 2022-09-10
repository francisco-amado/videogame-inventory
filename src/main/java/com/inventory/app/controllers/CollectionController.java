package com.inventory.app.controllers;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> saveCollection(@RequestBody Collection collection) {

        if (collectionService.existsByOwnerId(collection.getOwnerId())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        collectionService.save(collection.getCollectionId(), collection.getOwnerId(), collection.getGameList());

        return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
    }
}
