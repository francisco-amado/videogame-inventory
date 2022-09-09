package com.inventory.app.domain.owner;

import com.inventory.app.domain.valueobjects.*;

import java.util.Objects;

public class Owner {

    OwnerId ownerId;
    Name userName;
    CollectionId collectionId;
    Email email;
    Password password;

    public Owner(OwnerId ownerId, Name userName, CollectionId collectionId, Email email, Password password) {

        this.ownerId = ownerId;
        this.userName = userName;
        this.collectionId = collectionId;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(ownerId, owner.ownerId) &&
                Objects.equals(userName, owner.userName) &&
                Objects.equals(collectionId, owner.collectionId) &&
                Objects.equals(email, owner.email) &&
                Objects.equals(password, owner.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, userName, collectionId, email, password);
    }
}
