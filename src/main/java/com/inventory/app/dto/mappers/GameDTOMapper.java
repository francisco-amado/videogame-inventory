package com.inventory.app.dto.mappers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.dto.GameDTO;
import org.springframework.stereotype.Service;

@Service
public class GameDTOMapper {

    public GameDTO toDTO(Game game) {

        GameDTO gameDTO = new GameDTO();

        gameDTO.setName(game.getName().getName());
        gameDTO.setConsole(game.getConsole().getConsoleDescription());
        gameDTO.setReleaseDate(game.getReleaseDate());
        gameDTO.setRegion(game.getRegion().getRegionDescription());
        gameDTO.setLocation(game.getLocation());
        gameDTO.setLocalBought(game.getLocalBought());
        gameDTO.setWasGifted(game.getWasGifted());

        return gameDTO;
    }
}
