package com.inventory.app.repositories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    boolean existsByUserName(Name userName);

    boolean existsByEmail(String email);

    Optional<Owner> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Owner o SET o.enabled = TRUE WHERE o.email = ?1")
    void enableOwner(String email);
}
