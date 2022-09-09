package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.*;

public interface OwnerFactoryInterface {

    Owner createOwner(OwnerId ownerId, Name userName, CollectionId collectionId, Email email, Password password);
}