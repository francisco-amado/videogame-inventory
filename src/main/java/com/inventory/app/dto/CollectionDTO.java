package com.inventory.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Component
public class CollectionDTO extends RepresentationModel<CollectionDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private @Getter @Setter UUID collectionID;
    private @Getter @Setter List<GameDTO> gameList;
}
