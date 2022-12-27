package com.inventory.app.restcontrollers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.EditGameDTO;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

        Optional<Game> gameFound = gameService.findGameById(gameId);

        return gameFound.<ResponseEntity<Object>>map(
                game -> ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(game))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Game does not exist"));
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createGame(@RequestBody GameDTO gameDTO, UriComponentsBuilder ucBuilder) {

       Name name = Name.createName(gameDTO.getName());
       Console console = Console.createConsole(Console.ConsoleEnum.valueOf(gameDTO.getConsole()));
       Region region = Region.createRegion(Region.RegionEnum.valueOf(gameDTO.getRegion()));

       UUID gameUUID = gameService.createGame(name, console, gameDTO.getReleaseDate(), region);

       HttpHeaders headers = new HttpHeaders();
       headers
               .setLocation(ucBuilder.path("/games/{id}")
                       .buildAndExpand(gameUUID)
                       .toUri());

       return ResponseEntity
               .status(HttpStatus.CREATED)
               .headers(headers)
               .body("Game successfully created");
    }

    @PatchMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> editGame(@PathVariable(value=("id")) UUID gameId,
                                           @RequestBody EditGameDTO editGameDTO) {

        Optional<Game> gameToEdit = gameService.findGameById(gameId);

        if (gameToEdit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        } else {
            gameService.editGame(gameId, editGameDTO);
            return ResponseEntity.status(HttpStatus.OK).body(gameToEdit);
        }
    }

    @DeleteMapping(path = "/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> deleteGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> gameToDelete = gameService.findGameById(gameId);

        if (gameToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }

        try{
            gameService.deleteGame(gameId);
        } catch (UnsupportedOperationException uoe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uoe.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Game deleted successfully");
    }
}
