package com.inventory.app.repositories;

import com.inventory.app.domain.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {

    boolean existsByOwnerId(UUID ownerId);
}
