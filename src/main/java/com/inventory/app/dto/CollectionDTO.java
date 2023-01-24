package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionDTO {

    private @Getter @Setter List<GameDTO> gameList;
}
