package com.inventory.app.controllers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    @GetMapping(path = "/get/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> game = gameService.findGameById(gameId);

        if (game.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(game.get());

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");
        }
    }

    @PostMapping(path = "/create", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createGame(@RequestBody GameDTO gameDTO) {

       Name name = Name.createName(gameDTO.getName());
       Console console = Console.createConsole(Console.ConsoleEnum.valueOf(gameDTO.getConsole()));
       Region region = Region.createRegion(Region.RegionEnum.valueOf(gameDTO.getRegion()));

       gameService.createGame(name, console, gameDTO.getReleaseDate(), region);

       return ResponseEntity.status(HttpStatus.CREATED).body("Game created successfully");
    }

    @DeleteMapping(path = "/delete/{id}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> deleteGame(@PathVariable(value=("id")) UUID gameId) {

        Optional<Game> gameToDelete = gameService.findGameById(gameId);

        if (gameToDelete.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game does not exist");

        } else if (gameToDelete.get().getCollection() != null) {

            throw new UnsupportedOperationException("Game cannot be deleted if it belongs to a collection");
        }

        gameService.deleteGame(gameId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Game deleted successfully");
    }
}
