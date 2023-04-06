package com.inventory.app.repositories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {

    boolean existsByOwner(Owner owner);

    Optional<Collection> findByOwner(Owner owner);
}
