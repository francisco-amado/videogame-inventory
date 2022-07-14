package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Region;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class GameFactory implements GameFactoryInterface{

    public Game createGame(String name, Console console, LocalDate releaseDate, Region region, String location) {

        return new Game(name, console, releaseDate, region, location);
    }
}
