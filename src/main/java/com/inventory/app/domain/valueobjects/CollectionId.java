package com.inventory.app.domain.valueobjects;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class CollectionId {

    String collectionId;

    public CollectionId(String collectionId){

        this.collectionId = collectionId;
    }

    private static final AtomicLong idCounter = new AtomicLong();

    public static CollectionId createCollectionID() {

        return new CollectionId("COL" + idCounter.getAndIncrement());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionId that = (CollectionId) o;
        return collectionId.equals(that.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId);
    }
}
