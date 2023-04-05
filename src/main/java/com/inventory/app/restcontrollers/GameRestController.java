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

            Link editGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .editGame(gameDTO.getGameId(), null))
                            .withRel("editGame")
                            .withType("PATCH");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(gameDTO.getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            gameDTO.add(selfLink, editGameLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gameDTO);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("");
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createGame(@RequestBody CreateGameDTO createGameDTO) {

        try{
            Game game = gameService.createGame(createGameDTO);

            GameDTO gameDTO = gameDTOMapper.toDTO(game);

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameDTO.getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link editGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .editGame(gameDTO.getGameId(), null))
                            .withRel("editGame")
                            .withType("PATCH");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(gameDTO.getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            gameDTO.add(selfLink, editGameLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(gameDTO);

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

            GameDTO gameDTO = gameDTOMapper.toDTO(gameToEdit.get());

            Link selfLink =
                    linkTo(methodOn(GameRestController.class)
                            .getGame(gameDTO.getGameId()))
                            .withSelfRel()
                            .withType("GET");

            Link deleteGameLink =
                    linkTo(methodOn(GameRestController.class)
                            .deleteGame(gameDTO.getGameId()))
                            .withRel("deleteGame")
                            .withType("DELETE");

            gameDTO.add(selfLink, deleteGameLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(gameDTO);

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
