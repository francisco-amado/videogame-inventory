package com.inventory.app.domain.factories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CreateCollectionDTO;
import org.springframework.stereotype.Service;

@Service
public class CollectionFactory {

    public Collection createCollection(Owner owner, CreateCollectionDTO createCollectionDTO) {

        return new Collection(owner, createCollectionDTO.getGameList());
    }
}
