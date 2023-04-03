package com.inventory.app.dto.mappers;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.dto.CollectionDTO;
import com.inventory.app.dto.GameDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionDTOMapper {

    public CollectionDTO toDTO(Collection collection) {

        GameDTOMapper gameDTOMapper = new GameDTOMapper();
        CollectionDTO collectionDTO = new CollectionDTO();
        List<Game> gameList = collection.getGameList();
        List<GameDTO> gameDTOList = new ArrayList<>();

        for(Game game : gameList) {
            GameDTO gameDTO = gameDTOMapper.toDTO(game);
            gameDTOList.add(gameDTO);
        }

        collectionDTO.setCollectionID(collection.getCollectionId());
        collectionDTO.setGameList(gameDTOList);

        return collectionDTO;
    }
}