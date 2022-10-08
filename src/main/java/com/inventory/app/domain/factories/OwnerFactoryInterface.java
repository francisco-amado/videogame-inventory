package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OwnerFactoryInterface {

    Owner createOwner(Name userName, Email email, Password password);
}
