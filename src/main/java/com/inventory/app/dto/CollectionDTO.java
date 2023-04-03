package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Component
public class CollectionDTO {

    private @Getter @Setter UUID collectionID;
    private @Getter @Setter List<GameDTO> gameList;
}
