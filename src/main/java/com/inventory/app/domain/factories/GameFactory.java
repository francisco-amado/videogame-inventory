package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameFactory {

    public Game createGame(Name name, Console console, LocalDate releaseDate, Region region) {

        return new Game(name, console, region, releaseDate);
    }
}
