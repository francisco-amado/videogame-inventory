package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Region;
import java.time.LocalDate;

public class GameFactory implements GameFactoryInterface{

    public Game createGame(String name, Console console, LocalDate releaseDate, Region region) {

        return new Game(name, console, releaseDate, region);
    }
}
