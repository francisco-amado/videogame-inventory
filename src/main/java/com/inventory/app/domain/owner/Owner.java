package com.inventory.app.domain.owner;

import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.OwnerId;

import java.util.Objects;

public class Owner {

    OwnerId ownerId;
    Name userName;
    CollectionId collectionId;
    Email email;

    public Owner(OwnerId ownerId, Name userName, CollectionId collectionId, Email email) {

        this.ownerId = ownerId;
        this.userName = userName;
        this.collectionId = collectionId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(ownerId, owner.ownerId) &&
                Objects.equals(userName, owner.userName) &&
                Objects.equals(collectionId, owner.collectionId) &&
                Objects.equals(email, owner.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, userName, collectionId, email);
    }
}
