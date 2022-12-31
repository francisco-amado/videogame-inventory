package com.inventory.app.dto;

import com.inventory.app.domain.game.Game;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ToString
public class CollectionDTO {

    private @Getter @Setter List<Game> gameList;
}
