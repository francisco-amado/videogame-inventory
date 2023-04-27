package com.inventory.app.restcontrollers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.dto.EditGameDTO;
import com.inventory.app.dto.CreateGameDTO;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.dto.mappers.GameDTOMapper;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.exceptions.InvalidEntryDataException;
import com.inventory.app.services.GameService;
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
@RequestMapping("/games")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GameRestController {

    private final GameService gameService;
    private final GameDTOMapper gameDTOMapper;

    @Autowired
    public GameRestController(GameService gameService, GameDTOMapper gameDTOMapper) {
        this.gameService = gameService;
        this.gameDTOMapper = gameDTOMapper;
    }

    @GetMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> gameFound = gameService.findById(gameId);

        if(gameFound.isPresent()) {

            GameDTO gameDTO = gameDTOMapper.toDTO(gameFound.get());

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameDTO.getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link collectionLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .getCollection(gameFound.get().getCollection().getCollectionId()))
                            .withRel("getCollection")
                            .withType("GET");

            Link editGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .editGame(gameDTO.getGameId(), null))
                            .withRel("editGame")
                            .withType("PATCH");

            gameDTO.add(selfLink, collectionLink, editGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gameDTO);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("");
    }

    @PatchMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> editGame(@PathVariable(value=("id")) UUID gameId,
                                           @RequestBody EditGameDTO editGameDTO) {

        Optional<Game> gameToEdit = gameService.findById(gameId);

        if (gameToEdit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }
        try {
            gameService.editGame(gameId, editGameDTO);

            GameDTO gameDTO = gameDTOMapper.toDTO(gameToEdit.get());

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameDTO.getGameId()))
                            .withSelfRel()
                            .withType("GET");

            gameDTO.add(selfLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(gameDTO);

        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(nse.getMessage());
        }
    }
}
