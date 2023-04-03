package com.inventory.app.restcontrollers;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.dto.CreateCollectionDTO;
import com.inventory.app.dto.mappers.CollectionDTOMapper;
import com.inventory.app.exceptions.InvalidEntryDataException;
import com.inventory.app.services.CollectionService;
import com.inventory.app.services.GameService;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollectionRestController {

    private final CollectionService collectionService;
    private final GameService gameService;
    private final OwnerService ownerService;
    private final CollectionDTOMapper collectionDTOMapper;

    @Autowired
    public CollectionRestController(CollectionService collectionService, GameService gameService,
                                    OwnerService ownerService, CollectionDTOMapper collectionDTOMapper) {

        this.collectionService = collectionService;
        this.gameService = gameService;
        this.ownerService = ownerService;
        this.collectionDTOMapper = collectionDTOMapper;
    }

    @GetMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getCollection(@PathVariable(value="id") UUID collectionId) {

        Optional<Collection> collectionFound = collectionService.findById(collectionId);

        if (collectionFound.isPresent()) {

            CollectionDTO collectionDTO = collectionDTOMapper.toDTO(collectionFound.get());

            Link selfLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .getCollection(collectionDTO.getCollectionID()))
                            .withSelfRel()
                            .withType("GET");

            Link addGameLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .addGameToCollection(collectionDTO.getCollectionID(), null))
                            .withRel("addGame")
                            .withType("PATCH");

            Link removeGameLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .removeGameFromCollection(collectionDTO.getCollectionID(), null))
                            .withRel("removeGame")
                            .withType("PATCH");

            collectionDTO.add(selfLink, addGameLink, removeGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(collectionDTO);
        }

        return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("");
    }

    @PostMapping(path = "/{ownerId}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createCollection(@PathVariable(value="ownerId") UUID ownerId,
                                                   @RequestBody CreateCollectionDTO createCollectionDTO) {

        Optional<Owner> owner = ownerService.findById(ownerId);

        if (owner.isPresent() && collectionService.existsByOwner(owner.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
        }

        if (owner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner does not exist");
        } else {
            try {
                Collection collection = collectionService.createCollection(owner.get(), createCollectionDTO);

                CollectionDTO collectionDTO = collectionDTOMapper.toDTO(collection);

                Link selfLink =
                        linkTo(methodOn(CollectionRestController.class)
                                .getCollection(collectionDTO.getCollectionID()))
                                .withSelfRel()
                                .withType("GET");

                Link addGameLink =
                        linkTo(methodOn(CollectionRestController.class)
                                .addGameToCollection(collectionDTO.getCollectionID(), null))
                                .withRel("addGame")
                                .withType("PATCH");

                Link removeGameLink =
                        linkTo(methodOn(CollectionRestController.class)
                                .removeGameFromCollection(collectionDTO.getCollectionID(), null))
                                .withRel("removeGame")
                                .withType("PATCH");

                collectionDTO.add(selfLink, addGameLink, removeGameLink);

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(collectionDTO);

            } catch (InvalidEntryDataException | NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    @PatchMapping(path = "/{collectionid}/addgame/{gameid}",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> addGameToCollection(@PathVariable(value="collectionid")UUID collectionId,
                                                      @PathVariable(value="gameid") UUID gameId) {

        Optional<Collection> collection = collectionService.findById(collectionId);

        if (collection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Optional<Game> gameToAdd = gameService.findById(gameId);

        if (gameToAdd.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        collectionService.addGame(gameToAdd.get(), collectionId);

        CollectionDTO collectionDTO = collectionDTOMapper.toDTO(collection.get());

        Link selfLink =
                linkTo(methodOn(CollectionRestController.class)
                        .getCollection(collectionDTO.getCollectionID()))
                        .withSelfRel()
                        .withType("GET");


        Link removeGameLink =
                linkTo(methodOn(CollectionRestController.class)
                        .removeGameFromCollection(collectionDTO.getCollectionID(), gameToAdd.get().getGameId()))
                        .withRel("removeGame")
                        .withType("PATCH");

        collectionDTO.add(selfLink, removeGameLink);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionDTO);
    }

    @PatchMapping(path = "/{collectionid}/game/{gameid}/remove",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> removeGameFromCollection(@PathVariable(value="collectionid") UUID collectionId,
                                                      @PathVariable(value="gameid") UUID gameId) {

        Optional<Collection> collection = collectionService.findById(collectionId);

        if (collection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Optional<Game> gameToRemove = gameService.findById(gameId);

        if (gameToRemove.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        collectionService.removeGame(gameToRemove.get(), collectionId);

        CollectionDTO collectionDTO = collectionDTOMapper.toDTO(collection.get());

        Link selfLink =
                linkTo(methodOn(CollectionRestController.class)
                        .getCollection(collectionDTO.getCollectionID()))
                        .withSelfRel()
                        .withType("GET");

        Link addGameLink =
                linkTo(methodOn(CollectionRestController.class)
                        .addGameToCollection(collectionDTO.getCollectionID(), gameToRemove.get().getGameId()))
                        .withRel("addGame")
                        .withType("PATCH");

        collectionDTO.add(selfLink, addGameLink);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionDTO);
    }
}
