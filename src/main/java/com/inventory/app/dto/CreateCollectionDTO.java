package com.inventory.app.dto;

import com.inventory.app.domain.game.Game;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateCollectionDTO {

    private @Getter @Setter List<Game> gameList;
}
