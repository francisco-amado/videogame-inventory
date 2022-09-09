package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.*;

public class OwnerFactory implements OwnerFactoryInterface {

    public Owner createOwner(OwnerId ownerId, Name userName, CollectionId collectionId,
                             Email email, Password password) {

        return new Owner(ownerId, userName, collectionId, email, password);
    }
}
