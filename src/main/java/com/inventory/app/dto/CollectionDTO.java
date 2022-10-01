package com.inventory.app.dto;

import com.inventory.app.domain.game.Game;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CollectionDTO {

    public String ownerId;
    public List<Game> gameList;
}
