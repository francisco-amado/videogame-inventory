package com.inventory.app.services;

import com.inventory.app.domain.factories.OwnerFactoryInterface;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.*;
import com.inventory.app.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerFactoryInterface ownerFactoryInterface;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,
                             OwnerFactoryInterface ownerFactoryInterface) {

        this.ownerRepository = ownerRepository;
        this.ownerFactoryInterface = ownerFactoryInterface;
    }

    public Owner createOwner(Name userName, Email email, Password password) {

        Owner newOwner = ownerFactoryInterface.createOwner(userName, email, password);

        return ownerRepository.save(newOwner);
    }

    public boolean existsByUsername(Name userName) {

        return ownerRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(Email email) {

        return ownerRepository.existsByEmail(email);
    }

    public Optional<Owner> findById(UUID ownerId) {

        return ownerRepository.findById(ownerId);
    }
}
