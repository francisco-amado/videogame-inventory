package com.inventory.app.repositories;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.GameId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, GameId> {
}
