package com.inventory.app.services;

import com.inventory.app.domain.factories.GameFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.GameId;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameFactoryInterface gameFactoryInterface;

    @Autowired
    public GameService(GameRepository gameRepository, GameFactoryInterface gameFactoryInterface) {

        this.gameRepository = gameRepository;
        this.gameFactoryInterface = gameFactoryInterface;
    }

    public Game createGame(Name name, Console console, LocalDate releaseDate, Region region) {

        Game game = gameFactoryInterface.createGame(GameId.createGameId(), name, console, releaseDate, region);

        return gameRepository.save(game);
    }
}
