package com.inventory.app.domain.valueobjects;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Embeddable
public class OwnerId implements Serializable {

    String ownerId;

    public OwnerId(String ownerId){

        this.ownerId = ownerId;
    }

    private static final long serialVersionUID = 5L;

    private static final AtomicLong idCounter = new AtomicLong();

    public OwnerId() {

    }

    public static GameId createOwnerId() {

        return new GameId("OWNER" + idCounter.getAndIncrement());
    }
}
