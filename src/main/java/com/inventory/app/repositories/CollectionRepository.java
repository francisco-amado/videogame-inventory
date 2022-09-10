package com.inventory.app.repositories;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.valueobjects.CollectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, CollectionId> {
}
