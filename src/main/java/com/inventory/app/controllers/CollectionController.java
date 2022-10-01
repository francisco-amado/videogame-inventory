package com.inventory.app.controllers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.dto.CollectionDTO;
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

    @PostMapping(path = "/create/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createCollection(@PathVariable(value="id") OwnerId id,
                                                   @RequestBody CollectionDTO collectionDTO) {

        if (collectionService.existsByOwnerId(id)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        collectionService.createCollection(id,collectionDTO.getGameList());

        return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
    }
}
