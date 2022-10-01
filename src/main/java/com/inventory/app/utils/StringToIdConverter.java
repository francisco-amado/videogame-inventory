package com.inventory.app.utils;

import com.inventory.app.domain.valueobjects.CollectionId;
import com.inventory.app.domain.valueobjects.GameId;
import com.inventory.app.domain.valueobjects.OwnerId;

public class StringToIdConverter {

    public static OwnerId stringToOwnerIdConverter(String ownerId) {

        return new OwnerId(ownerId);
    }

    public static CollectionId stringToCollectionIdConverter(String collectionId) {

        return new CollectionId(collectionId);
    }

    public static GameId stringToGameIdConverter(String gameId) {

        return new GameId(gameId);
    }
}
