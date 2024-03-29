package com.inventory.app.restcontrollers;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.dto.CreateGameDTO;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.dto.mappers.CollectionDTOMapper;
import com.inventory.app.dto.mappers.GameDTOMapper;
import com.inventory.app.services.CollectionService;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final CollectionDTOMapper collectionDTOMapper;
    private final GameDTOMapper gameDTOMapper;

    @Autowired
    public CollectionRestController(CollectionService collectionService, GameService gameService,
                                    CollectionDTOMapper collectionDTOMapper, GameDTOMapper gameDTOMapper) {

        this.collectionService = collectionService;
        this.gameService = gameService;
        this.collectionDTOMapper = collectionDTOMapper;
        this.gameDTOMapper = gameDTOMapper;
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

            Link ownerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(collectionFound.get().getOwner().getEmail()))
                            .withRel("getOwner")
                            .withType("GET");

            Link gameLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(null))
                            .withRel("getGame")
                            .withType("PATCH");

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

            collectionDTO.add(selfLink, ownerLink, gameLink, addGameLink, removeGameLink);

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

    @PatchMapping(path = "/{collectionid}/addgame",
            headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> addGameToCollection(@PathVariable(value="collectionid")UUID collectionId,
                                                      @RequestBody CreateGameDTO createGameDTO) {

        Optional<Collection> collection = collectionService.findById(collectionId);

        if (collection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection does not exist");
        }

        Game game = gameService.createGame(createGameDTO, collection.get());

        GameDTO gameDTO = gameDTOMapper.toDTO(game);

        CollectionDTO collectionDTO = collectionDTOMapper.toDTO(collection.get());

        Link selfLink =
                linkTo(methodOn(CollectionRestController.class)
                        .getCollection(collectionDTO.getCollectionID()))
                        .withSelfRel()
                        .withType("GET");


        Link removeGameLink =
                linkTo(methodOn(CollectionRestController.class)
                        .removeGameFromCollection(collectionDTO.getCollectionID(), gameDTO.getGameId()))
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

        collectionDTO.add(selfLink);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionDTO);
    }
}
