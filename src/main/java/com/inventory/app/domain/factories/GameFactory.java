package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.GameId;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class GameFactory implements GameFactoryInterface{

    public Game createGame(GameId gameId, Name name, Console console, LocalDate releaseDate,
                           Region region, String location, String localBought, Boolean wasGifted) {

        return new Game(gameId, name, console, releaseDate, region, location, localBought, wasGifted);
    }
}
