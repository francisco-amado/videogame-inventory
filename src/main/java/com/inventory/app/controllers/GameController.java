package com.inventory.app.controllers;

import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    @PostMapping(path = "/create", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createGame(@RequestBody GameDTO gameDTO) {

       Name name = Name.createName(gameDTO.getName());
       Console console = Console.createConsole(Console.ConsoleEnum.valueOf(gameDTO.getConsole()));
       Region region = Region.createRegion(Region.RegionEnum.valueOf(gameDTO.getRegion()));

       gameService.createGame(name, console, gameDTO.getReleaseDate(), region);

       return ResponseEntity.status(HttpStatus.CREATED).body("Game created successfully");
    }
}
