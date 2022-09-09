package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.*;
import org.springframework.stereotype.Service;

@Service
public class OwnerFactory implements OwnerFactoryInterface {

    public Owner createOwner(OwnerId ownerId, Name userName, Email email, Password password) {

        return new Owner(ownerId, userName, email, password);
    }
}
