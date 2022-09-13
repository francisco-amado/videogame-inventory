package com.inventory.app.repositories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.OwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, OwnerId> {

    boolean existsByUserName(Name userName);

    boolean existsByEmail(Email email);
}
