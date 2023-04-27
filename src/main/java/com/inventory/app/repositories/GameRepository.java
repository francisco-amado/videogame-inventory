package com.inventory.app.repositories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

    List<Game> findAllByCollection_CollectionId(UUID collectionId);
}
