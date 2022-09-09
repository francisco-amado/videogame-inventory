package com.inventory.app.domain.valueobjects;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Embeddable
public class CollectionId implements Serializable {

    String collectionId;

    private static final long serialVersionUID = 4L;

    public CollectionId(String collectionId){

        this.collectionId = collectionId;
    }

    private static final AtomicLong idCounter = new AtomicLong();

    public CollectionId() {

    }

    public static CollectionId createCollectionId() {

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
