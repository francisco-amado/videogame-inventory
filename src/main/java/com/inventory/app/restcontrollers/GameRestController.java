package com.inventory.app.restcontrollers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.EditGameDTO;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.exceptions.InvalidEntryDataException;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Autowired
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> gameFound = gameService.findById(gameId);

        if(gameFound.isPresent()) {

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameFound.get().getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link editGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .editGame(gameFound.get().getGameId(), null))
                            .withRel("editGame")
                            .withType("PATCH");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(gameFound.get().getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            gameFound.get().add(selfLink, editGameLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gameFound.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("");
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createGame(@RequestBody GameDTO gameDTO) {

        try{
            Game game = gameService.createGame(gameDTO);

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(game.getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link editGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .editGame(game.getGameId(), null))
                            .withRel("editGame")
                            .withType("PATCH");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(game.getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            game.add(selfLink, editGameLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Game successfully created");

        } catch (InvalidEntryDataException ide) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ide.getMessage());
        }
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

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameToEdit.get().getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(gameToEdit.get().getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            gameToEdit.get().add(selfLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(gameToEdit.get());

        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(nse.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> deleteGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> gameToDelete = gameService.findById(gameId);

        if (gameToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }
        try{
            gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        } catch (BusinessRulesException bre) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }
}
