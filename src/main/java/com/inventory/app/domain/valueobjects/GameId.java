package com.inventory.app.domain.valueobjects;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class GameId {

    String gameId;

    public GameId(String gameId){

        this.gameId = gameId;
    }

    private static final AtomicLong idCounter = new AtomicLong();

    public static GameId createGameId() {

        return new GameId("GAME" + idCounter.getAndIncrement());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameId gameId1 = (GameId) o;
        return gameId.equals(gameId1.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }
}
