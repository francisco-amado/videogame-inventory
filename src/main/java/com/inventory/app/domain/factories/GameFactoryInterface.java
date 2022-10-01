package com.inventory.app.domain.factories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public interface GameFactoryInterface {

    Game createGame(GameId gameId, Name name, Console console, LocalDate releaseDate,
                    Region region);
}
