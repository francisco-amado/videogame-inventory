package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class GameFactory implements GameFactoryInterface {

    public Game createGame(GameId gameId, Name name, Console console, LocalDate releaseDate, Region region) {

        return new Game(gameId, name, console, region, releaseDate);
    }
}
