package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CollectionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionFactoryInterface {

    Collection createCollection(Owner owner, CollectionDTO collectionDTO);
}
