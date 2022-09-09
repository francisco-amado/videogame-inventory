package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.OwnerId;

public class OwnerFactory implements OwnerFactoryInterface {

    public Owner createOwner(OwnerId ownerId, Name userName, CollectionId collectionId, Email email) {

        return new Owner(ownerId, userName, collectionId, email);
    }
}
