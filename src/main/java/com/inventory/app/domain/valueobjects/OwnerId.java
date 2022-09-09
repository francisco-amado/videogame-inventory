package com.inventory.app.domain.valueobjects;

import java.util.concurrent.atomic.AtomicLong;

public class OwnerId {

    String ownerId;

    public OwnerId(String ownerId){

        this.ownerId = ownerId;
    }

    private static final AtomicLong idCounter = new AtomicLong();

    public static GameId createOwnerId() {

        return new GameId("OWNER" + idCounter.getAndIncrement());
    }
}
